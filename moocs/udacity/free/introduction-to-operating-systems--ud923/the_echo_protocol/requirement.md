### The Echo Protocol
In C, write a server and client that implement the fictitious "echo protocol". To implement the protocol, the server should accept any string from the client, and then return that string with all letters capitalized (if letters exist).

Echo Protocol Example
- Client sends "Hello, wOrlD"
- Server echoes "HELLO, WORLD"

As soon as the server responds to a client, it may close. And, as soon as the clients receives a response, it may close. Feel free to reuse portions of the **Simple Socket: Client** and **Simple Socket: Server** programs.

To assist with implementation, we provided two example echo clients and echo servers:

- [example echo client and server](https://s3.amazonaws.com/content.udacity-data.com/courses/ud923/resources/ud923-ps1-the-echo-protocol-exe-linux.zip)(executables, should work on VM)
- [example echo client and server](https://s3.amazonaws.com/content.udacity-data.com/courses/ud923/resources/ud923-ps1-the-echo-protocol-exe.zip)(executables, compiled with gcc on Mac)