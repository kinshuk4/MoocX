#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int globalVar = 0; // this is a global variable

int func


int main(){
	int whatToDo = 0;

	switch(whatToDo){

		case(0): 
			printf("case 0\n");
			break;
		case(1): 
			printf("case 1\n");
			break;
		default: 
			printf("case default\n");
			exit(0);
			break;
	}
	printf("I am continuing!\n");

	int primeNumbers[3] = {2,3,5};
	int morePrimes[] = {13,17, 19};
	printf("The first prime is %d\n", primeNumbers[0]);

	char yourCity[30];
	printf("What city do you live in?");
	fgets(yourCity, 30, stdin); // the fgets will only keep the letters within the size limit
	printf("Hello %s\n\n", yourCity);


	for(int i=0; i<30; i++){
		if(yourCity[i]=='\n'){ // the fgets automically adds \n at the end
			yourCity[i] = '\0';
			break;
		}
	}
	printf("Hello %s\n\n", yourCity);

	char thirdCity[] = "Paris";
	printf("Is your city paris? %d\n\n", strcmp(yourCity, thirdCity));

	char yourState[] = ", Pennsylvania";
	strcat(yourCity, yourState); // the strcat modifies the variable inplace.
	printf("You live in %s\n\n", yourCity);

	printf("Letters in Paris: %lu\n\n", strlen(thirdCity));

	// strcpy is not safe, as it can overwrite memory of other parts
	// strlcpy is safe
	strlcpy(yourCity, "El Pueblo del la Reina de Los Angeles", sizeof(yourCity));

	printf("New city is %s\n\n", yourCity); // the part longer than the size limit is cut off


}