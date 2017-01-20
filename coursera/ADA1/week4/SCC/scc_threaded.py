import pprint
import sys
import threading

#Code has become very ugly. Looks like a hack job.

def createGraph(mp, tail, head):
    if tail in mp:
        mp[tail].append((tail, head))
    else:
        mp[tail] = [(tail, head)]


def dfsForTime(vert, seen, vertTimeMap):
    if vert in seen:
        return False
        
    seen.add(vert)

    global _timer
    if vert in fGraph:
        edges = fGraph[vert]
        for edge in edges:
            dfsForTime(edge[1], seen, vertTimeMap)

        _timer = _timer + 1
        vertTimeMap[vert] = _timer
        return True
    else:
        return False


def dfsForGrouping(vert, seen, traversedVertices):
    if vert in seen:
        return False
        
    seen.add(vert)
    traversedVertices.add(vert)

    if vert in rGraph:
        edges = rGraph[vert]
        for edge in edges:
            dfsForGrouping(edge[1], seen, traversedVertices)

vertTimeMap = {}
fGraph = {}
rGraph = {}

_timer = 0

def main():
    fh = open('testSCC2.txt')
    #fh = open('tstCase0.txt')
    #n = 12
    #print n, "=>"
    #fh = open('PQ4GraphSCC12TestCases\\graph{0}.txt'.format(n))

    lines = fh.readlines()


    mxVert = 0

    for line in lines:
        line = line.strip()
        vertices = line.split()

        v0 = long(vertices[0])
        v1 = long(vertices[1])

        if v0 == v1:
            continue

        createGraph(fGraph, v0, v1)
        createGraph(rGraph, v1, v0)

        mxVert = max(mxVert, v0, v1)

    global _timer

    seen = set()
    for vert in reversed(xrange(1, mxVert + 1)):
        if vert not in fGraph:
            continue

        if not dfsForTime(vert, seen, vertTimeMap):
            if not vert in vertTimeMap:
                _timer = _timer + 1
                vertTimeMap[vert] = _timer


    timeVertMap = dict([[v,k] for k,v in vertTimeMap.items()])

    seen = set()
    sccSize = []

    maxTime = max(timeVertMap.keys())
    for time in reversed(xrange(1, maxTime + 1)):
        vert = timeVertMap[time]
        traversedVertices = set()
        dfsForGrouping(vert, seen, traversedVertices)
        sccSize.append(len(traversedVertices))

    sccSize.sort()
    sccSize.reverse()

    pprint.pprint(sccSize[0:5])

if __name__=="__main__":
    sys.setrecursionlimit(100000)
    threading.stack_size(128 * 1024 * 1024) # 128 MB
    thread = threading.Thread(target=main)
    thread.start()
    thread.join(0)