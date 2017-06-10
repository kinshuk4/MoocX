## Arrays

 Bookmark this page

**Complex Data Types (Arrays)**

So far you have been introduced to the intrinsic data types that C++ supports. These are types that contain the data literals directly. The int type directly stores the literal integer value, for example.

C++ also provides support for complex data types. These are also referred to as compound data types. Mostly because they store more than on piece of data or potentially more than one data type.

An array is a set of objects that are grouped together and managed as a unit. You can think of an array as a sequence of elements, all of which are the same type. You can build simple arrays that have one dimension (a list), two dimensions (a table), three dimensions (a cube), and so on. Arrays in C++ have the following features:

- Every element in the array contains a value.
- Arrays are zero-indexed, that is, the first item in the array is element 0.
- The size of an array is the total number of elements that it can contain.
- Arrays can be single-dimensional, multidimensional, or jagged.
- The rank of an array is the number of dimensions in the array.

Arrays of a particular type can only hold elements of that type. This means that you cannot store integers, longs, and character data types in the same array.

**Creating and Using Arrays**

When you declare an array, you specify the type of data that it contains and a name for the array. To declare a single-dimensional array, you specify the type of elements in the array and use brackets, [] to indicate that a variable is an array. The following code example shows how to create a single-dimensional array of integers with elements zero through nine.

int arrayName[10];

You can also choose to create an array and initialize it with values at the same time as in the following example that declares and integer array and assigns values to it. The compiler knows how large to make the array by the number of values in the curly braces:

