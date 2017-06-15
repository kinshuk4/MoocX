**if Statements**
In C++, if statements are concerned with Boolean logic. If the statement is **true**, the block of code associated with the if statement is executed.  If the statement is **false**, control either falls through to the line after the if statement, or after the closing curly brace of an if statement block.
The following code sample demonstrates an if statement to determine if a response contains a value of **y or Y**.
char response = 'y';
if (response == 'y' || response == 'Y')
{
​    cout << "Positive response received" << endl;
}
Note the use of curly braces in the code sample.  You can eliminate the curly braces if your statement to execute is a single line statement.  C++ understands that if no curly braces are used, the line immediately after the if(condition) will be executed if the condition is **true**.  Otherwise that line of code is not executed and the code resumes after that line.  If you need to have multiple statements execute if the condition is true, then you must use curly braces to surround the body of the if structure as in the code sample.  

*TIP: To avoid confusion as to which lines will execute for a true condition, a recommended practice is to always use curly braces for your if statement.*
In C++, if statements can also have associated else clauses. The else clause executes when the if statement is **false**.
The following code example shows how to use an if else statement to execute code when a condition is **false**.
*if else Statements*
string response;
if (response == "connection_failed")
{
​    // Block of code executes if the value of the response variable is "connection_failed".
}
else
{
​    // Block of code executes if the value of the response variable is not "connection_failed".
}
if statements can also have associated else if clauses. The clauses are tested in the order that they appear in the code after the if statement. If any of the clauses returns **true**, the block of code associated with that statement is executed and control leaves the block of code associated with the entire if construct.
The following code example shows how to use an if statement with an else if clause.
*else if Statements*
string response;
if (response == "connection_failed")
{
​    // Block of code executes if the value of the response variable is "connection_failed".
}
else if (response == "connection_error")
{
​    // Block of code executes if the value of the response variable is "connection_error".
}
else
{
​    // Block of code executes if the value of the response variable is neither above responses.
}

You can create as many else if blocks as necessary for your logic, or until you become completely lost from too many else if clauses.  If you require any more than five else if clauses, you might want to consider the switch statement, presented next.

## switch Statements

If there are too many else if statements, code can become messy and difficult to follow. In this scenario, a better solution is to use a switchstatement. The switch statement simply replaces multiple else if statements. The following sample shows how you can use a switch statement to replace a collection of else if clauses.

*switch Statement*

char response = 'y';
switch (response)
{
   case 'y':
​      // Block of code executes if the value of response is y.
​      break;
   case 'Y':
​      // Block of code executes if the value of response is Y.
​      break;
   case 'n':
​      // Block of code executes if the value of response is n.
​      break;
   default:
​      // Block executes if none of the above conditions are met.
​      break;
}
Notice that there is a block labeled default:. This block of code will execute when none of the other blocks match.  The default block is optional.
In each case statement, notice the break keyword. This causes control to jump to the end of the switch after processing the block of code. If you omit the break keyword, the application may not perform as you anticipate.  In other languages, such as C#, omitting the break; keyword will cause the code to no longer compile. 

Without the break statement, the code will "fall through" to the remaining cases until it encounters a break statement.   Be very careful in using fall through logic in your switch statements.  The most common use for a fall through scenario is when you want to handle multiple cases with a single statement or set of statements.

If you are coming from another programming language, such as C#, that also uses the switch statement, you might notice that in the C# language, you can use string values in your switch statements and don't have to use integers or enumerated types.  C++ switch statements support the following data types as expressions:

- - `intrinsic data types such as int or char`
  - `enumerations`

## The Conditional (Ternary) Operator

The C++ conditional operator is also known as a ternary operator because it takes three operands.  How this operator functions is somewhat similar to an if statement or a switch statement, but in a more compact form and for one single Boolean value with one of two possible outputs.   That is to say, the first operand is evaluated as a Boolean result.  If the result is true, then the second operand will be the one evaluated.  Otherwise, the third operand will be evaluated.   A sample helps amplify this.

