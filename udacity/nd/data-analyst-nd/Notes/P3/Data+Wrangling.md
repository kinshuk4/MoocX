
# 1. Read csv in python


```python
import os

DATADIR = ""
DATAFILE = "beatles-diskography.csv"


def parse_file(datafile):
    data = []
    with open(datafile, "rb") as f:
        header = f.readline().split(',')
        counter = 0
        for line in f:
            if counter == 10:
                break
            fields = line.split(',')
            entry = {}          
            for i, value in enumerate(fields):
                entry[header[i].strip()] = value.strip()
            data.append(entry)
            counter +=1
    return data

#parse_file(DATAFILE)
```

# 2. Use cvs module to read csv


```python
import os
import pprint
import csv

DATADIR =""
DATAFILE = "beatles-diskography.csv"

def parse_csv(datafile):
    data = []
    n = 0
    with open(datafile,'rb') as sd:
        r = csv.DictReader(sd)
        for line in r:
            data.append(line)
    return data

#parse_csv(DATAFILE)
```

# 3. Use XLRD to read excel to python


```python
import xlrd

datafile = "2013_ERCOT_Hourly_Load_Data.xls"


def parse_file(datafile):
    workbook = xlrd.open_workbook(datafile)
    sheet = workbook.sheet_by_index(0)

    data = [[sheet.cell_value(r, col) 
                for col in range(sheet.ncols)] 
                    for r in range(sheet.nrows)]

    print "\nList Comprehension"
    print "data[3][2]:",
    print data[3][2]

    print "\nCells in a nested loop:"    
    for row in range(sheet.nrows):
        for col in range(sheet.ncols):
            if row == 50:
                print sheet.cell_value(row, col),


    ### other useful methods:
    print "\nROWS, COLUMNS, and CELLS:"
    print "Number of rows in the sheet:", 
    print sheet.nrows
    print "Type of data in cell (row 3, col 2):", 
    print sheet.cell_type(3, 2)
    print "Value in cell (row 3, col 2):", 
    print sheet.cell_value(3, 2)
    print "Get a slice of values in column 3, from rows 1-3:"
    print sheet.col_values(3, start_rowx=1, end_rowx=4)

    print "\nDATES:"
    print "Type of data in cell (row 1, col 0):", 
    print sheet.cell_type(1, 0)
    
    exceltime = sheet.cell_value(1, 0)
    print "Time in Excel format:",
    print exceltime
    print "Convert time to a Python datetime tuple, from the Excel float:",
    print xlrd.xldate_as_tuple(exceltime, 0)

    return data

data = parse_file(datafile)
```

    
    List Comprehension
    data[3][2]: 1036.088697
    
    Cells in a nested loop:
    41277.0833333 9238.73731 1438.20528 1565.442856 916.708348 14010.903488 3027.98334 6165.211119 1157.741663 37520.933404 
    ROWS, COLUMNS, and CELLS:
    Number of rows in the sheet: 7296
    Type of data in cell (row 3, col 2): 2
    Value in cell (row 3, col 2): 1036.088697
    Get a slice of values in column 3, from rows 1-3:
    [1411.7505669999982, 1403.4722870000019, 1395.053150000001]
    
    DATES:
    Type of data in cell (row 1, col 0): 3
    Time in Excel format: 41275.0416667
    Convert time to a Python datetime tuple, from the Excel float: (2013, 1, 1, 1, 0, 0)
    

### quiz


```python
#!/usr/bin/env python
"""
Your task is as follows:
- read the provided Excel file
- find and return the min, max and average values for the COAST region
- find and return the time value for the min and max entries
- the time values should be returned as Python tuples

Please see the test function for the expected return format
"""

import xlrd
from zipfile import ZipFile
datafile = "2013_ERCOT_Hourly_Load_Data.xls"

def parse_file(datafile):
    workbook = xlrd.open_workbook(datafile)
    sheet = workbook.sheet_by_index(0)
    sheet_data = [[sheet.cell_value(r, col) for col in range(sheet.ncols)] for r in range(sheet.nrows)]
    
    #coast_list = []
    #for i in range(1,sheet.nrows):
    #    coast_list.append(sheet_data[i][1])
    coast_list = sheet.col_values(1, start_rowx=1, end_rowx=None) #This is the same as for-loop above, while more easy to read
    
    
    maxvalue = max(coast_list)
    maxvalue_pos = coast_list.index(maxvalue) + 1 #when we calcuate coast_list，we ignore the first row, 
                                                    #while when we back to find time in the whole sheet, we need to add 1
    
    #maxtime = xlrd.xldate_as_tuple(sheet_data[maxvalue_pos][0],0)
    maxtime = xlrd.xldate_as_tuple(sheet.cell_value(maxvalue_pos,0),0) #same as the above, but use sheet.cell_value directly
    
    minvalue = min(coast_list)
    minvalue_pos = coast_list.index(minvalue) + 1 
    #mintime = xlrd.xldate_as_tuple(sheet_data[minvalue_pos+1][0],0)
    mintime = xlrd.xldate_as_tuple(sheet.cell_value(minvalue_pos,0),0)
    
    data = {
            'maxtime': maxtime,
            'maxvalue': maxvalue,
            'mintime': mintime,
            'minvalue': minvalue,
            'avgcoast': sum(coast_list)/len(coast_list)
    }
    return data    
```


