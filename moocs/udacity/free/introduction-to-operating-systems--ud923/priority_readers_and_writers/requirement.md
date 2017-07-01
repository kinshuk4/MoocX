### Priority Readers and Writers

Write a multi-threaded C program that gives readers priority over writers concerning a shared (global) variable. Essentially, if any readers are waiting, then they have priority over writer threads -- writers can only write when there are no readers. This program should adhere to the following constraints:

- Multiple readers/writers must be supported (5 of each is fine).
- Readers must read the shared variable X number of times.
- Writers must write the shared variable X number of times.
- Readers must print:
  - The value read.
  - The number of readers present when value is read.
- Writers must print:
  - The written value.
  - The number of readers present were when value is written (should be 0).
- Before a reader/writer attempts to access the shared variable it should wait some random amount of time. 
  - Note: This will help ensure that reads and writes do not occur all at once
- Use pthreads, mutexes, and condition variables to synchronize access to the shared variable