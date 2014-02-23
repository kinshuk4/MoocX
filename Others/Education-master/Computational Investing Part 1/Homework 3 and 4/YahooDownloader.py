import urllib2
import urllib
import datetime
import sys
import os

def get_data(data_path, ls_symbols):

    if not (os.access(data_path, os.F_OK)):
        os.makedirs(data_path)
    
    missed = 0
	now =datetime.datetime.now();
    
	for symbol in ls_symbols:
        symbol_name = symbol
        if symbol[0] == '$':
            symbol = '^' + symbol[1:]

        symbol_data=list()
		
        print "Getting " + str (symbol_name)		

        try:
            params= urllib.urlencode ({'a':1, 'b':1, 'c':2000, 'd':now.month, 'e':now.day, 'f':now.year, 's': str(symbol)})
            url_get= urllib2.urlopen("http://ichart.finance.yahoo.com/table.csv?%s" % params)
            
            header= url_get.readline()
            symbol_data.append (url_get.readline())
			
            while (len(symbol_data[-1]) > 0):
                symbol_data.append(url_get.readline())
            
            symbol_data.pop(-1)

            f = open (data_path + symbol_name + ".csv", 'w')
            f.write (header)
            
            while (len(symbol_data) > 0):
                f.write (symbol_data.pop(0))
             
            f.close();    
 
        except urllib2.HTTPError:
            missed = missed + 1
			print "Unable to fetch data for stock: " + str (symbol_name)
			
        except urllib2.URLError:
            print "URL Error for stock: " + str (symbol_name)
            
    print "All done. Got " + str (len(ls_symbols) - missed) + " stocks. Could not get " + str (missed) + " stocks."   

def read_symbols(s_symbols_file):

    ls_symbols=[]
    file = open(s_symbols_file, 'r')
	
    for line in file.readlines():
        i = line[:-1]
        ls_symbols.append(i)
    file.close()
    
    return ls_symbols  

def main():

    path = './'
    ls_symbols = read_symbols('symbols.txt')
    get_data(path, ls_symbols)

if __name__ == '__main__':
    main()