```python
data= parse_file(datafile)
import pprint
pprint.pprint(data)
```

    {'avgcoast': 10976.933460679751,
     'maxtime': (2013, 8, 13, 17, 0, 0),
     'maxvalue': 18779.025510000003,
     'mintime': (2013, 2, 3, 4, 0, 0),
     'minvalue': 6602.113898999982}
    

[the introduction of pprint](https://docs.python.org/2/library/pprint.html)

# 4. Use JSON


```python
# To experiment with this code freely you will have to run this code locally.
# Take a look at the main() function for an example of how to use the code.
# We have provided example json output in the other code editor tabs for you to
# look at, but you will not be able to run any queries through our UI.
import json
import requests

BASE_URL = "http://musicbrainz.org/ws/2/"
ARTIST_URL = BASE_URL + "artist/"

# query parameters are given to the requests.get function as a dictionary; this
# variable contains some starter parameters.
query_type = {  "simple": {},
                "atr": {"inc": "aliases+tags+ratings"},
                "aliases": {"inc": "aliases"},
                "releases": {"inc": "releases"}}


def query_site(url, params, uid="", fmt="json"):
    # This is the main function for making queries to the musicbrainz API.
    # A json document should be returned by the query.
    params["fmt"] = fmt
    r = requests.get(url + uid, params=params)
    print "requesting", r.url

    if r.status_code == requests.codes.ok:
        return r.json()
    else:
        r.raise_for_status()


def query_by_name(url, params, name):
    # This adds an artist name to the query parameters before making
    # an API call to the function above.
    params["query"] = "artist:" + name
    return query_site(url, params)


def pretty_print(data, indent=4):
    # After we get our output, we can format it to be more readable
    # by using this function.
    if type(data) == dict:
        print json.dumps(data, indent=indent, sort_keys=True)
    else:
        print data
```


```python
# how many bands named "First Aid Kit"
def main():
    results = query_by_name(ARTIST_URL, query_type["simple"], "FIRST AID KID")
    for i in range(0,len(results["artists"])):
        if results["artists"][i]["name"] == "First Aid Kit":
            print results["artists"][i]["name"]

if __name__ == '__main__':
    main()
```

    requesting http://musicbrainz.org/ws/2/artist/?query=artist%3AFIRST+AID+KID&fmt=json
    First Aid Kit
    First Aid Kit
    


```python
# begin-area name for queen
def main():
    results = query_by_name(ARTIST_URL, query_type["simple"], "Queen")
    for i in range(0,len(results["artists"])):
        if results["artists"][i]["name"] == "Queen":
            if "begin-area" in results["artists"][i].keys():
                pretty_print(results["artists"][i]["begin-area"]["name"])

if __name__ == '__main__':
    main()
```

    requesting http://musicbrainz.org/ws/2/artist/?query=artist%3AQueen&fmt=json
    London
    


```python
def main():
    results = query_by_name(ARTIST_URL, query_type["simple"], "Nirvana")
    #pretty_print(results)
    for i in range(0,len(results["artists"])):
        if results["artists"][i]["name"] == "Nirvana":
            if "disambiguation" in results["artists"][i].keys():
                if "tags" in results["artists"][i].keys():
                    for n in range(0,len(results["artists"][i]["tags"])):
                        if results["artists"][i]["tags"][n]["name"] == "kurt cobain":
                            pretty_print(results["artists"][i]["disambiguation"])

if __name__ == '__main__':
    main()
```

    requesting http://musicbrainz.org/ws/2/artist/?query=artist%3ANirvana&fmt=json
    90s US grunge band
    


```python
# Spanish alias for Beatles?

def main():
    results = query_by_name(ARTIST_URL, query_type["simple"], "BEATLES")
    for i in range(0,len(results["artists"])):
        #pretty_print(results["artists"][i])   
        if "aliases" in results["artists"][i]:      
            for a in range(0,len(results["artists"][i]["aliases"])):
                pretty_print(results["artists"][i]["aliases"][a]["name"])

if __name__ == '__main__':
    main()
```

    requesting http://musicbrainz.org/ws/2/artist/?query=artist%3ABEATLES&fmt=json
    더 비틀즈
    ザ・ビートルズ
    B
    Be
    Beat
    Beatles
    Beetles
    fab four
    Los Beatles
    The Beatles
    The Savage Young Beatles
    Битлз
    披头士
    披頭四
    Tape-Beatles
    Beatles Revival Band
    

# TEST 1


```python
#!/usr/bin/env python
"""
Your task is to process the supplied file and use the csv module to extract data from it.
The data comes from NREL (National Renewable Energy Laboratory) website. Each file
contains information from one meteorological station, in particular - about amount of
solar and wind energy for each hour of day.

Note that the first line of the datafile is neither data entry, nor header. It is a line
describing the data source. You should extract the name of the station from it.

The data should be returned as a list of lists (not dictionaries).
You can use the csv modules "reader" method to get data in such format.
Another useful method is next() - to get the next line from the iterator.
You should only change the parse_file function.
"""
import csv
import os

DATADIR = ""
DATAFILE = "745090.csv"


def parse_file(datafile):
    data = []
    with open(datafile,'rb') as f:
        reader = csv.reader(f) #载入datafile
        name = reader.next()[1] #读取下一行的处于1位子的元素，因为前面没有读取过，所以其实就是读取第一行
        reader.next() #读取下一行，就是第二行，不作任何处理，其实就是跳过第二行
        for row in reader: #开始for，是从第三行开始的
            data.append(row)
        print data
    return (name, data)

def test():
    datafile = os.path.join(DATADIR, DATAFILE)
    name, data = parse_file(datafile)

    assert name == "MOUNTAIN VIEW MOFFETT FLD NAS"
    assert data[0][1] == "01:00"
    assert data[2][0] == "01/01/2005"
    assert data[2][5] == "2"


#if __name__ == "__main__":
    #test()
```

# TEST2: Excel to CSV


```python
import xlrd
import os
import csv
from zipfile import ZipFile

datafile = "2013_ERCOT_Hourly_Load_Data.xls"
outfile = "2013_Max_Loads.csv"


def open_zip(datafile):
    with ZipFile('{0}.zip'.format(datafile), 'r') as myzip:
        myzip.extractall()
```


```python
def parse_file(datafile):
    workbook = xlrd.open_workbook(datafile)
    sheet = workbook.sheet_by_index(0)
    header = 'Station,Year,Month,Day,Hour,Max Load'.split(',')
    data = [header] #第一行包括标题
    #i = 1
    #while i < len(sheet.row_values(0))-1:
    for i in range(1,sheet.ncols):
        column = sheet.col_values(i, start_rowx=1, end_rowx=None)
        name = str(sheet.cell_value(0, i))
        max_load = max(column)
        max_place = column.index(max_load)+1
        max_time = xlrd.xldate_as_tuple(sheet.cell_value(max_place, 0), 0)
        row = [name, max_time[0], max_time[1], max_time[2], max_time[3], max_load]
        data.append(row)
        i += 1
    return data

def save_file(data, filename): 
    csvfile = file(filename, 'wb')
    writer = csv.writer(csvfile,delimiter='|')
    writer.writerows(data)
```


```python
a = parse_file(datafile)
save_file(a, "hello122211ddddd.csv")
```

老师给的范例解法

```
def parse_file(datafile):
    workbook = xlrd.open_workbook(datafile)
    sheet = workbook.sheet_by_index(0)
    data = {}
    # process all rows that contain station data
    for n in range (1, 9):
        station = sheet.cell_value(0, n)
        cv = sheet.col_values(n, start_rowx=1, end_rowx=None)

        maxval = max(cv)
        maxpos = cv.index(maxval) + 1
        maxtime = sheet.cell_value(maxpos, 0)
        realtime = xlrd.xldate_as_tuple(maxtime, 0)
        data[station] = {"maxval": maxval,
                         "maxtime": realtime}

    print data
    return data

def save_file(data, filename):
    with open(filename, "w") as f:
        w = csv.writer(f, delimiter='|')
        w.writerow(["Station", "Year", "Month", "Day", "Hour", "Max Load"])
        for s in data: #s其实就是data[station]的station名
            year, month, day, hour, _ , _= data[s]["maxtime"]
            
            #关于这个的解释，可以(参考)https://discussions.udacity.com/t/excel-to-csv-write-to-csv-explanation-of-line/186271/2?u=clarkyu，其实就是tuple的每一个值被逐一赋值
            w.writerow([s, year, month, day, hour, data[s]["maxval"]])
            
```
[参考](https://discussions.udacity.com/t/excel-to-csv-write-to-csv-explanation-of-line/186271/2?u=clarkyu)

# Test3 JOSN


```python
def pretty_print(data, indent=4):
    # After we get our output, we can format it to be more readable
    # by using this function.
    if type(data) == dict:
        print json.dumps(data, indent=indent, sort_keys=True)
    else:
        print data
```


```python
#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
This exercise shows some important concepts that you should be aware about:
- using codecs module to write unicode files
- using authentication with web APIs
- using offset when accessing web APIs

To run this code locally you have to register at the NYTimes developer site 
and get your own API key. You will be able to complete this exercise in our UI
without doing so, as we have provided a sample result. (See the file 
'popular-viewed-1.json' from the tabs above.)

Your task is to modify the article_overview() function to process the saved
file that represents the most popular articles (by view count) from the last
day, and return a tuple of variables containing the following data:
- labels: list of dictionaries, where the keys are the "section" values and
  values are the "title" values for each of the retrieved articles.
- urls: list of URLs for all 'media' entries with "format": "Standard Thumbnail"

All your changes should be in the article_overview() function. See the test() 
function for examples of the elements of the output lists.
The rest of functions are provided for your convenience, if you want to access
the API by yourself.
"""
import json
import codecs
import requests
import pprint

URL_MAIN = "http://api.nytimes.com/svc/"
URL_POPULAR = URL_MAIN + "mostpopular/v2/"
API_KEY = { "popular": "1f4cb0bcf60d4dab92d2ada23a264050",
            "article": "1f4cb0bcf60d4dab92d2ada23a264050"}  #自己的key


#def get_from_file(kind, period):
 #   filename = "popular-{0}-{1}.json".format(kind, period)
  #  with open(filename, "r") as f:
   #     return json.loads(f.read())


def article_overview(url,kind, days):
    data = get_popular(url, kind, days, section="all-sections", offset=0)["results"]
    
    titles = []
    urls =[]
    #pretty_print(data[0])
    for s in data:
        section = s["section"]
        title = s["title"]
        titles.append({section: title})
        
        for i in s["media"]:
            for t in i["media-metadata"]:
                if t["format"] == "Standard Thumbnail":
                    urls.append(t["url"])
    return (titles, urls)


def query_site(url, target, offset):
    # This will set up the query with the API key and offset
    # Web services often use offset paramter to return data in small chunks
    # NYTimes returns 20 articles per request, if you want the next 20
    # You have to provide the offset parameter
    if API_KEY["popular"] == "" or API_KEY["article"] == "":
        print "You need to register for NYTimes Developer account to run this program."
        print "See Intructor notes for information"
        return False
    params = {"api-key": API_KEY[target], "offset": offset}
    r = requests.get(url, params = params)

    if r.status_code == requests.codes.ok:
        return r.json()
    else:
        r.raise_for_status()


def get_popular(url, kind, days, section="all-sections", offset=0):
    # This function will construct the query according to the requirements of the site
    # and return the data, or print an error message if called incorrectly
    if days not in [1,7,30]:
        print "Time period can be 1,7, 30 days only"
        return False
    if kind not in ["viewed", "shared", "emailed"]:
        print "kind can bae only one of viewed/shared/emailed"
        return False

    url += "most{0}/{1}/{2}.json".format(kind, section, days)
    data = query_site(url, "popular", offset)

    return data


def save_file(kind, period):
    # This will process all results, by calling the API repeatedly with supplied offset value,
    # combine the data and then write all results in a file.
    data = get_popular(URL_POPULAR, "viewed", 1)
    num_results = data["num_results"]
    full_data = []
    with codecs.open("popular-{0}-{1}.json".format(kind, period), encoding='utf-8', mode='w') as v:
        for offset in range(0, num_results, 20):        
            data = get_popular(URL_POPULAR, kind, period, offset=offset)
            full_data += data["results"]
        
        v.write(json.dumps(full_data, indent=2))


```


```python
#get_popular(URL_POPULAR, "viewed", 7, section="all-sections", offset=0)
article_overview(URL_POPULAR,"viewed", 7)
```




    ([{u'Arts': u'Mariah Carey\u2019s Manager Blames Producers for New Year\u2019s Eve Debacle'},
      {u'Travel': u'52 Places to Go in 2017'},
      {u'Business Day': u'Megyn Kelly\u2019s Jump to NBC From Fox News Will Test Her, and the Networks'},
      {u'U.S.': u'With No Warning, House Republicans Vote to Gut Independent Ethics Office'},
      {u'U.S.': u'Putin Ordered \u2018Influence Campaign\u2019 Aimed at U.S. Election, Report Says'},
      {u'U.S.': u'Florida Airport Assailant May Have Heard Voices Urging Violence, Officials Say'},
      {u'U.S.': u'In Break With Precedent, Obama Envoys Are Denied Extensions Past Inauguration Day'},
      {u'U.S.': u'Putin Led a Complex Cyberattack Scheme to Aid Trump, Report Finds'},
      {u'U.S.': u'Turmoil Overshadows First Day of Republican-Controlled Congress'},
      {u'U.S.': u'Dan Coats Expected to Be Named Intelligence Director'},
      {u'Magazine': u'One Man\u2019s Quest to Change the Way We Die'},
      {u'U.S.': u'House Republicans, Under Fire, Back Down on Gutting Ethics Office'},
      {u'U.S.': u'Trump Promises a Revelation on Hacking'},
      {u'Opinion': u'Rumors of Hillary Clinton\u2019s Comeback'},
      {u'Opinion': u'Hipsters Broke My Gaydar'},
      {u'Opinion': u'A Month Without Sugar'},
      {u'Opinion': u'Why Rural America Voted for Trump'},
      {u'U.S.': u'Trump Calls for Closer Relationship Between U.S. and Russia'},
      {u'U.S.': u'Jared Kushner, a Trump In-Law and Adviser, Chases a Chinese Deal'},
      {u'Opinion': u'How to Become a \u2018Superager\u2019'}],
     [u'https://static01.nyt.com/images/2017/01/02/arts/music/02mariah-photo1/02mariah-photo1-thumbStandard-v2.jpg',
      u'https://static01.nyt.com/images/2017/01/03/travel/places-to-visit-1483468095725/places-to-visit-1483468095725-thumbStandard-v10.jpg',
      u'https://static01.nyt.com/images/2017/01/04/business/04kelly1/04kelly1-thumbStandard-v2.jpg',
      u'https://static01.nyt.com/images/2017/01/03/us/03ethics/03ethics-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/07/us/07hack/07hack-thumbStandard-v2.jpg',
      u'https://static01.nyt.com/images/2017/01/06/us/07lauderdale-hp-slide-ZHG5/07lauderdale-hp-slide-ZHG5-thumbStandard-v3.jpg',
      u'https://static01.nyt.com/images/2017/01/06/us/06AMBASSADORS1/06AMBASSADORS1-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/07/us/07trump3_hp/07trump3_hp-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/04/us/04cong-1/04cong-1-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/06/us/06transitionbriefing/06transitionbriefing-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/08/magazine/08miller1/08miller1-thumbStandard-v2.jpg',
      u'https://static01.nyt.com/images/2017/01/04/us/04ethics/04ethics-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/01/us/01trump_web1/01trump_web1-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/08/opinion/sunday/08bruni/08bruni-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/01/opinion/sunday/01burton/01burton-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/01/opinion/01leon-sugar-promo/01leon-sugar-promo-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/05/opinion/05leonardWeb/05leonardWeb-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/07/us/08TRUMP/08TRUMP-thumbStandard.jpg',
      u'https://static01.nyt.com/images/2017/01/08/us/08KUSHNER/08KUSHNER-thumbStandard-v4.jpg',
      u'https://static01.nyt.com/images/2017/01/01/opinion/sunday/01gray/01gray-thumbStandard-v2.jpg'])


