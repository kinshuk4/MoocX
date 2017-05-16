#include <stdio.h>
#include <pthread.h>

#define NUM_THREADS 4

void *threadFunc (void *args) {
	int num = *((int*)args);
	printf("Thread number %d\n", num);
	return 0;
}
int main (void) {
	int i;
	pthread_t tid[NUM_THREADS];
	int tNUM[NUM_THREADS];
	for(i = 0; i < NUM_THREADS; i++) {
		tNUM[i] = i;
		pthread_create(&tid[i], NULL, threadFunc, &tNUM[i]);
	}
	for(i = 0; i < NUM_THREADS; i++) {
		pthread_join(tid[i], NULL);
	}
	return 0;
}