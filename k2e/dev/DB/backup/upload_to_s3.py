#!/usr/bin/env python
import os
import argparse

import boto
from boto.s3.key import Key
import boto.s3.connection


parser = argparse.ArgumentParser()
parser.add_argument("bucket", help="The S3 bucket for the file upload")
parser.add_argument("filename", help="The file to upload to S3")
parser.add_argument("-r", "--region", help="The AWS region (default: ap-southeast-2)")
parser.add_argument("-v", "--version", help="Adds the supplied version into the filename with the format: filename.version.extension")
parser.add_argument("-d", "--dest", help="Destination location relative to the S3 bucket. If you wanted the file to "
                                         "be located at '/<bucket>/some/location/filename.ext' then you would supply "
                                         "'some/location/filename.ext' to this parameter. Using the -v flag will still "
                                         "modify the filename if specified")

args = parser.parse_args()

try:
    fp = open(args.filename, 'r')
except IOError:
    print "File does not exist"

if not args.region:
    args.region = 'ap-southeast-2'

if args.version:
    if args.dest:
        path = os.path.dirname(args.dest)
        filename_parts = os.path.splitext(os.path.basename(args.dest))
        path_with_filename = "%s/%s" % (path, filename_parts[0])
        file_parts = (path_with_filename, filename_parts[1])
    else:
        file_parts = os.path.splitext(os.path.basename(args.filename))

    filename = "%s.%s%s" % (file_parts[0], args.version, file_parts[1])
else:
    if args.dest:
        filename = args.dest
    else:
        filename = os.path.basename(args.filename)

conn = boto.s3.connect_to_region(args.region, calling_format=boto.s3.connection.OrdinaryCallingFormat())

bucket = conn.lookup(args.bucket)
if bucket is None:
    bucket = conn.create_bucket(args.bucket)

k = Key(bucket)
k.key = filename
k.set_contents_from_file(fp)

#https://gist.github.com/nigeldunn/ea245d14eed17ee8c989
