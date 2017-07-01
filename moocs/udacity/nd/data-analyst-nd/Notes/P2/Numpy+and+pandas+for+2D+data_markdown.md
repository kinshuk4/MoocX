

```python
import numpy as np
import seaborn
%pylab inline


# Subway ridership for 5 stations on 10 different days
ridership = np.array([
    [   0,    0,    2,    5,    0],
    [1478, 3877, 3674, 2328, 2539],
    [1613, 4088, 3991, 6461, 2691],
    [1560, 3392, 3826, 4787, 2613],
    [1608, 4802, 3932, 4477, 2705],
    [1576, 3933, 3909, 4979, 2685],
    [  95,  229,  255,  496,  201],
    [   2,    0,    1,   27,    0],
    [1438, 3785, 3589, 4174, 2215],
    [1342, 4043, 4009, 4665, 3033]
])
```

    Populating the interactive namespace from numpy and matplotlib
    


```python
# Accessing elements
if True:
    print ridership[1, 3]
    print ridership[1:3, 3:5]
    print ridership[1, :]
```

    2328
    [[2328 2539]
     [6461 2691]]
    [1478 3877 3674 2328 2539]
    


```python
# Vectorized operations on rows or columns
if True:
    print ridership[0, :] + ridership[1, :]
    print ridership[:, 0] + ridership[:, 1] #最后变成一组横的数列
```

    [1478 3877 3676 2333 2539]
    [   0 5355 5701 4952 6410 5509  324    2 5223 5385]
    


```python
# Vectorized operations on entire arrays
if True:
    a = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
    b = np.array([[1, 1, 1], [2, 2, 2], [3, 3, 3]])
    print a + b
```

    Populating the interactive namespace from numpy and matplotlib
    [[ 2  3  4]
     [ 6  7  8]
     [10 11 12]]
    


```python
def mean_riders_for_max_station(ridership):
    '''
    Fill in this function to find the station with the maximum riders on the
    first day, then return the mean riders per day for that station. Also
    return the mean ridership overall for comparsion.
    
    Hint: NumPy's argmax() function might be useful:
    http://docs.scipy.org/doc/numpy/reference/generated/numpy.argmax.html
    '''
    max_pos = ridership[0,:].argmax()
    overall_mean =  ridership.mean()
    mean_for_max = ridership[:, max_pos].mean()
    
    return (overall_mean, mean_for_max)

print mean_riders_for_max_station(ridership)
```

    (2342.5999999999999, 3239.9000000000001)
    


```python
# NumPy axis argument
if True:
    a = np.array([
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]
    ])
    
    print a.sum()
    print a.sum(axis=0) #纵轴相加
    print a.sum(axis=1) #横轴相加
```

    45
    [12 15 18]
    [ 6 15 24]
    


```python
def min_and_max_riders_per_day(ridership):
    '''
    Fill in this function. First, for each subway station, calculate the
    mean ridership per day. Then, out of all the subway stations, return the
    maximum and minimum of these values. That is, find the maximum
    mean-ridership-per-day and the minimum mean-ridership-per-day for any
    subway station.
    '''
    mean_ridership_per_station = ridership.mean(axis=0)
    max_daily_ridership = mean_ridership_per_station.max()
    min_daily_ridership = mean_ridership_per_station.min()  
                                                        
    return (max_daily_ridership, min_daily_ridership)
```


```python
min_and_max_riders_per_day(ridership)
```




    (3239.9000000000001, 1071.2)




```python
import pandas as pd
# Subway ridership for 5 stations on 10 different days
ridership_df = pd.DataFrame(
    data=[[   0,    0,    2,    5,    0],
          [1478, 3877, 3674, 2328, 2539],
          [1613, 4088, 3991, 6461, 2691],
          [1560, 3392, 3826, 4787, 2613],
          [1608, 4802, 3932, 4477, 2705],
          [1576, 3933, 3909, 4979, 2685],
          [  95,  229,  255,  496,  201],
          [   2,    0,    1,   27,    0],
          [1438, 3785, 3589, 4174, 2215],
          [1342, 4043, 4009, 4665, 3033]],
    index=['05-01-11', '05-02-11', '05-03-11', '05-04-11', '05-05-11',
           '05-06-11', '05-07-11', '05-08-11', '05-09-11', '05-10-11'],
    columns=['R003', 'R004', 'R005', 'R006', 'R007']
)
```


```python
ridership_df.mean()
```




    R003    1071.2
    R004    2814.9
    R005    2718.8
    R006    3239.9
    R007    1868.2
    dtype: float64




```python
ridership_df.values.mean()  #!!!注意了！这里的values后面是没有（）的！！！所有的加起来 其实就是ridership_df.mean().mean()
```




    2342.5999999999999




```python
# Change False to True for each block of code to see what it does

# DataFrame creation
if True:
    # You can create a DataFrame out of a dictionary mapping column names to values
    df_1 = pd.DataFrame({'A': [0, 1, 2], 'B': [3, 4, 5]})
    print df_1

    # You can also use a list of lists or a 2D NumPy array
    df_2 = pd.DataFrame([[0, 1, 2], [3, 4, 5]], columns=['A', 'B', 'C'])
    print df_2
```

       A  B
    0  0  3
    1  1  4
    2  2  5
       A  B  C
    0  0  1  2
    1  3  4  5
    


