## Introduction to Functions

 Bookmark this page

Functions are a component of a programming language that permit you to break down the behavior of an application into discreet pieces of functionality, hence the name function.  A function is essentially a block of C++ code that you give a name and then call from other locations in your application, when you want the computer to perform the instructions contained in that function.

You create a function by defining it.  The function definition may contain a return type, a function name, parameters to accept incoming arguments, and finally a body which contains the code that will execute as part of that function.  Functions may or may not return a value back to the caller and they may or may not accept arguments passed in to the function.

When you move into more advanced C++ programming, you can also overload functions.  This refers to the practice of using the same function name to refer to multiple functions that perform different actions.  Why would you do this?  Consider a simple scenario where you want to perform addition on some values so you create a function called Sum().  You could overload Sum() by creating variants such as:

int Sum(int x, int y)
{
​     return x + y;
}
int Sum(int x, int y, int z)
{
​     return x + y + z;
}

The compiler will know which function to call, based on the number of arguments passed in.



## Function Prototypes

 Bookmark this page

When declaring a function, you specify its storage class, return type, name, and parameters.  Some refer to this as the function signature.  In C++ it is also called a function prototype. 

In C++, function prototypes belong in header files.  Recall that the header file is what gets imported into other source code files so that the compiler can track proper use of functions and other aspects of the included files.  The function prototype only contains the function's signature with no implementation details.  The implementation details along with the function signature, define the function.  The function definition exists in the source code file (.cpp).

## Function Parameters

 Bookmark this page

A function can accept values that will be used in the statements in the function body.  These values are known as arguments when passed to a function.  The arguments are passed into parameters.  Parameters are the placeholders that are found inside the parentheses of a function declaration, as shown in the following example where a and b are the parameters.  Note that the data types for these are specified and that the parameters are separated by a comma.

int Sum(int a, int b)
{
​     return a + b;
}

Your function can specify as many parameters as necessary for the function to complete its work but for obvious reasons, you don't want to create a function signature that is so long, your code line wraps.  If your function takes that many parameters, it's worth refactoring it to reduce the number of parameters.

When calling the function, you use the function name, followed by an open parenthesis, the arguments that will be passed into the parameters, and then a closing parenthesis, as shown here:

int result = Sum(2, 3);

Note that you don't have to include the data type specifiers for the arguments being passed in but you must know the data type the function expects.  This is one of the reasons for declaring a function prototype prior to using it in code.



## Inline Functions

 Bookmark this page

One of the goals for using functions in your code is to create discrete pieces of functionality in your code that is easier to test and maintain.  However, functions also have an impact on application performance.  The reason for this impact results from the operations that must be performed when a function is called.  For example, registers in the CPU need to be reset, stack pointers are created, memory is consumed for these pointers and the values that are passed into and out of the function.    For simple functions that may perform only a single operation or have only a single, simple statement, you might wonder why creating a function for it is worth the effort.

You can still make use of a function to perform the necessary computation but it makes more sense to create the function as an inline function.  Inline functions avoid the overhead associated with traditional function calls.  You create an inline function by prefixing it with the inline keyword.  A common function found in applications for a sorting algorithm such as bubble sort, is a swap function.  Swap takes two variables and swaps their values as shown here:

inline void swap(int & a, int & b)
{
​     int temp = a;
​     a = b;
​     b = temp;
}

Using this mechanism, each time that a call to the swap() method is encountered in your code, the compiler will insert the body of the function in that location as opposed to making a function call.  This example demonstrates what that would look like.

// Traditional method that results in a function call
swap(5, 6);
// Using an inline function call, the compiler converts the previous line to this
int temp = a;
a = b;
b = temp;

This avoids the overhead of making a function call because the contents of the function body are now located at the point where the functionality is required.   Note a couple of points about inline functions:

- the inline keyword is a compiler directive that is a recommendation only.  The compiler may ignore your request and compile the function normally resulting in function calls anyway.
- if you are using inline functions and change the function in anyway, the code needs to be recompiled because the code for that function will need to be updated in each location it was used.
- use inline functions only for small functions that are used frequently, not for large functions.



## Storage Classes and Scope

 Bookmark this page

MSDN defines storage class as, "A storage class in the context of C++ variable declarations is a type specifier that governs the lifetime, linkage, and memory location of objects."

Lifetime refers to how long the variable "hangs around" in memory from the point at which it is declared and the point at which it is destroyed (the memory it used is released).  For the most part, once a variable goes out of scope, its memory will be released back to the operating system for reuse.

Linkage refers to the visibility of a variable outside of the file that contains it. 

Memory location refers to the place in which the variable is found in memory.  This doesn't refer to the physical memory address as you might expect but more to the logical division of memory that applies to a running application.  There are two logical memory areas known as the stack and the heap.  The stack is a location in memory where intrinsic data is stored as well as memory addresses (pointers).  It operates in the form of data structure known as a stack.  Like a cafeteria stack of plates, items are pushed on top of the stack and other items are pushed further down.  To remove an item from the stack, it is popped off, used, and discarded.

The heap, or free store, is a pool of memory that is used to store objects that dynamically allocated at run time by your application.  An object is what you will learn about in the next topic on object-oriented programming.  You create and destroy objects on the heap by using specific instructions in your program code.

Scope is the term used describe where an identifier is visible in a program.  An identifier is a variable, constant, class, etc.  Your identifier is visible from the point in which you have declared it until the end of its scope.  The following code sample displays different scope for the identifiers used.

\1. #include <iostream>
\2. int main()
\3. {
\4.     int total = 0;
\5.     for(int i = 1; i <= 10; i++)
\6.     {
\7.          total += i;
\8.     }
\9.     std::cout << "The sum of the numbers 1 to 10 is " << total << std::endl;
\10.    std::cout << "Current value of i is " << i << std::cout;
\11. return 0;
\12. }

In the previous code, the variable total is declared inside main() but outside of the for loop.  This means that total is visible (in scope) for the entiremain() method, which also includes inside the for loop.  However, the variable i is declared inside the for loop's initialization section and is therefore constrained to the scope of the for loop.   The code at line 10 will result in an error in C++ that indicates the variable is undefined.    Anyplace other than inside the for loop is out of scope for the variable i.

C++ makes use of the following keywords that apply to storage classes:

- static - identifiers declared with static are allocated when the program starts and deallocated when the program execution ends.  Declaring a variable as static in a function means that the variable will retain its value between calls to the function.
- extern - used to declare an object that is defined in another translation unit of within the enclosing scope but has an external linkage.
- thread_local - declares that the identifier is only accessible on the thread in which it is created.  This prevents sharing of the identifier across multiple threads in the same application.   This is part of the C++11 standard.



https://courses.edx.org/courses/course-v1:Microsoft+DEV210x+2T2017/courseware/4b85ea07fc364189a59bd7e6e79b7921/51f3c77abf7447898b723fd7e7d40a38/?child=first