#include <stdio.h>
#include <stdlib.h>

int main(){

	char buffer[1000];

	FILE *pFile;

	pFile = fopen("randomwords.txt", "r+"); // open the file
	if(!pFile){ // if not opened
		printf("Error: couldn't open file");
		return 1;

	}

	fputs("Messing with strings", pFile);

	fseek(pFile, 12, SEEK_SET); 
	// SEEK_SET - start from the beginning of the file
	//SEEK_CUR - move based off of current position in file
	//SEEK_END - move based off of starting at end

	fputs(" Files ", pFile);


	printf("Success reading from file\n");

	fseek(pFile, 0, SEEK_SET);
	while(fgets(buffer, 1000, pFile)!=NULL){
		printf("%s", buffer);
	}
	
	if(fclose(pFile)!=0){
		printf("Error: File not closed\n");
	}
	
	return 0;


}