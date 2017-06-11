## Numeric Data

C++ contains intrinsic data types to store numeric values in your application code. It's important to remember that these values are binary-based and not as flexible as their base 10 counterparts. For example, in mathematical terms of a base 10 integer, the definition is a value that is negative infinity to positive infinity whole numbers. Modern computers still cannot represent numbers these large. Take as an example the int type in the Numeric Data Types table. The range does not exceed 3 billion in either direction.

The byte representation given in that table will give you a hint as to how the values are stored in the memory and on disk.

*NOTE: The type names that start with a __ character are considered non-standard types.*

| Type Name          | Bytes | Alias                              | Range                                    |
| ------------------ | ----- | ---------------------------------- | ---------------------------------------- |
| int                | 4     | signed                             | –2,147,483,648 to 2,147,483,647          |
| unsigned int       | 4     | unsigned                           | 0 to 4,294,967,295                       |
| __int8             | 1     | char                               | -128 to 127                              |
| unsigned __int8    | 1     | unsigned char                      | 0 to 255                                 |
| __int16            | 2     | short, short int, signed short int | –32,768 to 32,767                        |
| unsigned __int16   | 2     | unsigned short, unsigned short int | 0 to 65,535                              |
| __int32            | 4     | signed, signed int, int            | –2,147,483,648 to 2,147,483,647          |
| unsigned __int32   | 4     | unsigned, unsigned int             | 0 to 4,294,967,295                       |
| __int64            | 8     | long long, signed long long        | –9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 |
| unsigned __int64   | 8     | unsigned long long                 | 0 to 18,446,744,073,709,551,615          |
| short              | 2     | short int, signed short int        | -32,768 to 32,767                        |
| unsigned short     | 2     | unsigned short int                 | 0 to 65,535                              |
| long               | 4     | long int, signed long int          | –2,147,483,648 to 2,147,483,647          |
| unsigned long      | 4     | unsigned long int                  | 0 to 4,294,967,295                       |
| long long          | 8     | none                               | –9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 |
| unsigned long long | 8     | none                               | 0 to 18,446,744,073,709,551,615          |
| float              | 4     | none                               | 3.4E +/- 38 (7 digits)                   |
| double             | 8     | none                               | 1.7E +/- 308 (15 digits)                 |
| long double        | 8     | none                               | 1.7E +/- 308 (15 digits)                 |



## Character Data

Character data is used to represent non-numeric data such as letters and symbols.  Character data is actually represented as numeric information under the covers.  The standard char type is used to represent the numeric values for character data as represented by the basic character set present on a particular computer.  This is determined by the locale settings.

For internationalization purposes, the wchar_t type is used which expands on the numeric values available to represent character sets from various languages found around the world. 

*NOTE: The type names that start with a __ character are considered non-standard types.*

| **Type Name**                   | **Bytes** | **Alias** | **Range**                                |
| ------------------------------- | --------- | --------- | ---------------------------------------- |
| char                            | 1         | none      | –128 to 127 by default 0 to 255 when compiled by using /J |
| signed char                     | 1         | none      | -128 to 127                              |
| unsigned char                   | 1         | none      | 0 to 255                                 |
| wchar_t, char16_t, and char32_t | 2 or 4    | __wchar_t | 0 to 65,535 (wchar_t & char16_t), 0 to 4,294,967,295 (char32_t) |

## Other Data Types

C++ supports other data types outside of the numeric or character data types.  The first one we see in the table below, is the Boolean data type called bool.  This is used to represent true or false values in an application.  In previous languages such as C, false was represented as a 0 value and true was represented as any non-zero value.

| **Type Name** | **Bytes** | **Alias** | **Range**                            |
| ------------- | --------- | --------- | ------------------------------------ |
| bool          | 1         | none      | true or false                        |
| enum          | varies    | none      | dependant on the enclosed data types |

C++ also has the concept of an enumeration, called an enum.  An enumeration is a set of constants stored as literal values.  They limit the choices for the type.  For example, when dealing with an int data type, you can assign any value to that data type that fits within the range of the integer type for that computer.  With an enum, you specify a limited set of literal constants that can be assigned to the type. 

Consider the need to use a data type to represent days of the week.  How do you store this information in a data type?  You could use an array, but what type of data would you use?  Perhaps a string data type.  But how do you prevent someone from adding a non-valid day of the week, like moncleday, to the array?  If you create an enumeration that stores only valid values for Sunday through Saturday, you constrain the data type to those literal constants only.

Enumerations are covered later in this module under the lesson on Complex Data Types.



## Choosing Data Types

Choosing the correct data type is important in your applications to ensure that you can represent your data efficiently and correctly. Some examples of this would be;

- making use of short rather than int if your data range permits
- using a double rather than a float to get greater a accuracy for values representing money
- using a wchar_t for character data that doesn't fit in the standard ASCII character set, such as Japanese kanji



https://courses.edx.org/courses/course-v1:Microsoft+DEV210x+2T2017/courseware/ec46a4a428a944a4b8cbe9010af4af97/7a6776e7c1d242228fd3619792f8c49a/?child=first