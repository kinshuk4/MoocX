#include <stdio.h>
#include <pthread.h>

#define NUM_THREADS 4

void *threadFunc (void *args) {
	int *p = (int*)args;
	int num = *p;
	printf("Thread number %d\n", num);
	return 0;
}
int main (void) {
	int i;
	pthread_t tid[NUM_THREADS];
	for(i = 0; i < NUM_THREADS; i++) {
		pthread_create(&tid[i], NULL, threadFunc, &i);
	}
	for(i = 0; i < NUM_THREADS; i++) {
		pthread_join(tid[i], NULL);
	}
	return 0;
}