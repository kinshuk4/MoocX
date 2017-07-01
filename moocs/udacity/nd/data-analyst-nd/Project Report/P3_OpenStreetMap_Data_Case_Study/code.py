import csv
import codecs
import pprint
import re
import xml.etree.cElementTree as ET

import cerberus

import schema

OSM_PATH = "birmingham_small.osm" #change the osm file name

NODES_PATH = "nodes.csv"
NODE_TAGS_PATH = "nodes_tags.csv"
WAYS_PATH = "ways.csv"
WAY_NODES_PATH = "ways_nodes.csv"
WAY_TAGS_PATH = "ways_tags.csv"

LOWER_COLON = re.compile(r'^([a-z]|_)+:([a-z]|_)+')
PROBLEMCHARS = re.compile(r'[=\+/&<>;\'"\?%#$@\,\. \t\r\n]')

SCHEMA = schema1.schema

# Make sure the fields order in the csvs matches the column order in the sql table schema
NODE_FIELDS = ['id', 'lat', 'lon', 'user', 'uid', 'version', 'changeset', 'timestamp']
NODE_TAGS_FIELDS = ['id', 'key', 'value', 'type']
WAY_FIELDS = ['id', 'user', 'uid', 'version', 'changeset', 'timestamp']
WAY_TAGS_FIELDS = ['id', 'key', 'value', 'type']
WAY_NODES_FIELDS = ['id', 'node_id', 'position']
street_type_re = re.compile(r'\b\S+\.?$', re.IGNORECASE)

mapping = { "St": "Street",
            "St.": "Street",
            "street":"Street",
            "Rd": "Road",
            "Ln":"Lane",
            "Ave":"Avenue",
           "Rd.": "Road",
           "HIll":"Hill",
           "Street,":"Street"
            }

def check_key_postcode(key):
    """check if the key type is postal_code change it to postcode"""
    if key =="postal_code":
        return "postcode"
    else:
        return key

def is_street_name(elem):
    """check whether the elem is street"""
    return (elem.attrib['k'] == "addr:street")

def process_node(element, node_attr_fields, problem_chars, node_attribs,tags):
    """Return the dict when element.tag is node"""
    for attri in node_attr_fields:
        try:
            if attri in ['id','uid', 'changeset']:
                node_attribs[attri] = int(element.attrib[attri])
            elif attri in ['lat', 'lon']:
                node_attribs[attri] = float(element.attrib[attri])
            else:
                node_attribs[attri] = element.attrib[attri]
        except:
            node_attribs[attri] = '00000'
            pass

    # Code for node_tags
    for child in element:
        tag = {}
        m = re.search(problem_chars,child.attrib['k'])
        if not m:
            tag["id"] = int(element.attrib["id"])

            if is_street_name(child):
                child.attrib['v'] = update_name(child.attrib['v'], mapping)

            if child.attrib['k'].find(':')>=0:
                pos = child.attrib['k'].find(':')
                tag["key"] =  check_key_postcode(child.attrib['k'][pos+1:])
                tag["type"] = child.attrib['k'][:pos]
            else:
                tag["key"] = check_key_postcode(child.attrib['k'])
                tag["type"] = "regular"
            tag["value"] = child.attrib['v']

            if tag["key"] == "postcode":
                clean_postcode(tag,tags)
            else:
                tags.append(tag)
    return {'node': node_attribs, 'node_tags': tags}

def process_way(element, way_attr_fields, problem_chars, way_attribs, way_nodes,tags):
    """Return the dict when element.tag is way"""
    for attri in way_attr_fields:
        if attri in ['id','uid', 'changeset']:
            way_attribs[attri] = int(element.attrib[attri])
        else:
            way_attribs[attri] = element.attrib[attri]

    n = 0
    for child in element:
        way_node ={}
        way_tag = {}
        if child.tag == 'nd':
            n = n + 1
            way_node["id"] = int(element.attrib["id"])
            way_node["node_id"] = int(child.attrib["ref"])
            way_node["position"] = int(n-1)
            way_nodes.append(way_node)

        if child.tag == 'tag':
            m = re.search(problem_chars,child.attrib['k'])
            if not m:
                way_tag["id"] = int(element.attrib["id"])

                if is_street_name(child):
                    child.attrib['v'] = update_name(child.attrib['v'], mapping)

                if child.attrib['k'].find(':')>=0:
                    pos = child.attrib['k'].find(':')
                    way_tag["key"] = check_key_postcode(child.attrib['k'][pos+1:])
                    way_tag["type"] = child.attrib['k'][:pos]
                else:
                    way_tag["key"] = check_key_postcode(child.attrib['k'])
                    way_tag["type"] = "regular"
                way_tag["value"] = child.attrib['v']

            try：
                if way_tag["key"] == "postcode":
                    clean_postcode(way_tag,tags)
                else:
                    tags.append(way_tag)
            except:
                pass


    return {'way': way_attribs, 'way_nodes': way_nodes, 'way_tags': tags}


