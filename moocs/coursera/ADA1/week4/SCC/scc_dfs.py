import sys
sys.setrecursionlimit(300000)
#dfs implementation
#0 univistied
#1 visited
#2 explored



#globals
visited={}
finish={}
leader={}

def init():
    for i in range(1,N+1):
        visited[i] = 0
        finish[i] = 0
        leader[i] = 0

#dfs function
def dfs(G, i):
    global t
    visited[i] = 1
    leader[i] = s
    for j in G[i]:
        if(visited[j] == 0): dfs(G,j)
    t = t + 1
    finish[i] = t


#visit the nodes
def dfs_loop(G):
    global t
    global s
    t = 0 #number of nodes processed so far
    s = 0 #current source vertex
    i = N
    while( i > 0):
        if(visited[i] == 0):
            s = i
            dfs(G, i)
        i = i - 1




#graph
G = { }
#transpose of graph
GT = { }

#max number of vertices
N = 875714

for i in range(1, N+1) :
    G[i] = []
    GT[i] = []

# read from file
script, filename = "","SCC.txt"
for line in open(filename,'r').readlines():    
    inputstr = []
    inputstr = line.split()
    x = int(inputstr[0])
    y = int(inputstr[1])
    G[x].append(y)
    GT[y].append(x)


#connected component logic
init()
dfs_loop(GT)

newGraph={}
for i in range(1,N+1):
       temp=[]
       for x in G[i]: temp.append(finish[x])
       newGraph[finish[i]] = temp
init()
dfs_loop(newGraph)

# statistics
a_list= sorted(leader.values())
stat=[]
pre=0
for i in range(0,N-1):
    if a_list[i] != a_list[i+1]:
        stat.append(i+1-pre)
        pre = i+1
stat.append(N-pre)
final_answer= sorted(stat)
final_answer.reverse()
print(final_answer[0:5])    