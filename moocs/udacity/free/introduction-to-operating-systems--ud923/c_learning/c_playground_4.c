#include <stdio.h>
#include <stdlib.h>

void generateTwoRandomNumbs(int rand1, int rand2){
	rand1 = rand() % 50 + 1;
	rand2 = rand() % 50 + 1;

	printf("New rand1 in function = %d\n", rand1);


}

void pointerRandomNumbers(int* rand1, int* rand2){
	*rand1 = rand() % 50 + 1;
	*rand2 = rand() % 50 + 1;
	printf("New rand1 in function = %d\n", *rand1);

	printf("New rand2 in function = %d\n", *rand2);

}


int main(){
	int rand1 = 12;
	int rand2 = 15;
	printf("rand1 = %p, rand2 = %p \n\n", &rand1, &rand2);
	printf("Size of int %lu\n\n", sizeof(rand1));

	int *pRand1 = &rand1; // it is custom to start the pointer variable name with a p
	// here pRand1 is a pointer, with initial value designated as the address of rand1
	// the * here does not mean we are giving value to the pointed address of pRand1, just mean it is a pointer
	printf("Pointer %p\n", pRand1);
	printf("Value of pointer %d\n", *pRand1);

	int primeNumbers[] = {2,3,5,7};
	printf("First index: %d\n", *primeNumbers);
	printf("Second index: %d\n", *(primeNumbers+1));

	char *students[4] = {"Sally", "Mark", "Paul", "Sue"}; 
	// this is an array of pointers, each pointer point to a char
	// on the other hand, each pointer to a char is equivalent to a string

	for(int i=0; i<4; i++){
		printf("%s : %p\n\n", students[i], &students[i]);
	}
	rand1 = 0;
	rand2 = 0;
	printf("Main before function call\n");
	printf("rand1 = %d\n", rand1);
	printf("rand2 = %d\n", rand2);
	//generateTwoRandomNumbs(rand1, rand2);
	pointerRandomNumbers(&rand1, &rand2);
	printf("Main after function call\n");
	printf("rand1 = %d\n", rand1);
	printf("rand2 = %d\n", rand2);

}
