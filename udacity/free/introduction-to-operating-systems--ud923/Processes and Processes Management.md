# Process Definition

  Process is a stateful, active entity loaded and executed in main memory.

# Process Address Space

  1. Process States:

    a. Text and data
      Static data created when the process is loaded

    b. Heap
      Dynamically allocated memory block used by the process during execution

    c. Stack
      Also dynamically allocated but in a LIFO scheme.

  2. Virtual Addresses

    a. Memory allocated on the heap of processes is a mapping to the actual physical memory. A contiguous block of memory on the heap of processes may not be contiguous on physical memory.

    b. Operating System will keep information about mapping between virtual addresses and physical addresses.
    
    c. Virtual addresses are relative to the process itself, which means two processes can have the same addresses on their heap pointing to different physical memory.

# Process Control Block

  1. Process related states and data such as virtual memory mapping, register values, states, program counter and etc.

  2. PCB can be used to perform context switch by storing and restoring PCBs on and from the memory.

# Context Switch

  Switching between different processes on CPU.

  1. It has direct costs on storing and restoring PCBs.

  2. It also has indirect costs on resulting in cold caches.

# Process Lifecycle

  Process can be either **running** or **ready**.

  Process states:

  1. New
    a. If **admitted**, go to **2**

  2. Ready 
    a. If **scheduler dispatched**, go to **3**

  3. Running 
    a. If **I/O or event waiting**, go to **4**
    b. If **exit**, go to **5**
    c. If **interrupted**, go to **2**

  4. Waiting
    a. If **I/O or event completion**, go to **2**

  5. Terminated

# Process Creation

  1. fork
    Copy the PCB from the parent and the child will execute from the instuctions at the point

  2. exec 
    Create a new process with new PCB and data.

# CPU Scheduler 

  It's used to determine which ready process should be running on the CPU and how long should it runs.

  There are three steps it will do:

  1. Preempt
    Interrupt the current process and store its PCB and states.

  2. Schedule 
    Run the scheduler to choose the next ready process in the ready queue with a certain algorithm.

  3. Dispatch
    Switch into the new process's context.

# Inter Process Communication (IPC)

  1. Message channel

    The os maintains a message channel, i.e. an intermediate buffer for two processes to communicate.

      a. Pros: Identical APIs.

      b. Cons: This method will be costly because there will be lots of copying between processes and the buffer.

  2. Shared memory space

    A shared memory is mapped to process address space of both processes and two processes directly read or write to the memory.

    a. Pros: Under certain circumstance, the performance will be better than message channel. However the memory mapping can be expensive.
    
    b. Cons: The memory is not maintained by the os thus it does not have identical APIs to use. Also it may be error-prone.

