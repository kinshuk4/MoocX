
# OpenStreetMap Data Case Study

### Map Area
Birmingham, United Kingdom

- [Birmingham from Mapzen](https://mapzen.com/data/metro-extracts/metro/birmingham_england/)

Birmingham is the city I live now, so I’m curious about what the database shows here.

## Problems in the Map
First, I downloaded the full zip files of Birmingham from Mapzen and select a small sample of it to check the data.




### Postcode
To be honest, the postcode in UK is one of the most complicated systems in the world. I found two main problems of the postcode:

-  Incorrect postcode or format, such as "Interpolation between Land Registry 'Price Paid' data". All the code should obey the [vaildation of UK postcode](https://en.wikipedia.org/wiki/Postcodes_in_the_United_Kingdom#Outward_code) in the UK.
- There are two key names for postcode, `postcode` and `postal_code`

#### Incorrect postcodes or formats
There were a part of the list of incorrect postcode value.
```
B
FHRS Open Data
CodePoint Open
Land Registry 'Price Paid' data
extraploation
ONS Postal Code centroids
Interpolation between Land Registry 'Price Paid' data
Land Registry 'Price Paid'
Land Registry
code-point_open
Land_Registry_house_sales
company website
B18 6BP;B18 6BU
Interpolation between ONS and Land Registry data
interpolation from adjacent addrresses
school website
BCC website
Occupants website
```
To deal with this problem, I shaped postcode before converting .osm to .csv files, changing the appending tag code. In the code, I only keep the code start as four characters which could include 1 or 2 letters and 1 or 2 numbers, but the total length should be less than 9 characters.

```python
def clean_postcode(tag,tags):
    """Return the tags after modify, delet the invalid postcode format in UK"""
    n = re.search(re.compile(r'^(\D{1,2}\d{1,2})'),tag["value"])
    if n and len(tag["value"])< 9:
        tags.append(tag)
    else:
        print tag["value"]
    return tag,tags
```
Now, the tag with wrong postcodes and wrong formats were excluded.

#### Two key names
To deal with this problem, I added a new function named `check_key_postcode`:

```python
def check_key_postcode(key_name):
    if key =="postal_code":
        return "postcode"
    else:
        return key_name
        ...
if child.attrib['k'].find(':')>=0:
    pos = child.attrib['k'].find(':')                       
    tag["key"] =  check_key_postcode(child.attrib['k'][pos+1:]) #add check postcode function
    tag["type"] = child.attrib['k'][:pos]


```
### Street Name
Case mistakes and Overabbreviated were two major problems with the street name. For example:
- `Spring HIll` should be `Spring Hill`, and `Cape road` should be `Cape Road`
- `Malt Mill Ln` should be `Malt Mill Lane`
And I even found someone added his website as the street name! 

I used the mapping function and `.title()` to modify street names.

```python
mapping = { "St": "Street",
            "St.": "Street",
            "street":"Street",
            "Rd": "Road",
            "Ln":"Lane",
            "Ave":"Avenue",
           "Rd.": "Road",
           "HIll": "Hill",
           "Street,":"Street"
            }
            
def update_name(name, mapping):
    m = street_type_re.search(name)
    if m:
        street_type = m.group()
        if street_type in mapping.keys():
            name = re.sub(street_type_re, mapping[street_type], name)
    return name.title() #capitalize the first letter of each word

```

## Data Overview and Analysis
This part included an overview of data and other ideas.The SQL queries were applied here.

### File sizes
```
birmingham.osm ......... 1331 MB
birmingham.db .......... 798 MB
nodes.csv ............. 477 MB
nodes_tags.csv ........ 18.3 MB
ways.csv .............. 58.4 MB
ways_tags.csv ......... 104 MB
ways_nodes.cv ......... 191 MB 
```

### Number of unique users
```sql
SELECT COUNT(a.uid)
FROM (SELECT uid FROM nodes UNION SELECT uid FROM ways) as a;
```
2229

### Number of nodes
```sql
SELECT COUNT(*) FROM nodes;
```
6115956
### Number of ways
```sql
SELECT COUNT(*) FROM ways;
```
1038897

### Top 10 types of shop in Birmingham
```sql
SELECT value, count(value) as num
FROM nodes_tags
WHERE nodes_tags.key = "shop"
GROUP by value
ORDER by num DESC
LIMIT 10;
```
```sql
hairdresser|799
convenience|750
clothes|707
supermarket|228
yes|217
jewelry|194
car_repair|191
newsagent|161
beauty|147
charity|136
```
It was surprising that the most popular shop type in Birmingham was a hairdresser.

## Additional Exploration

### Most popular postcode
```sql
SELECT value, count(value) as num
FROM nodes_tags
WHERE nodes_tags.key = "postcode"
GROUP by value
ORDER by num DESC
LIMIT 10;
```
```sql
B91|141
B44|73
B28|55
B5|51
B13|45
B47|44
B63|43
B29|41
B12|36
B90|35

```

    The most popular postcode seemed to be B91, the area named Solihull. 
```sql
SELECT value, user, count(user) as num
FROM nodes_tags,nodes
WHERE nodes.id = nodes_tags.id and nodes_tags.key = "postcode" and nodes_tags.value = "B91"
GROUP by user
ORDER by num
```
`B91|brianboru|141`

When I deeply explored this postcode B91, I found the user `brianboru` contributed all 141 pieces data. So technically, B91 was not the famous postcode. So, I changed the query, the postcode that contributed by different users whose all contributions to this postcode should only be counted as once:
```sql
SELECT b.value, count(b.value) as num
FROM
(SELECT distinct value, user
FROM nodes_tags,nodes
WHERE nodes.id = nodes_tags.id and nodes_tags.key = "postcode") as b
GROUP BY e.value
ORDER BY num DESC
LIMIT 10;

```
```sql
B42 1TN|6
B29|5
B44|5
B13 8DD|4
B32 1AJ|4
B42 1LR|4
B1 2PZ|3
B13|3
B3 1EU|3
B4 7DA|3

```
As a result, 6 people contributed a node whose postcode was B42 1TN. While, this method also have some limitation. Because if someone had already tag something and contributed before you, you had nothing to contribute. In my opinion, this method was more useful when we looked for the most popular city, as the scope of the city is larger than the postcode.

### Most popular city
Ingored the user difference:
```sql
SELECT value, count(value) as num
FROM nodes_tags
WHERE nodes_tags.key = "postcode"
GROUP by value
ORDER by num DESC
LIMIT 10;
```
```sql
Birmingham|1115
Solihull|391
Alcester|158
Warwick|93
Leamington Spa|90
```
Considered distinct users:

```sql
SELECT b.value, count(b.value) as num
FROM
(SELECT distinct value, user
FROM nodes_tags,nodes
WHERE nodes.id = nodes_tags.id and nodes_tags.key = "postcode") as b
GROUP BY e.value
ORDER BY num DESC
LIMIT 10;

```
```sql
Birmingham|57
Coventry|29
Leamington Spa|12
Wolverhampton|10
Telford|7
```

It was clear that Birmingham was the most popular city. And after considering distinct user, I found Coventry was the second most popular rather than Solihull. It was totally right! Because Coventry was the 12th largest in the United Kingdom and the second largest city in the West Midlands, after Birmingham, with a population of 345,385 in 2015.

## Additional improvements and conclusion
First, it appeared that the whole 'Birmingham.osm' file omits some attributes that were standard. I ran small part of it worked well while ran the whole file, it alerted `"KeyError: 'user'`. It seemed in the .osm, some node's user attribute was missing. I found the similar problem [here](https://discussions.udacity.com/t/help-cleaning-data/169833/5) and using additional code to trap this problem:
```
for node in NODE_FIELDS:
    try:
        node_attribs[node] = element.attrib[node]
    except:
        print(node)
        node_attribs[node] = None
        pass
```
Therefore, "omiting some attributes" was one thing that Birmingham map could be improved.


Second, I calculated the contributing users.
```sql
SELECT a.user, COUNT(*) as num
FROM (SELECT user FROM nodes UNION ALL SELECT user FROM ways) as a
GROUP BY a.user
ORDER BY num DESC
LIMIT 5;
```
```sql
brianboru|3701292
blackadder|514316
Miked29|471313
mrpacmanmap|180650
Curran1980|157516
```
The user named brianboru contributed 3701292 pieces of data, accounting for **51.73%** of the total, and the top five contributed **70.23%**. So I thought OpenStreetMap may need to take more interesting activities to attract more users to contribute. For example, building a cool comunnity to encourage more users to contribute information, maybe could consider the method of game. Moreover,OpenStreetMap also can build a reviews system, and encourage users to check others contribution and modify it.

However, there must be much problems and challenges associated with the corresponding implementation. The first problem is the language problem.Because different users speak different language. In one area map, there are severals languages and it is hard to data wrangling and analysis. The second problem is the lack of the standard. Though most of the data are unified, textual data are easy to be chaotic. OpenStreetMap is supposed to consider use more strict restriction to make textual data more accurate. Third, if OpenStreetMap decides to build review system I mentioned above, it should establishment detailed rules to regulate when the user can modify other contribution and how modified content can be proved correct. There are still many things to explore in the future. 
