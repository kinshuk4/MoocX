### File IO

As with other programming languages, we can read and write files.

File IO Steps:

```
 - Include the <fstream> library 
 - Create a stream (input, output, both)
      - ofstream myfile; (for writing to a file)
      - ifstream myfile; (for reading a file)
      - fstream myfile; (for reading and writing a file)
 - Open the file  myfile.open(“filename”);
 - Write or read the file
 - Close the file myfile.close();
```



Here is the Code:

```c++
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main () {
    string line;
    //create an output stream to write to the file
    //append the new lines to the end of the file
    ofstream myfileI ("input.txt", ios::app);
    if (myfileI.is_open())
    {
        myfileI << "\nI am adding a line.\n";
        myfileI << "I am adding another line.\n";
        myfileI.close();
    }
    else cout << "Unable to open file for writing";

    //create an input stream to read the file
    ifstream myfileO ("input.txt");
    //During the creation of ifstream, the file is opened. 
    //So we do not have explicitly open the file. 
    if (myfileO.is_open())
    {
        while ( getline (myfileO,line) )
        {
            cout << line << '\n';
        }
        myfileO.close();
    }

    else cout << "Unable to open file for reading";

    return 0;
}
```





### Header files

As we have seen we can include additional libraries in C++, we can also include our own libraries.

Traditionally, these files are called header files and they have an .hpp extension. Although any extension will work.

- Header files contain information about how to do a task.
- The main program contains information about what to do.

Let’s see how a header file works with a simple hello world program.

We have a simple hello world program. We can test this, and the program runs.

Below I have included a screenshot of the programming quiz interface. Note that there is a main.cpp and a main.hpp.

- main.cpp: all the code on **what** the program does goes in this file.
- main.hpp: all the code on **how** the program does a task goes in this file.

Eg. main.hpp

```c++
#include <iostream>

 using namespace std;
```

main.cpp - We don't have to include iostream, it automatically gets from main.hpp. Also, we are using double quotes to get main.hpp rather than angular brackets.

    #include "main.hpp"
    int main(){
    
    cout<<"Hello, I use header files!";
    return 0;
    }


### User Input

In C++ we use std::cout for writing to the console.

And we have std::cin for reading from the console.

Since we have talked about cout earlier in the lesson, and we have used it in many of our programs, we will concentrate on cin. Let’s look at an example of reading user input from the console.



In this program you will see how to use std::cin. Because we are in the Udacity classroom, we need to use the input.txt file to enter user inputs.

This works because when you click on the 'test run' menu item, you are actually executing the following statements:

```
"g++", "-o main.out", "main.cpp"
"./main.out", stdin=open("input.txt", "r")

```

The first statement compiles the code and names the executable file main.out. Then main.out is executed using an input file called "input.txt".

These are the commands you would run if you were compiling and executing the program in a terminal. In the Udacity classroom setting clicking the "Test Run" button executes these commands for you.

We need to do this because the Udacity platform does not have a mechanism to accept user input in programming quizzes.





```c++
/This program accepts inputs from the input.txt file/ 

include <iostream>

include <string>

int main()

{

int year = 0;
int age = 0;
std::string name = " ";
//print a message to the user
std::cout<<"What year is your favorite? ";

//get the user response and assign it to the variable year
std::cin >> year;

//output response to user
std::cout<<"How interesting, your favorite year is "<<year<<"!\n";

//print a message to the user
std::cout<<"At what age did you learn to ride a bike? ";

//get the user response and assign it to the variable age
std::cin >> age;

//output response to user
std::cout<<"How interesting you learned to ride at "<<age<<"!\n";

std::cout<<"What is your name? ";
std::cin>>name;
std::cout<<"Hello "<<name<<" !\n";
return 0;
}
```
#### String Input

So, we now know that std::cin will not retrieve strings that have a space in them. It will see the space as the end of the input. We will obviously need a method to enter strings.

C++ has a function called getline. You can find detailed information at the link posted with this video.

The basic form of getline is:

getline: it will retrieve characters from the std::cin source and stores them in the variable called variableName. It will retrieve all characters until the newline or “\n” is detected.

The programmer can also specify a different delimiter if the newline character is not desired. The details are in the provided link.




    include<iostream>
    
    include<string>
    
    int main(){
    std::string userName; 
    std::cout<<"Tell me your nickname?: ";
    std::getline(std::cin, userName);
    std::cout<<"Hello "<<userName<<"\n";
    return 0;
    }


#### Using StringStream

There is one further aspect of string inputs that you might find handy.

First we will need to include the Stringstream library. Then use getline to get the string from the user Then we will convert the string variable to a numeric variable.

Steps for using Stringstream:

1. Include the Stringstream library.

   ```
       #include<sstream>

   ```

2. Use getline to get the string from the user

   ```
       std::getline(std::cin, stringVariable);

   ```

3. Convert the string variable to a float variable.

   ```
       std::stringstream(stringVariable) >> numericVariable;

   ```

We can see the steps applied in this example:

```c++
 #include <iostream>
 #include <string>
 #include <sstream>

 int main ()
 {
   std::string stringLength;
   float inches = 0;
   float yardage = 0;

   std::cout << "Enter number of inches: ";
   std::getline (std::cin,stringLength);
   std::stringstream(stringLength) >> inches;
   std::cout<<"You entered "<<inches<<"\n";
   yardage = inches/36;
   std::cout << "Yardage is " << yardage;
   return 0;
 }
```