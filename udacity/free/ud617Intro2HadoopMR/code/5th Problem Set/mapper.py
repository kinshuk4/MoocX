#!/usr/bin/env python
# Find the total number of hits to the page /assets/js/the-associates.js.
#link : https://www.udacity.com/course/viewer#!/c-ud617/l-313947755/# #m-2465068684
import sys
import re

for line in sys.stdin:
    data = line.strip().split()
    if len(data) == 10:
        ip, id, user, datetime, timezone, method, path, proto, status, size = data
        print ip
