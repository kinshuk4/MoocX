/* 
This is a multiline comment
*/
// This is a single line comment

#include <stdio.h>  // deal with input/output
#include <string.h> // deal with string

#define MYNAME "Jiaxing Liu" // use uppercase letters for define

int globalVar = 100; // global because declared outside main

int main(){

	char firstLetter = 'D'; // use single quote to create char
	int age = 38;
	long int superBigNum = -327670000; // when the integer is outside the range of int
	float piValue = 3.14; // can have 28 decimals
	double reallyBigPi = 3.1415916273819428374; // can have more decimals

	printf("\n");
	printf("This will print to screen\n\n");
	printf("I am %d years old\n\n", age);
	printf("Big Number %ld\n\n", superBigNum);
	printf("Pi = %.5f\n\n", piValue);
	printf("Big Pi = %.15f\n\n", reallyBigPi); // The double and float are the same in printf
	printf("The first letter in my name is %c\n\n", firstLetter);
	printf("My Name is %s\n\n", "Jiaxing Liu");
    char myName[] = "Jiaxing Liu"; //string is created as list of char
	char myName2[12] = "Jiaxing Liu"; // works the same as above, the length is 12 because there is one additional string terminator \0 at the end implicitly
	strcpy(myName, "new name");
	printf("My name is %s\n\n", myName);

	char middleInitial;
	printf("What is your middle initial?");
	scanf(" %c", &middleInitial);
	printf("Middle initial: %c\n", middleInitial);

	char firstName[30], lastName[30]; // The initial size has to be given if the initial values are note given
	printf("What is your name?");
	scanf(" %s %s", firstName, lastName);
	printf("Your name is %s %c %s\n", firstName, middleInitial, lastName);

	int month, day, year;
	printf("What is your birth date?");
	scanf("%d/%d/%d", &month, &day, &year);
	printf("\nBirth Date %d/%d/%d", month, day, year);

}