```python
ridership_df
```




<div>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>R003</th>
      <th>R004</th>
      <th>R005</th>
      <th>R006</th>
      <th>R007</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>05-01-11</th>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>5</td>
      <td>0</td>
    </tr>
    <tr>
      <th>05-02-11</th>
      <td>1478</td>
      <td>3877</td>
      <td>3674</td>
      <td>2328</td>
      <td>2539</td>
    </tr>
    <tr>
      <th>05-03-11</th>
      <td>1613</td>
      <td>4088</td>
      <td>3991</td>
      <td>6461</td>
      <td>2691</td>
    </tr>
    <tr>
      <th>05-04-11</th>
      <td>1560</td>
      <td>3392</td>
      <td>3826</td>
      <td>4787</td>
      <td>2613</td>
    </tr>
    <tr>
      <th>05-05-11</th>
      <td>1608</td>
      <td>4802</td>
      <td>3932</td>
      <td>4477</td>
      <td>2705</td>
    </tr>
    <tr>
      <th>05-06-11</th>
      <td>1576</td>
      <td>3933</td>
      <td>3909</td>
      <td>4979</td>
      <td>2685</td>
    </tr>
    <tr>
      <th>05-07-11</th>
      <td>95</td>
      <td>229</td>
      <td>255</td>
      <td>496</td>
      <td>201</td>
    </tr>
    <tr>
      <th>05-08-11</th>
      <td>2</td>
      <td>0</td>
      <td>1</td>
      <td>27</td>
      <td>0</td>
    </tr>
    <tr>
      <th>05-09-11</th>
      <td>1438</td>
      <td>3785</td>
      <td>3589</td>
      <td>4174</td>
      <td>2215</td>
    </tr>
    <tr>
      <th>05-10-11</th>
      <td>1342</td>
      <td>4043</td>
      <td>4009</td>
      <td>4665</td>
      <td>3033</td>
    </tr>
  </tbody>
</table>
</div>




```python
# Accessing elements
if True:
    print ridership_df.iloc[0] #iol指定相应的row
    print ridership_df.loc['05-05-11'] #指定相应的row，索引的是字符行
    print ridership_df['R003']
    print ridership_df.iloc[1, 3] #row, column
```

    R003    0
    R004    0
    R005    2
    R006    5
    R007    0
    Name: 05-01-11, dtype: int64
    R003    1608
    R004    4802
    R005    3932
    R006    4477
    R007    2705
    Name: 05-05-11, dtype: int64
    05-01-11       0
    05-02-11    1478
    05-03-11    1613
    05-04-11    1560
    05-05-11    1608
    05-06-11    1576
    05-07-11      95
    05-08-11       2
    05-09-11    1438
    05-10-11    1342
    Name: R003, dtype: int64
    2328
    


```python
# Accessing multiple rows
if True:
    print ridership_df.iloc[1:4]
    
# Accessing multiple columns
if True:
    print ridership_df[['R003', 'R005']]
```

              R003  R004  R005  R006  R007
    05-02-11  1478  3877  3674  2328  2539
    05-03-11  1613  4088  3991  6461  2691
    05-04-11  1560  3392  3826  4787  2613
              R003  R005
    05-01-11     0     2
    05-02-11  1478  3674
    05-03-11  1613  3991
    05-04-11  1560  3826
    05-05-11  1608  3932
    05-06-11  1576  3909
    05-07-11    95   255
    05-08-11     2     1
    05-09-11  1438  3589
    05-10-11  1342  4009
    


```python
# Pandas axis
if True:
    df = pd.DataFrame({'A': [0, 1, 2], 'B': [3, 4, 5]})

df
```




<div>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>A</th>
      <th>B</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>3</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>4</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>5</td>
    </tr>
  </tbody>
</table>
</div>




```python
# Pandas axis
if True:
    df = pd.DataFrame({'A': [0, 1, 2], 'B': [3, 4, 5]})
    print df.sum() #默认是axis=0，按照column来统计，纵向求和
    print df.sum(axis=1) #横线求和
    print df.values.sum()#所有cell一起求和
```

    A     3
    B    12
    dtype: int64
    0    3
    1    5
    2    7
    dtype: int64
    15
    


```python
def mean_riders_for_max_station(ridership):
    '''
    Fill in this function to find the station with the maximum riders on the
    first day, then return the mean riders per day for that station. Also
    return the mean ridership overall for comparsion.
    
    This is the same as a previous exercise, but this time the
    input is a Pandas DataFrame rather than a 2D NumPy array.
    '''
    overall_mean = ridership.values.mean()
    mean_for_max = ridership[ridership.iloc[0].argmax()].mean()
    
    return (overall_mean, mean_for_max)

print (mean_riders_for_max_station(ridership_df)
```


      File "<ipython-input-36-81a2a5aaefcc>", line 15
        print (mean_riders_for_max_station(ridership_df)
                                                        ^
    SyntaxError: invalid syntax
    

