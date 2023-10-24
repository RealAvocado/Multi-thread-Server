# Server-Client Number Operation Service
# 1. Set up
1.1 IDE version: Intellij IDEA 2021.2 (other proper types of IDE would also be OK)
1.2 SDK: JKD1.8.0_301 (or any other versions of JDK which can support this project)
# 2. How to run:
## 2.1 Server side:
For all the server programs, the port number argument should be passed to the program before running, otherwise the program will automatically select a port. 
In Intellij IDEA 2021.2, the port number can be passed through "Run/Debug Configurations" and there's a bar in it for users to enter program arguments. For other types of IDE, just find proper place to pass arguments.
The manager of the server can check the processing details in the console, which shows running details of threads. Each thread is intended to display the number list being processed by it. However, if more than 20 numbers is allocated to a thread, for concise display it will only show the size of number list. In the end execution time is displayed.
### 2.1.1 "Server" class 
This version of server uses k-1 threads for the first k-1 numbers and 1 thread for all the remaining. Users can run it to receive client connection. 
### 2.1.2 "Server_UniformDivision" class
This version of server would uniformly divide the number sequence across the threads. Users can also run it to receive client connection.
## 2.2 Client side:
For client programs, the host name and port number arguments should be passed to the program before running, otherwise the program would terminate. Normally the host name is "localhost" and the port number is the same as that of the server which you want to connect to.
In Intellij IDEA 2021.2, these two arguments can be passed through "Run/Debug Configurations" and there's a bar in it for users to enter program arguments. For other types of IDE, just find proper place to pass arguments.
### 2.2.1 "Client" class
This client program is for users to use. Just pass the arguments, run the program and follow the instruction in the interface. The user will be asked to enter the numbers one by one and then select an available operation provided by the server. Then the server will return the result back to the client along with execution time.
### 2.2.2 "Client_Test_Only" class
This client program is only for data testing purpose. It's not a real client interface model because in this case large amount of numbers can be generated automatically without user's input, and it's meaningless for a single client to do that operation. However, this client program is nice for exploring the server's performance. One thing should be paid attention is that if users send a huge amount of numbers to the server (number sequence size larger than 100,000, e.g.), it may take more than tens of seconds to display the results in the console and don't regard it as a bug.
*(Notice: Server program should run before the client program.)*
# 3. Content of the project
## 3.1 ServerPackage
Classes in this package are the two server classes mentioned in 2.1.
## 3.2 ClientPackage 
Classes in this package are the client classes mentioned in 2.2.
## 3.3 CommunicationPackage
There are two classes in this package. MessageSender class is used to generate serializable objects that can be transmitted between the server and the client. ServiceProtocol class focuses on processing data from the client and preparing the results.
