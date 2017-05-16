#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct product
{
	float price;
	char productName[30];

	struct product *next;
};

struct product *pFirst = NULL;
struct product *pLast = NULL;


void createNewList(){
	struct product *pNewStruct = (struct product *)malloc(sizeof(struct product)); // created a pointer to a new struct
	pNewStruct->next = NULL;

	printf("Enter product name: ");
	//scanf("%s", &(pNewStruct)->productName);
	scanf("%s", pNewStruct->productName);
	
	printf("Enter product price: ");
	//scanf("%f", &(pNewStruct)->price);
	scanf("%f", &(pNewStruct->price)); // this line is equivalent to the above
	pFirst = pLast = pNewStruct;
}
void inputData(){

	if(pFirst == NULL){

		createNewList();

	}else{

	struct product *pNewStruct = (struct product *)malloc(sizeof(struct product)); // created a pointer to a new struct
	printf("Enter product name: ");
	//scanf("%s", &(pNewStruct)->productName);
	scanf("%s", pNewStruct->productName);
	
	printf("Enter product price: ");
	//scanf("%f", &(pNewStruct)->price);
	scanf("%f", &(pNewStruct->price));

	if(pFirst == pLast){  // creating the second node
		pFirst->next = pNewStruct;
		pLast = pNewStruct;
		pNewStruct->next = NULL;
	}else{ // creating the third or more node
		pLast->next = pNewStruct;
		pNewStruct->next = NULL;
		pLast = pNewStruct;
	}

	}

}

void outputData(){

	struct product *pProducts = pFirst;

	printf("Products entered\n");

	while(pProducts!= NULL){
		printf("%s costs %.2f\n", pProducts->productName, pProducts->price);
		pProducts = pProducts->next;
	}
}

struct product *pProductBeforeProductToDelete = NULL;

struct product *searchForProduct(char *productName){

	struct product *pProductIterator = pFirst;
	while(pProductIterator != NULL){

		int areTheyEqual = strncmp(pProductIterator->productName, productName, 30);
		if(!areTheyEqual){
			printf("%s was found and it casts %.2f\n\n", pProductIterator->productName,
				pProductIterator->price);

			return pProductIterator;
		}

		pProductBeforeProductToDelete = pProductIterator;
		pProductIterator = pProductIterator->next;


	}

	printf("%s wasn't found\n\n", productName);

}

void removeProduct(char *productName){

	struct product *pProductToDelete = NULL;

	pProductToDelete = searchForProduct(productName);

	if(pProductToDelete!=NULL){

		printf("%s was deleted\n\n", productName);

		if(pProductToDelete == pFirst){
			pFirst = pProductToDelete->next;
		}else{
			pProductBeforeProductToDelete->next = pProductToDelete->next;


		}
		free(pProductToDelete);
	}else{
		printf("%s was not found", productName);
	}

}


int main(){

	inputData();
	inputData();
	inputData();
	outputData();
	//searchForProduct("tomato");
	removeProduct("tomato");


}