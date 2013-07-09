'''Sortuje + liczy inwersje'''
data = list()
try:
    file = open("E:\\coursera\\algo part1\\program\\IntegerArray.txt")
    for line in file:
        data.append(int(line))
finally:
    file.close()

global count
count = 0

def merge(left, right):
    global count
    result = list()
    i = 0
    j = 0
    k = 0
    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            count += len(left[i:])
            j += 1
    result += left[i:]
    result += right[j:]
    return result

def merge_sort(L):
    if len(L) <= 1:
        return L
    else:
        left = list()
        right = list()
        middle = len(L) // 2
        left = merge_sort(L[:middle])
        right = merge_sort(L[middle:])
        return merge(left, right)


lista = [1,4,6,2,3,9,7,5]
merge_sort(data)
print(count)
