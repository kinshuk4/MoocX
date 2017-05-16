#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

int main(){ 
	FILE *pFile;
	char *buffer;
	size_t dataInFile; //represent the size of element in bytes


	pFile = fopen("randomnums.bin", "rb+"); // b for binary, r+ for both reading and writing

	if(pFile==NULL){

		perror("Error Occured"); // generate the error object and print the corresponding strings
		printf("Error code: %d\n", errno); // print the error

		printf("File being created\n");
		pFile = fopen("randomnums.bin", "wb+"); // since the file does not exist, create the file
		if(pFile==NULL){

			perror("Error Occured"); // generate the error object and print the corresponding strings
			printf("Error code: %d\n", errno); // print the error
			exit(1);
		}
 	}

 	int randomNumbers[20];
 	// create array of random numbers
 	for(int i=0;i<20;i++){
 		randomNumbers[i] = rand()%100;
 		printf("Number %d is %d\n", i, randomNumbers[i]);
 	}

 	//fwrite(const void *ptr, size_t size, size_t elem_num, FILE *filePointer)
 	fwrite(randomNumbers, sizeof(int), 20, pFile);

 	long randomIndexNumber;

 	int numberAtIndex;

 	printf("Which random number do you want? ");
 	scanf("%ld", &randomIndexNumber);
 	// fseek(FILE *file, long int offset, int whence)
 	fseek(pFile, randomIndexNumber*sizeof(int), SEEK_SET);
 	// fread(void *pointer, size_t size, size_t, numElement, FILE *filePointer)
 	fread(&numberAtIndex, sizeof(int), 1, pFile);

 	printf("The random number at index %ld is %d\n", 
 		randomIndexNumber, numberAtIndex);

	fclose(pFile);


	return 0;
}