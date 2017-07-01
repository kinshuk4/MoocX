#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

int main(){ 
	FILE *pFile;
	char *buffer; // used to hold the data from the file
	size_t dataInFile; //represent the size of element in bytes

	long fileSize;

	pFile = fopen("names.bin", "rb+"); // b for binary, r+ for both reading and writing

	if(pFile==NULL){
		perror("Error Occured"); // generate the error object and print the corresponding strings
		printf("Error code: %d\n", errno); // print the error

		// create the file
		printf("File being created\n");
		pFile = fopen("name.bin", "wb+"); // since the file does not exist, create the file
		if(pFile==NULL){
			perror("Error Occured"); // generate the error object and print the corresponding strings
			printf("Error code: %d\n", errno); // print the error
			exit(1);
		}
 	}

 	char name[] = "Jiaxing Liu";

 	fwrite(name, sizeof(name[0]), sizeof(name)/sizeof(name[0]), pFile); // write to file
 	fseek(pFile, 0, SEEK_END); // going to the end of the file
 	fileSize = ftell(pFile); // get the size of the file. Basically ftell tells the position of the cursor

 	rewind(pFile); // go back to the beginning of the file

 	buffer = (char *)malloc(sizeof(char)*fileSize);
 	if(buffer==NULL){
		perror("Error Occured"); // generate the error object and print the corresponding strings
		printf("Error code: %d\n", errno); // print the error
		exit(2);
	}
	// read the file to buffer
	dataInFile = fread(buffer, 1, fileSize, pFile);

	if(dataInFile!=fileSize){
		perror("Error Occured"); // generate the error object and print the corresponding strings
		printf("Error code: %d\n", errno); // print the error
		exit(3);
	}

	printf("%s\n", buffer);
	fclose(pFile);
	free(buffer);


	return 0;
}