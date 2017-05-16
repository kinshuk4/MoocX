#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

int main(void){

	FILE *pFile;
	size_t dataInFile;

	// Opens the file using binary mode
	pFile = fopen("randomnums.bin", "rb+");
	
	if(pFile == NULL){
	
		perror("Error Occurred");
		printf("Error Code: %d\n", errno);
		printf("File Being Created\n\n");
		
		pFile = fopen("randomnums.bin", "wb+");
		
		if(pFile == NULL){
		
			perror("Error Occurred");
			printf("Error Code: %d\n", errno);
			exit(1);
		
		}
	}
	
	// NEW STUFF ----------
	
	int randomNumbers[20];
	
	for(int i = 0; i < 20; i++){
	
		randomNumbers[i] = rand() % 100;
		
		printf("Number %d is %d\n", i, randomNumbers[i]);
	
	}
	
	// Write the array of ints to the file

	fwrite(randomNumbers, sizeof(int),
	 	20, pFile);
	
	long randomIndexNumber;
	int numberAtIndex;
	
	printf("Which Random Number do you Want? ");
	
	scanf("%ld", &randomIndexNumber);
	
	// When using fseek() in binary mode make sure the
	// offset is the number of the element * possible size 
	
	fseek(pFile, randomIndexNumber * sizeof(int), SEEK_SET);
	
	// Read the next int from the file
	
	fread(&numberAtIndex, sizeof(int), 1, pFile);
	
	printf("The Random Number at Index %d is %d\n",
		randomIndexNumber, numberAtIndex);
	
	// END OF NEW STUFF	
	
	// Closes the file associated with the stream
	
	fclose(pFile);

	return 0;
}