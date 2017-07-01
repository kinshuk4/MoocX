#include <stdio.h>
#include <stdlib.h>

int main(){
	int randomNumber;

	FILE *pFile;

	pFile = fopen("randomnumbers.txt", "w"); // open/create the file in writing mode by overriding the existing the content
	
	if(!pFile){ // if not opened
		printf("Error: couldn't write to file");
		return 1;

	}

	for(int i=0;i<10;i++){
		randomNumber = rand()%100;
		fprintf(pFile, "%d\n", randomNumber);
	}
	printf("Success writing to file\n");

	if(fclose(pFile)!=0){
		printf("Error: File not closed\n");
	}
	
	return 0;


}