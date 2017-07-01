#include <stdio.h>

int main(){
	// a union is different from struct in that it can only store one field at a time
	typedef union{ // create a union data type called amount
		short individual;
		float pound;
		float ounce;

	} amount;

	amount orangeAmt = {.ounce =16 };
	orangeAmt.individual = 4; //this is the same as above

	// the example below shows the output of union only distinguishes by data type instead of by field
	// the output is not trust worth
	printf("orange juice amount: %.2f : size: %lu\n\n",
			orangeAmt.ounce,
			sizeof(orangeAmt.ounce));

	printf("Number of oranges: %d, size: %lu\n\n",
			orangeAmt.individual,
			sizeof(orangeAmt.individual));
	/*
	printf("Number of oranges: %.2f, size: %lu\n\n",
			orangeAmt.individual,
			sizeof(orangeAmt.individual));
	*/

	orangeAmt.pound = 1.5;

	printf("orange juice amount by pound: %.2f : size: %lu\n\n",
			orangeAmt.pound,
			sizeof(orangeAmt.pound));

	printf("orange juice amount by ounce: %.2f : size: %lu\n\n",
			orangeAmt.ounce,
			sizeof(orangeAmt.ounce));
	typedef struct{
		char *brand;
		amount theAmount;
	} orangeProduct;

	orangeProduct productOrdered = {"Chiquita",
		.theAmount.ounce = 16};

	printf("You bought %.2f ounces of %s organges \n\n", 
			productOrdered.theAmount.ounce,
			productOrdered.brand);

	typedef enum{INDIV, OUNCE, POUND} quantity;

	quantity quantityType = INDIV;

	orangeAmt.individual = 4;

	if(quantityType == INDIV){
		printf("You bought %d oranges\n\n",
			 orangeAmt.individual);
	}else if(quantityType == OUNCE){
		printf("You bought %.2f ounces oranges\n\n",
			 orangeAmt.ounce);
	}else{
		printf("You bought %.2f pound oranges\n\n",
			 orangeAmt.pound);
	}

}