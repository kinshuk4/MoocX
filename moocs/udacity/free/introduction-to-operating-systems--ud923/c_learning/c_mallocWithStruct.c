#include <stdio.h>
#include <stdlib.h>

struct product{

	float price;
	char productName[30];

};

int main(){

	struct product *pProducts;

	int i,j;

	int numberOfProducts;

	printf("Enter the number of products to store: ");

	scanf("%d", &numberOfProducts);

	pProducts = (struct product *)malloc(numberOfProducts * sizeof(struct product));

	for(i=0; i<numberOfProducts; i++){
		printf("Enter a product name: ");
		scanf("%s", &(pProducts+i)->productName);

		printf("Enter a product price: ");
		scanf("%f", &(pProducts+i)->price);

	}

	printf("Products Stored\n");
	for (j=0; j < numberOfProducts; j++){
		printf("%s\t%.2f\n", (pProducts+j)->productName, (pProducts+j)->price);
	}

	free(pProducts);


	return 0;

}