int arrayName[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

You can also declare an array and only initialize some of the elements:

int arrayName[10] = {1, 2, 3};

In this case, we have declared an array of size 10 but have only assigned values to the first three elements. The compiler will initialize the remaining elements to the default value for the data type the array holds. In this case, int data type, the remaining values are initialized to 0.

**Accessing Data in an Array**

You can access data in an array in several ways, such as by specifying the index of a specific element that you require or by iterating through the entire array and returning each element in sequence.

The following code example uses an index to access the element at index two.

//Accessing Data by Index 
int oldNumbers[] = { 1, 2, 3, 4, 5 }; 
//number will contain the value 3 
int number = oldNumbers[2];

Note: Arrays are zero-indexed, so the first element in any dimension in an array is at index zero. The last element in a dimension is at index N-1, where N is the size of the dimension. If you are using some other languages, such as C#, and you attempt to access an element outside this range, the C# runtime will throw an exception (error). C++ doesn't offer such protection. If you attempt to access an element that is outside the bounds of your array, you will still return data, but you have no idea what that data is.

The reason for this is because an array is simply a pointer to a memory location. The first element of the array is the starting memory address for the entire array. If you have an array of integer data types, then the number of the elements multiplied by the size of the int data type on your system, determines how much memory is used by the array, and at the same time, permits the access of the elements in the array by performing math on the memory address to get at the required element. If you attempt to access oldNumbers[5], the program will simply return the data found at the memory address that is the next memory address location beyond the last array element. This can be a dangerous situation and is in fact, the result of some security issues found in software.

You can also iterate through an array by using a for loop. You will cover loops in module 3 so don't worry if you don't completely understand this example at this time. Essentially, the for loop starts at 0 and repeats the portion in the curly braces {} for each of the five steps in the loop.

The following code example shows how to use a for loop to iterate through an array.

//Iterating Over an Array 
int oldNumbers[] = { 1, 2, 3, 4, 5 };
for (int i = 0; i < 5; i++) 
{ 
​     int number = oldNumbers[i]; 
​     ... 
}



## Strings

 Bookmark this page

Strings are a series of characters. C++ represents strings in one of two ways. The first maintains backward compatibility with the C language and represents the string as a character array. There is one aspect to a C-style string that is important to note. The last character of every string you store is the null character string, typically represented by the ASCII code for 0 which is \0. This is necessary so that the compiler knows when the string ends. An example demonstrates a C-style string stored in a character array:

char isAString[6] = { 'H', 'e', 'l', 'l', 'o', '\0'}; 
char isNotAString[5] = { 'H', 'e', 'l', 'l', 'o'}; 
cout << isAString << endl; 
cout << isNotAString << endl;

The most common mistake made by users of the C-style string is to forget to make the char array large enough to accommodate the \0 character, but also forgetting include the \0. In the previous example, a programmer might think that an array of size 5 would be large enough to contain Hello because that's how many characters are in the word. However, the null character would not be included in the second array, which could result in errors in code that uses this array. The reason is that C++ does not consider the isNotAString array to be a string.

Consider the output displayed in Figure 2.1. Note that the first output correctly terminates because C++ encountered the null (\0) character. The second did not terminate and output the contents of adjacent memory.

An alternative method of declaring a character array for use as a string is to simply initialize it with a string literal. A string literal is a sequence of characters enclosed in the double quotes ("). For example:

char isAString[6] = "Hello"; 
char isAnotherString[] = "Array size is inferred";

In the previous example, the first line creates an array of size 6 and assigns the string literal Hello to the array. The second example lets the compiler infer the size from the string literal itself. Note that neither of these two string literals specifies a \0 character. The compiler will implicitly add that for you. However, caution is advised in the first line to ensure that you allow enough room in the array size specified for the null character. If you create an array that is larger than required for the string literal along with the null character, then C++ simply fills the remaining elements of the array with null characters.

**The string Class** If the use of character arrays, single quoted characters, and null termination characters are making you think that strings aren't worth the hassle, consider the string class instead. The ISO/ANSI standard helped to expand the string handling capabilities of C++ by adding the string class.

In order to use the string class, you have to include the string header file. We have not covered namespaces yet but to make typing much easier, you would add a using statement as in the following example.

using namespace std; 
string myString = "Hello!"; 
std::string myNewString = "Less typing";

Without the using directive, you would have to type std::string every time you wanted to use the string class in your code, as in the second line above.

As you can see from the code example, you use string in the same manner in which you would use any other data type in C++. You also do not need to add a null character to terminate your string.



## Structures

Arrays can store multiple pieces of data in one compound data type but recall, the data types must all be of the same type. If that is the case, how might you store multiple pieces of data in one type, where the individual pieces are of different data types? For example, let's say that we want to store information about a coffee bean. We might want to store information about the bean type, its strength, and perhaps which country it originated from. In this case, we could use all strings to store that information but what if the strength was intended to be a number from 1 to 10. In this case, we would want to store two strings and one integer in our coffee bean data type.

We haven't covered classes yet, which is another data type we could use, but instead, we will use a structure (struct) to store this information. Structures are known as user-defined types. You define the struct by giving it a name and then adding the member data types as in the following example:

struct coffeeBean 
{ 
​     string name; 
​     string country; 
​     int strength; 
};

Recall that in order to use the string data type in our struct, the C++ file that contains the struct must include the string header file. This code snippet also assumes that using namespace std; has also been included.

Once we have defined the structure, we can then use it in our code the same as any other data type. To use the coffeeBean struct in your code, you simply declare a new variable of that type as shown in this example.

struct coffeeBean 
{ 
​     string name; 
​     string country; 
​     int strength; 
}; 
coffeeBean myBean = { "Strata", "Columbia", 10 }; 
coffeeBean newBean; 
newBean.name = "Flora"; 
newBean.country = "Mexico"; 
newBean.strength = 9; 
cout << "Coffee bean " + newBean.name + " is from " + newBean.country << endl;

You can assign values to a struct using one of the methods seen here. For myBean, we assign values in the curly braces while for newBean, we use the dot notation. You can also access the values of the the struct members using the dot notation as well, shown in the cout statement at the end.

## Unions

A union, in C++, is similar to a structure in that it can store multiple, disparate data types. The differentiation is that a union can only store one piece of data at a time. What does that signify? It's best represented using an example.

```
union numericUnion 
{ 
     int intValue; 
     long longValue; 
     double doubleValue; 
};
numericUnion myUnion; 
myUnion.intValue = 3; 
cout << myUnion.intValue << endl; 
myUnion.doubleValue = 4.5; 
cout << myUnion.doubleValue << endl; 
cout << myUnion.intValue; cout << endl;
```

In this example, we define a union called numericUnion and then create a variable of that type, called myUnion. We first assign the value 3 to the intValue field and then output it. Next we assign the value 4.5 to the doubleValue field and output that. The example shows how the union works when on the second to last line, we try to output the value for intValue again. In the output, it results in 0 rather than 3. The reason is that once we assign a value to doubleValue, what was contained in intValue is lost. The union can only store a value in one of its fields at a time.

Why use a union over a struct if it can only hold one piece of data at a time? Consider a situation where you are programming an application that will run on a device with limited memory. You would like to use a data type that can support multiple types internally like a struct, but not necessarily all at the same time. For example, part numbers for components in manufacturing where the part number may be a number or perhaps a string, depending on the manufacturer of the part. In this case, you could use the union to represent a numeric and a string data type internally but only assign the proper data type based on the part number.



## Enumerations

In the topics on variables and constants, it was noted that anytime you want to create a value for use in a program, where the value should never change, you used a constant. An enumeration can be considered a way to create what are known as symbolic constants. The most common example is to use an enum to define the day of the week. There are only seven possible values for days of the week, and you can be reasonably certain that these values will never change.

To create an enum, you declare it in your code file with the following syntax, which demonstrates creating an enum called Day, that contains the days of the week:

enum Day { Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday };

By default enum values start at 0 and each successive member is increased by a value of 1. As a result, the previous enum 'Day' would contain the values:

- Sunday = 0
- Monday = 1
- Tuesday = 2
- etc.

You can change the default by specifying a starting value for your enum as in the following example.

enum Day { Sunday = 1, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday };

In this example, Sunday is given the value 1 instead of the default 0. Now Monday is 2, Tuesday is 3, etc.

The keyword enum is used to specify the "type" that the variable Day will be. In this case, an enumeration type. Consider the following code sample:

enum Day { Sunday = 1, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday }; 
Day payDay; 
payDay = Thursday; 
cout << payDay << endl;

The first line defines the enumeration Day and assigns seven values to the enum. Sunday is listed as the first day of the week and is initialized with the value one.

The second line declares a new variable called payDay which is of the Day enum type. In the third line, payDay is assigned a value from the list of values, in this case Thursday. Finally, the last line outputs the value of payDay to the console window. If you run this code, you will notice that the last line outputs 5 and not Thursday. Internally, the constants in the enum are used as numbers and not as the textual representation you assign to them.

[Naming enums - plurals or singular](https://stackoverflow.com/questions/1405851/enum-naming-convention-plural)



