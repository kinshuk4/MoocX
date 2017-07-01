#include <stdio.h>
#include <stdlib.h>

char *convertBase(unsigned int numberToConvert, int base){ // function to convert from base 10 to base 2
	char buffer[33]; // work for at most 32 bit int
	char *pConvertedNumber;

	char allValues[] = "0123456789abcdef"; // all possible values in the resulting string array

	if(base<2 || base>16){
		printf("Enter a number between 2 and 16\n");
		exit(1);
	}

	pConvertedNumber = &buffer[sizeof(buffer)-1];
	*pConvertedNumber = '\0'; // the end of a string

	do{

		int value = numberToConvert % base;
		pConvertedNumber -= 1;
		*pConvertedNumber = allValues[value]; // '0'+value is to convert value to char
		numberToConvert /= base;

	}while(numberToConvert != 0);

	printf("%s", pConvertedNumber);

	return pConvertedNumber;
}

int main(){


	unsigned int numberOne = 60;

	printf("%d in base 2 = ", numberOne);
	convertBase(numberOne, 2);
	printf("\n");

	printf("%d in base 8 = ", numberOne);
	convertBase(numberOne, 8);
	printf("\n");

	printf("%d in base 16 = ", numberOne);
	convertBase(numberOne, 16);
	printf("\n");

	printf("\n");
	return 0;
}