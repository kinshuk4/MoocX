# Performance Matrics

  Metrics is a measurement standard. 
  
    a. Should be measurable, quantifiable.

    b. Of the system we are interested in 

    c. that can be used to evaluate the system's behaviours

# Multi-process Web Server

  Sebsequent processes are spawned by the original process.

  1. Pros:

    a. Simple programming

  2. Cons

    a. High memory usage

    b. Costly context switch

    c. Hard and costly to maintain shared states

# Multi-threaded Web Server

  Threads can be divided into workers and perform all the tasks on one's own.

  1. Pros:

    a. Cheap context switching

    b. Shared memory space and states.

  2. Cons:

    a. Not simple implementation.

    b. Require synchronization

# Event-Driven Model

  In the model, it has only single address space, single process and single thread control.

  1. A global event dispatcher is responsible for reveiving events and dispatching events.

  2. Event types:

    a. Receipt of requests

    b. Completion of send

    c. Completion of disk read

  3. After the event is dispatched, the handler will be called. If there is a block operation in the handler, it will pass the control back to the dispatcher.

# Concurrency in Event-Driven Model

  Interleave multiple requests in a single execution context. The event dispatcher receives several events at the same time and send them to multiple handlers. Multiple handlers are handling different events at the same time.

# Why Does Event-Driven Model Work?

  The model processes requests until it is necessary to wait and immediately switch to another request.

# How Does Event-Driven Model Work?

  The model uses file descriptors to wrap sockets and files. Events are basically input on file descriptors.

  There are three ways to select which file descriptor to use.

  1. Among a range of file descriptors, it uses select() and return the one with the input.

  2. poll() scans a table of file descriptors and pick the one that fits.

  3. epoll() is a newer method supported by Linux and has better performance.

# Benefits Of Event-Driven Model

  1. Single address space, single flow of control

  2. Smaller memory requirement

  3. No context switching

  4. No synchronization.

# Problems With Event-Driven Model

  If there is a blocking I/O request, then the whole server will be blocked. To address this, asynchronous I/O operations can be used.

  
