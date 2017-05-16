#include <stdio.h>

typedef struct product
{
	const char *name;
	float price;
	struct product *next; // creating the pointer of the linked list
} product;

void printLinkedList(product *pProduct){
	while (pProduct != NULL){
		printf("A %s costs %.2f\n\n",
			(*pProduct).name,
			(*pProduct).price);
		pProduct = (*pProduct).next;
	}
}

int main(){

	product tomato = {"Tomato", .51, NULL};
	product potato = {"Potato", 1.21, NULL};
	product lemon = {"Lemon", 1.69, NULL};
	product milk = {"Milk", 3.09, NULL};
	product turkey = {"Turkey", 1.86, NULL};

	tomato.next = &potato;
	potato.next = &lemon;
	lemon.next = &milk;
	milk.next = &turkey;

	product apple = {"Apple", 1.58, NULL};

	lemon.next = &apple;
	apple.next = &milk;

	printLinkedList(&tomato);

}