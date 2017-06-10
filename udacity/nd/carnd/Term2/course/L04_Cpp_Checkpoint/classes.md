Classes enable you to create your own custom, self-contained, and reusable types. A class file is often considered a blueprint for objects that you use in your code.  Typically you will create class files to help model real-world objects in your code. 

An example of this might be the software that manages a bank ATM.  The software would need to understand the concept of customers, accounts, transactions, etc.   It's far easier to implement the software application by modelling these real world objects as software objects (classes) in your code.

In this module, you will gain an introduction to classes in C++ that will cover the basics only.  The second installment of the C++ course will delve deeper into object oriented concepts in C++. 



## Creating Classes

 Bookmark this page

Creating Classes and Members
In C++, a class is a programming construct that you can use to define your own custom types. When you create a class, you are effectively creating a blueprint for the type. The class defines the behaviors and characteristics, or class members, which are shared by all instances of the class. You represent these behaviors and characteristics by defining methods and fields within your class.
Suppose you create a class to represent a rectangle shape in your program.  You use the class keyword to declare a class, as shown in the following example:
//Declaring a Class
class Rectangle
{
public:
​    int _width;
​    int _height;
};
Here we have declared a class called Rectangle and given it two public member variables called _width and _height, that will be used to represent the width and height of our rectangle.   Note that they are accessible directly because they are public, as a result of the public: modifier.

**Using a Class**

Now that we have a class created to represent a rectangle, we can use that in our code to create instances of a rectangle in our program.  When we create a new rectangle from this class, it is known as a rectangle object and will be given a unique name.  That way ,we can refer to it in our program directly and distinguish it from other rectangle instances that we might create, should our program require more than one.

void main()
{
​     Rectangle outer;
​     Rectangle inner;     
​     outer._width = 10;
​     outer._height = 10;
​     inner._width = 5;
​     inner._height = 5;
}

In this sample code, we have created two rectangle objects called outer and inner.   Then, using what is known as "dot notation" or the dot operator, we provide values for the width and height of each rectangle.  The outer rectangle is 10 x 10 and the inner rectangle is 5x5.



## Class Initialization

 Bookmark this page

Initialization is an important part of working with your classes in C++.  Even when using intrinsic data types, if you do not initialize the variable for that type and you access it in your code, you will end up with whatever values are stored in the memory location that the variable refers to.  This is something you want to avoid doing.   You should always initialize your types in C++;

C++ offers a couple of options for initializing your classes.  You can initialize the member variables by using the dot operator and setting the values explicitly or you can include a constructor in your class that is responsible for initialization the member variables.   You'll see this in the demo video next as well as more information in the topic on encapsulation.



## Introducing Encapsulation

 Bookmark this page

Often considered the first pillar of object-oriented programming, encapsulation can be used to describe the accessibility of the members belonging to a class.  C++ provides access modifiers and properties to help implement encapsulation in your classes.  While some consider this accessibility configuration to be the only aspect of encapsulation, others also define encapsulation as the act of including all data and behavior required of the class, within the class definition.

We use encapsulation to prevent changing the member variables directly.  This is considered a poor practice because it presents the opportunity for potentially incorrect values to be assigned to these member variables.  This can result in unexpected behavior or more serious problems with your executing code.  It also helps with debugging of your code.



## Introducing Const Objects

 Bookmark this page

Recall that the keyword const was used to indicate that a data type you use in your code is a constant and cannot have its value changed during application runtime.  Objects in your code can also make use of the const keyword to indicate that the objects are immutable.  Immutable simply means that they cannot change.

In the next couple of video segments, you will be introduced to const objects and see a demo of how to use them in code.









https://courses.edx.org/courses/course-v1:Microsoft+DEV210x+2T2017/courseware/4b85ea07fc364189a59bd7e6e79b7921/113226151eda426dbc50fa5edf24338b/?child=first