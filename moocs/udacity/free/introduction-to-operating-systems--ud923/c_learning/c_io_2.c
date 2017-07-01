#include <stdio.h>
#include <stdlib.h>

int main(){

	char buffer[1000];

	FILE *pFile;

	pFile = fopen("randomnumbers.txt", "r"); // open the file
	if(!pFile){ // if not opened
		printf("Error: couldn't open file");
		return 1;

	}

	while(fscanf(pFile, "%s", buffer)==1){
		puts(buffer);
	}

	/*
	// works the same as above
	while(fgets(buffer, 1000, pFile)!=NULL){
		printf("%s", buffer);
	}
	*/


	printf("Success reading from file\n");

	if(fclose(pFile)!=0){
		printf("Error: File not closed\n");
	}
	
	return 0;


}