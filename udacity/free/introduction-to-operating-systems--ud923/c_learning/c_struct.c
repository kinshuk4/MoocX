#include <stdio.h>


struct dogsFavs 
{
	char *food;
	char *friend;
};


typedef struct dog // here dog is defined as a type, so when a dog is instantiated, we do not need to write "struct" every time
{
	const char *name;
	const char *breed;
	int avgHeightCm;
	int avgWeightLbs;

	struct dogsFavs favouriteThings; // struct inside a struct
} dog;





void getDogInfo(struct dog theDog){
	printf("\n");

	printf("Name: %s\n", theDog.name);
	printf("Breed: %s\n", theDog.breed);
	printf("Avg Height: %d\n", theDog.avgHeightCm);
	printf("Avg Weight: %d\n", theDog.avgWeightLbs);
}

void getMemoryLocations(struct dog theDog){
	printf("Name Location: %p\n\n", theDog.name);
	printf("Breed Location: %p\n\n", theDog.breed);
	printf("Height Location: %p\n\n", &theDog.avgHeightCm);
	printf("Weight Location: %p\n\n", &theDog.avgWeightLbs);

}

void getDogsFavs(dog theDog){
	printf("\n");

	printf("%s loves %s and his friend is %s\n", 
		theDog.name, 
		theDog.favouriteThings.food,
		theDog.favouriteThings.friend);
}

void setDogWeight(dog *theDog, int newWeight){ // here a pointer is needed because the struct is copied then send to the function
	(*theDog).avgWeightLbs = newWeight; // need the () for struct because . has higher priority
	printf("The weight was changed to %d\n\n", (*theDog).avgWeightLbs);
	printf("The weight was changed to %d\n\n", theDog->avgWeightLbs); // the same as above
}

int main(){
	struct dog cujo = {"Cujo", "Saint Bernard", 90, 264};

	getDogInfo(cujo);

	struct  dog cujo2 = cujo; // cujo2 has the same memory address as cujo;

	getMemoryLocations(cujo);
	getMemoryLocations(cujo2);

	dog benji = {"Benji", "silky terrier", 25, 9, {"Meat", "Joe Camp"}}; // here a struct inside the struct is created
	getDogsFavs(benji);
	setDogWeight(&benji, 11);
	printf("The weight of benji is %d", benji.avgWeightLbs);
}
