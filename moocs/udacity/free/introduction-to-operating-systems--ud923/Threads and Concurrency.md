# Process vs. Thread

  Processes have their own PCB or PVAS (Process Virtual Address Space).

  Threads share one PCB or PVAS in a process. Each thread has its own execution context.

# Multithreading Benefits

  1. Speed up the computation by parallelization.

  2. Specialize on different tasks so that computation can have hotter caches because a thread can maximize the usage of processor resources.

  3. For single-processor CPU, it can hide latancy. Sometimes after a task is executed, you have to wait for another system to return back the result. The waiting time, aka the idle time. The idle time can be used to perform context switch to another thread and perform some other tasks in that thread if the idle time is significantly longer than context switch time.

# Thread Creation

  1. Data structure to represent Thread type:
    
    a. Thread ID 
    
    b. PC 

    c. SP 

    d. Registers

    e. stack

    f. attributes

  2. Fork(procedure, args)

    a. Create a new thread to execute the **procedure** with specifies arguments.

    b. Not UNIX fork

  3. Join(thread)

    Execute the specified thread and block other threads until this thread finishes.

# Mutual Exclusion (Mutex)

  A mutex is acquired and locked before some critical operations which can only be performed by one thread at a time are about to be executed. After that, the mutex is released and unlocked. Other threads are not able to perform the critical operations until the mutex is released and unlocked by any other thread that is using it. This prevents data racing on critical resources.

# Condition Variable For Data Producers And Consumers

  Instead of checking for mutex usage and data states multiple times, consumers can first acquire the mutex, and start to wait for signals from producers when states change. In this method there are three apis: 

  1. wait(mutex, cond)

    Mutex is automatically released and re-acquire on wait.

  2. signal(cond)

    Notify only one thread that is waiting 

  3. broadcast(cond)

    Notify all threads that are waiting


# Readers and Writer Problem

  This problem describes a situation when there are 0 or more readers reading a resource while there is no more than 1 writer writing to the resource.

  1. If no readers and no writer is accessing the resource

    Any readers or writer is accepted.

  2. If any reader is accessing the resource

    Readers are accepted.

  3. If a writer is accessing the resource

    No readers or writer is accepted.

  Solution:

  Maintain a state counter. If the counter is 0, it means no readers or writer. If the counter is greater than 0, then there are some readers. Else if the counter is -1, then there is one writer.

# Critical Section Structure

  Before the critical section there is an enter block to encapsulate entering operations. After the critical section there is an exit block.

  1. The enter block (lock)

    Perform checking states, waiting for change of states and updating access states here.

  2. The exit block (unlock)

    Perform states clean-up and signal/broadcast here.

  What To Do To Avoid Common Mistakes:

  1. Explain what the mutex is for when you first create it.
  2. Check you are always using lock and unlock in pairs.
  3. Use a single mutex for a single resource.
  4. Make sure you are signaling the correct condition.
  5. Mutexes should be visible globally.

# Spurious Wake-Ups

  This problem occurs when some threads are signaled but still not able to acquire the lock, which means all waiting threads originally waiting for the signals now have to wait again. This adds some performance cost.

  Solution: Unlock before signaling or broadcasting other waiting threads.

# Deadlocks

  This problem occurs when more than 1 threads are waiting for each other to complete while none of them ever do.

  Solution:

  1. Assign orders to mutexes and check for order before acquiring every mutex. (expensive)
  2. Check for cycle and recover after a deadlock occurs

# Kernal vs. User-level Threads

  Kernal-level threads are those that run in the os and user-level threads are those that run in user applications.

  1. One to One Model

  2. Many to One Model 

  3. Many to Many Model

# Multithreading Patterns

  1. Boss-Workers Pattern 

    There is a boss thread responsible for dispatching and assigning tasks to worker threads. The boss thread assigns tasks in a **producer/consumer queue** and worker threads fetch tasks from the queue one by one.

    Pros:

      a. The boss doesn't need to know the details of workers 

    Cons:

      a. Queue synchronization problem 
      b. Thread pool management
      c. Locality

  2. Pipeline Pattern

    The system is divided into stages and threads are assigned to every stages. A task is divided into sub-tasks and one or more sub-tasks are handled by stages. Each stage has its own thread pool. Communication among all stages is done by using a buffer shared among stages.

    Pros:

      a. Specialization and Locality
        Every stage has its own specific sub-task.

    Cons:

      b. Balancing and synchronization.

  
# Thread Data Structure

  Split PCB into small blocks including User-level thread information, Kernal-level thread information, heavy process states(information related to the whole process) and light process states(information related to the kernal threads related to the process).

# Basic Thread Management Interaction

  If the all kernel-level threads are blocked, the kernel should signal the users, and users can ask for more LWP.

# Multi-CPU Issues

  1. Synchronization-related issue 

    When a thread wants to gain a mutex from another thread on another CPU, if the critical section is short, instead of pushing itself to the waiting queue, the thread can "spin" and wait for the mutex.


# Interrupts and Signals 

  1. Interrupts

    a. Events generated externally by other components other than CPU

    b. Determined based on the physical platform

    c. Appear asynchronously

    d. Per-CPU mask

  2. Signals

    a. Generated by CPUs or softwares running on the CPUs

    b. Can appear either synchronously or asynchronously

    c. Determined based on the operating system 

    d. Per-Process mask

  3. Common Features

    a. Both have a unique id

    b. Can be masked and disabled/suspended via corresponding mask

# Interrupts Handling

  1. A device sends a MSI to a running thread on the CPU and give the MSI an unique identity. 

  2. The unique identity is an index of a table in which you can find the starting address of the handler routine with the index.

  3. Set the program counter to the address and start to execute the routine.

# Signals Handling 

  1. The CPU sends a signal to some devices or processes and give the signal an unique identity.

  2. The same as interrupts handling. The only difference is that a process can have its own handler table.

  3. The same as interrupts handling.

  4. Processes can specify some handling routine for some kinds of signals.

# Disable Interrupts or Signals

  If the handler has to acquire a mutex to execute, then it will be a deadlock if the thread itself has already acquired the mutex.

  Solution:

  1. Keep handler code simple and away from mutex (too restrictive)

  2. Control interruptions by handler code (use interrupts/signals mask)

    Set a certain bit to 0 or 1 to disable or enable.

    a. If the thread has already acquired a mutex, the os will disable the interrupts.

    b. After the thread releases the mutex, the os will enable the interrupts again.

# Interrupt and Signal Masks

  1. Interrupt Masks are per CPU

    If a mask disables interrupts, then the hardware interrupt routing will not deliver interrupts to CPU

  2. Signal Masks are per execution context (ULT on top of KLT)

    If a mask disables signals, the OS will not send signals to corresponding threads.

# Types of Signals

  1. One-Shot Signals

    a. N signals with the same type will be handle as one signal.
    
    b. Custom handler must be explicitly re-enabled.
  
  2. Real-Time Signals

    a. The handler will fire N times if N signals occur.

# Interrupts as Threads

  Instead of handling interrupts as routines, they can be handled as threads. You can create separate threads for handlers and schedule the threads.

  However, dynamically create threads is expensive. Instead, we can allocate memory for handlers beforehand.
  