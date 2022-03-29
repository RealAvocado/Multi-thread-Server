#Server-Client Number Operation Service Model
#1. Set up
###1.1 IDE version: Intellij IDEA 2021.2 (other proper types of IDE would also be OK)
###1.2 SDK: JKD1.8.0_301 (or any other versions of JDK which can support this project)
#2. How to run:
##2.1 Server side:
### For all the server programs, the port number argument should be passed to the program before running, otherwise the program will automatically select a port. 
### In Intellij IDEA 2021.2, the port number can be passed through "Run/Debug Configurations" and there's a bar in it for users to enter program arguments. For other types of IDE, just find proper place to pass arguments.
###2.1.1 "Server" class 
###This version of server uses k-1 threads for the first k-1 numbers and 1 thread for all the remaining. Users can run it to receive client connection.
###2.1.2 "Server_UniformDivision" class
###This version of server would uniformly divide the number sequence across the threads. Users can also run it to receive client connection.
##2.2 Client side:
###For client programs, the host name and port number arguments should be passed to the program before running, otherwise the program would terminate.
### In Intellij IDEA 2021.2, these two arguments can be passed through "Run/Debug Configurations" and there's a bar in it for users to enter program arguments. For other types of IDE, just find proper place to pass arguments.
###2.2.1 "Client" class
###This client program is for users to use. Just pass the arguments, run the program and follow the instruction in the interface.
###2.2.2 "Client_Test_Only" class
###This client program is only for data testing purpose. It's not a real client interface model because in this case large amount of numbers can be generated automatically without user's input, and it's meaningless for a single client to do that operation. However, this client program is good at exploring the server's performance.