def update_name(name, mapping):
    """updata the street name"""
    m = street_type_re.search(name)
    if m:
        street_type = m.group()
        if street_type in mapping.keys():
            name = re.sub(street_type_re, mapping[street_type], name)
    return name.title()

def clean_postcode(tag,tags):
    """Return the tags after modify, delet the invalid postcode format in UK"""
    n = re.search(re.compile(r'^(\D{1,2}\d{1,2})'),tag["value"])
    if n and len(tag["value"])< 9:
        tags.append(tag)
    return tag,tags



def shape_element(element, node_attr_fields=NODE_FIELDS, way_attr_fields=WAY_FIELDS,
                  problem_chars=PROBLEMCHARS, default_tag_type='regular'):
    """Clean and shape node or way XML element to Python dict"""

    node_attribs = {}
    way_attribs = {}
    way_nodes = []
    tags = [] 


    if element.tag == 'node':
        return process_node(element, node_attr_fields, problem_chars, node_attribs,tags)
    elif element.tag == 'way':
        return process_way(element, way_attr_fields, problem_chars, way_attribs, way_nodes,tags)


# ================================================== #
#               Helper Functions                     #
# ================================================== #
def get_element(osm_file, tags=('node', 'way', 'relation')):
    """Yield element if it is the right type of tag"""

    context = ET.iterparse(osm_file, events=('start', 'end'))
    _, root = next(context)
    for event, elem in context:
        if event == 'end' and elem.tag in tags:
            yield elem
            root.clear()


def validate_element(element, validator, schema=SCHEMA):
    """Raise ValidationError if element does not match schema"""
    if validator.validate(element, schema) is not True:
        field, errors = next(validator.errors.iteritems())
        message_string = "\nElement of type '{0}' has the following errors:\n{1}"
        error_string = pprint.pformat(errors)

        raise Exception(message_string.format(field, error_string))


class UnicodeDictWriter(csv.DictWriter, object):
    """Extend csv.DictWriter to handle Unicode input"""

    def writerow(self, row):
        super(UnicodeDictWriter, self).writerow({
            k: (v.encode('utf-8') if isinstance(v, unicode) else v) for k, v in row.iteritems()
        })

    def writerows(self, rows):
        for row in rows:
            self.writerow(row)


# ================================================== #
#               Main Function                        #
# ================================================== #
def process_map(file_in, validate):
    """Iteratively process each XML element and write to csv(s)"""

    with codecs.open(NODES_PATH, 'w') as nodes_file, \
         codecs.open(NODE_TAGS_PATH, 'w') as nodes_tags_file, \
         codecs.open(WAYS_PATH, 'w') as ways_file, \
         codecs.open(WAY_NODES_PATH, 'w') as way_nodes_file, \
         codecs.open(WAY_TAGS_PATH, 'w') as way_tags_file:

        nodes_writer = UnicodeDictWriter(nodes_file, NODE_FIELDS)
        node_tags_writer = UnicodeDictWriter(nodes_tags_file, NODE_TAGS_FIELDS)
        ways_writer = UnicodeDictWriter(ways_file, WAY_FIELDS)
        way_nodes_writer = UnicodeDictWriter(way_nodes_file, WAY_NODES_FIELDS)
        way_tags_writer = UnicodeDictWriter(way_tags_file, WAY_TAGS_FIELDS)

        nodes_writer.writeheader()
        node_tags_writer.writeheader()
        ways_writer.writeheader()
        way_nodes_writer.writeheader()
        way_tags_writer.writeheader()

        validator = cerberus.Validator()

        for element in get_element(file_in, tags=('node', 'way')):
            el = shape_element(element)  #shape element
            if el:
                if validate is True:
                    validate_element(el, validator)

                if element.tag == 'node':
                    nodes_writer.writerow(el['node'])
                    node_tags_writer.writerows(el['node_tags'])
                elif element.tag == 'way':
                    ways_writer.writerow(el['way'])
                    way_nodes_writer.writerows(el['way_nodes'])
                    way_tags_writer.writerows(el['way_tags'])


if __name__ == '__main__':
    #Note: Validation is ~ 10X slower. For the project consider using a small
    #sample of the map when validating.
    process_map(OSM_PATH, validate=False)
