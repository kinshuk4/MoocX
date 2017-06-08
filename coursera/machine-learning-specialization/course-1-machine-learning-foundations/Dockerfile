FROM jupyter/notebook 
MAINTAINER Krzysztof Grodzicki <kgrodzicki@gmail.com>
VOLUME /tmp
COPY GraphLab-Create-License.tar.gz /tmp/
RUN pip2 install --upgrade --no-cache-dir /tmp/GraphLab-Create-License.tar.gz
RUN apt-get -y update && \
    apt-get install -y python-matplotlib && \
    apt-get clean
