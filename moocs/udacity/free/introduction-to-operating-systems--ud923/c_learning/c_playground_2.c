#include <stdio.h>
int main(){
	int num1 = 1;
	int num2 = 2;
	printf("is 1 > 2: %d\n\n", num1>num2);

	if(num1>num2){
		printf("%d is greater then %d\n\n", num1, num2);
	}else if(num1<num2){
		printf("%d is smaller than %d\n\n", num1, num2);
	}else{
		printf("%d is equal to %d\n\n", num1, num2);
	}

	printf("A char takes up %lu bytes\n\n", sizeof(char));
	printf("A int takes up %lu bytes\n\n", sizeof(int));
	printf("A long takes up %lu bytes\n\n", sizeof(long));
	printf("A float takes up %lu bytes\n\n", sizeof(float));
	printf("A double takes up %lu bytes\n\n", sizeof(double));

	// the overflow of numbers
	int bigInt = 2147483648;
	printf("I am bigger than allowed %d\n\n", bigInt); // the output is negative due to overflow of int

	for(int i=0; i<20; i++){
		printf("%d ", i);
	}

	return 0
}