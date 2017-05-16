#include <stdio.h>
#include <stdlib.h>

int main(){
	int amtOfNumbersToStore;

	printf("How many number do ou want to store: ");

	scanf("%d", &amtOfNumbersToStore);

	int *pRandomNumbers;

	pRandomNumbers = (int *)malloc(amtOfNumbersToStore*sizeof(int));

	if(pRandomNumbers != NULL){

		int i=0;

		printf("Enter a number or quit: ");

		while(i<amtOfNumbersToStore && 
			scanf("%d", &pRandomNumbers[i])==1){

			printf("Enter a number of quit:");
			i++;


		}
		printf("\nYou entered the following numbrs\n");

		for(int j=0;j<i;j++){
			printf("%d\n", pRandomNumbers[j]);
		}


	}

	free(pRandomNumbers);


	return 0;
}