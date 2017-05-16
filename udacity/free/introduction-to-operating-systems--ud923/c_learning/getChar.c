#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

void noMoreNewLine(char* theString){ // remove the \n at the end
	char *isANewLine;
	isANewLine = strrchr(theString, "\n"); // find the last occurance
	if(isANewLine){

		*isANewLine = '\0'; //remove the new line

	}
}
void makeLowercase(char* theString){

	int i = 0;

	while(theString[i]){
		theString[i] = tolower(theString[i]);
		i++;
	}
}

int main(){
	char doYouWantToQuit[10];
	printf("Enter quit to quit: ")	;
	fgets(doYouWantToQuit, 10, stdin);
	noMoreNewLine(doYouWantToQuit);
	printf(doYouWantToQuit);
	makeLowercase(doYouWantToQuit);

	while(strcmp(doYouWantToQuit, "quit")){
		printf("Enter quit to quit: ")	;
		fgets(doYouWantToQuit, 10, stdin);
		noMoreNewLine(doYouWantToQuit);
		printf(doYouWantToQuit);
		makeLowercase(doYouWantToQuit);

	}

	char *randomString = "just some random stuff";
	while(*randomString){ //iterate the string until the end which is a null
		putchar(*randomString++); //increase the string until the end which is a null
	} // note in this way, the randomString is modified inplace, i.e. it is effectively popping the first element each time

	int i = 0;
	while(randomString[i]!='\0'){
		putchar(randomString[i++]);
	}


	char name[50];
	printf("What is your name?\n");
	gets(name); // the \n entered is replaced will null
	// gets does not check the length
	puts("Hi"); // print the string followed by \n
	puts("Hi");
	puts(name);

	fgets(name, 50, stdin); // 50 is the size limit as a cutoff, also put \n at the end
	fputs("Hi ", stdout);
	fputs(name, stdout); //fputs does not add a new line at the end of string



	char theChar;

	while((theChar = getchar()) != '~'){
		putchar(theChar);
	}
}