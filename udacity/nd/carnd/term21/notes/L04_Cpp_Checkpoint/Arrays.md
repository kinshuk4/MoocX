## Introducing Variables

Variables are identifiers that you create to hold values or references to objects in your code. A variable is essentially a named memory location.

When you create a variable in C++, you must give it a data type. You can assign a value to the variable at the time you create it or later in your program code. This is known as initializing the variable. Even though you can initialize a variable separate from its creation, you must assign the data type when you define the variable. C++ will not allow you to use an uninitialized variable to help prevent unwanted data from being used in your application. The following code sample demonstrates declaring a variable and assigning a value to it.  C++ supports two methods of initializing a variable.

int myVar = 0;
int yourVar{1};

C++ has some restrictions around identifiers that you need to be aware of.

First off, identifiers are case-sensitive because C++ is a case-sensitive language. That means that identifiers such as myVar, _myVar, and myvar, are considered different identifiers.

Identifiers can only contain letters (in any case), digits, and the underscore character. You can only start an identifier with a letter or an underscore character. You cannot start the identifier with a digit. myVar and _myVar are legal but 2Vars is not.

C++ has a set of reserved keywords that the language uses. You cannot use these keywords as an identifier in your code. You may choose to take advantage of the case-sensitivity of C++ and use Long as an identifier to distinguish it from the reserved keyword long, but that is not a recommended approach.

To keep up to date on the reserved keywords for C++ you should always refer to the C++ standard but the current standard lists reserved keywords in the C++ Standard document found [here.](https://isocpp.org/files/papers/N3690.pdf)

## Introducing Constants

Similar to a variable, a constant expression is a named memory location for a value used in your application.  The difference is that, as you might expect, a constant expression cannot have its value change in the program during run time.  C++ uses the keyword const to indicate that an expression is a constant.

When you declare a constant in C++, you must assign a literal value to that constant at the same time.  You cannot assign it later in program nor can you change the value in code later.  Also, because the value cannot be changed, you cannot initialize a constant with a variable or any other value that will have its value modified during runtime.

## Type Conversions

Casting refers to converting one data type to another. Some data conversions are not possible while some are possible but result in data loss. C++ can perform many conversions automatically, what is known as implicit casting or implicit conversion. For example, attempting to convert a smaller data type to larger data type as shown here:

int myInt = 12; 
long myLong;
myLong = myInt;

In the first line, we declare an integer data type and assign it a value of 12. The next line declares a long data type and in the third line, we assign the integer data type value to the long data type. C++ automatically converts the data type for you. This is known as a widening conversion. Some programmers also call this an expanding assignment. We are expanding or widening the data type to a larger one. In this case, there is no loss in data. The following table highlights some potential data conversion problems.

| **Conversion**                           | **Potential Issues**                     |
| ---------------------------------------- | ---------------------------------------- |
| Large floating point type to small floating point type | Loss of precision and/or the starting value might be out of range for the new type |
| Floating point type to integer type      | Loss of the fractional component of the floating point type and/or out of range |
| Bigger integer type to smaller integer type | Starting value may be out of range for the new type |

This table only deals with numeric data type conversions. There are other conversion types such as from character to numeric or numeric to character, or among character types. C++ also uses the boolean type that represents true or false. If you assign a zero value to a bool variable, it will be converted to false. Any non-zero value is converted to true.

When you want to explicitly perform a conversion or cast, you can use the type cast features of C++. For example, the previous widening conversion in the int to long cast was implicit but you can also tell the compiler that you are know what you are doing by using the type cast statement as in:

long myLong = (long)myInt;

// or you can use this version as well

long myLong = long(myInt);

C++ also provides a cast operator that is more restrictive in its usage. This in the form static_cast (type)*. *This static* *cast operator can be used for converting numeric values or to numeric values. As an example:

```c++
char ch;
int i = 65;
float f = 2.5;
double dbl;
ch = static_cast<char>(i);   // int to char
dbl = static_cast<double>(f);   // float to double
```









