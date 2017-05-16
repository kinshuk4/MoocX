#include <stdio.h>
#include <stdbool.h> // for boolean variables
#include <string.h>
#include <ctype.h>

int main(){
	
	_Bool isANumber;

	int number;
	int sumOfNumbers = 0;

	printf("Enter a number:");

	isANumber = (scanf("%d", &number)==1);

	while(isANumber){
		sumOfNumbers += number;
		printf("Enter a number:");
		isANumber = (scanf("%d", &number)==1);
	}

	printf("The sum is %d\n", sumOfNumbers);
}