\#include <iostream> 
using namespace std; 
int main() 
{ 
​     int i = 1, j = 2; 
​     cout << ( i > j ? i : j ) << " is greater." << endl; 
}

In this example, we have two integer variables, i and j which are initialized to 1 and 2 respectively.  The ternary operator is embedded inside the cout statement and essentially follows this pattern:

1. it checks whether i is greater than j
2. it outputs the proper numeric value along with is greater.

In the code example here, j is greater than i so the condition evaluates to false and the value for j (2), is output to the console along with the text is greater.  In other words, the output is "2 is greater."  If i was 5 and j was 2, the output would be, "5 is greater." 

i > j ? **\*i*** : j where i is greater than j then the bold value is selected

i > j ? i : **\*j*** where j is greater than i, then the bold value is selected

## for Loops

 Bookmark this page

The for loop executes a block of code repeatedly until the specified expression evaluates to false. You can define a for loop as follows.
for ([initializer(s)]; [condition]; [iterator]) 
{
   // code to repeat goes here
}
The [initializer(s)] portion is used to initialize a value, or values, as a counter for the loop. On each iteration, the loop checks that the value of the counter is within the range to execute the for loop, specified in the [condition] portion., and if so, execute the body of the loop.   At then end of each loop iteration, the [iterator] section is responsible for incrementing the loop counter.
The following code example shows how to use a for loop to execute a code block 10 times.
*for Loop*
for (int i = 0 ; i < 10; i++) 
{
​    // Code to execute.
}
In this example, i = 0; is the initializer, i < 10; is the condition, and i++ is the iterator. 



## while Loops

 Bookmark this page

A while loop enables you to execute a block of code while a given condition is **true**. For example, you can use a while loop to process user input until the user indicates that they have no more data to enter.  The loop can continue to prompt the user until they decide to end the interaction by entering a sentinel value.   The sentinel value is responsible for ending the loop.
The following code example shows how to use a while loop.
*while Loop*
string response;
cout << "Enter menu choice " << endl << "More" << endl << "Quit" << endl;
cin >> response;
​    while (response != "Quit")
​    {
​        // Code to execute if Quit is not entered

​        // Prompt user again with menu choices until Quit is entered
​        cout << "Enter menu choice " << endl << "More" << endl << "Quit" << endl;
​        cin >> response;
​    }
It's imperative to include the prompt again, inside the loop braces.  Failure to put this into the loop body will result in an infinite loop because the sentinel value can never be changed.

## do Loops

 Bookmark this page

A do loop, sometimes also referred to as a do...while loop, is very similar to a while loop, with the exception that a do loop will always execute the body of the loop at least once.  In a while loop, if the condition is false from the start, the body of the loop will never execute. 
You might want to use a do loop if you know that the code will only execute in response to a user prompt for data. In this scenario, you know that the application will need to process at least one piece of data, and can therefore use a do loop.
The following code example shows the use of a do loop.
*do Loop*
string response;
do
{        
​     cout << "Enter menu choice " << endl << "More" << endl << "Quit" << endl;
​     cin >> response;
​     // Process the data.
} while (response != "Quit");

A couple of aspects to note about this loop.  First of all, the  response variable is declared outside of the loop.  This is important due to scope resolution requirements.  If you declare the variable inside the loop, then the while(response != "Quit") portion will not "see" the response variable.

Second, note that in comparison with the while loop, the prompt only needs to be placed inside the loop body and is not required ahead of the loop.  This is possible because the do loop executes the contents of the loop at least once due to the condition check being at the end of the loop.

Third, notice the semicolon at the end of the loop.  This is required in the do loop and not in the while or for loop.



https://courses.edx.org/courses/course-v1:Microsoft+DEV210x+2T2017/courseware/bfc4cb492f3241a3891a98587ddfb849/d4c37651971a4651809b018ecd4f99b2/?child=first