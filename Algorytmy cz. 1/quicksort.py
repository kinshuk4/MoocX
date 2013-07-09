'''quicksort'''
data = list()
try:
    file = open("E:\\coursera\\algo part1\\program\\QuickSort.txt")
    for line in file:
        data.append(int(line))
finally:
    file.close()

count = int()
def QuickSort(L, n):
    global count
    if n <= 1:
        return L
    '''I = n - 1
    L[0], L[n - 1] = L[n - 1], L[0]'''
    ''' 164123 '''
    if n % 2 == 1:
        if (L[0] < L[n // 2] and L[n // 2] < L[n - 1]) or (L[0] > L[n // 2] and L[n // 2] > L[n - 1]):
            L[n // 2], L[0] = L[0], L[n // 2]
        elif (L[n - 1] > L[0] and L[n - 1] <= L[n // 2]) or (L[n - 1] < L[0] and L[n - 1] >= L[n // 2]):
            L[n - 1], L[0] = L[0], L[n - 1]
    else:
        if (L[0] < L[n // 2 - 1] and L[n // 2 - 1] < L[n - 1]) or (L[0] > L[n // 2 - 1] and L[n // 2 - 1] > L[n - 1]):
            L[n // 2 - 1], L[0] = L[0], L[n // 2 - 1]
        elif (L[n - 1] > L[0] and L[n - 1] <= L[n // 2 - 1]) or (L[n - 1] < L[0] and L[n - 1] >= L[n // 2 - 1]):
            L[n - 1], L[0] = L[0], L[n - 1]
    I = 0
    ''' 162085 '''
    L, I = Partition(L, I, n)
    temp = QuickSort(L[:(I)], len(L[:(I)]))
    temp.append(L[I])
    temp1 = QuickSort(L[(I + 1):], len(L[(I + 1):]))
    for i in temp1:
        temp.append(i)
    return temp

def Partition(L, I, r):
    global count
    i = I + 1
    for j in range(I + 1, r):
        count += 1
        if L[j] < L[I]:
            L[j], L[i] = L[i], L[j]
            i += 1
    L[I], L[i - 1] = L[i - 1], L[I]
    return L, i - 1

data = QuickSort(data, len(data))
print(count)

