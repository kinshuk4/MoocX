#include <stdio.h>
#include <pthread.h>

#define NUM_THREADS 4

void *hello (void *args) {
	printf("Hello Thread\n");
	return 0;
}
int main (void) {
	int i;
	pthread_t tid[NUM_THREADS];
	for(i = 0; i < NUM_THREADS; i++) {
		pthread_create(&tid[i], NULL, hello, NULL);
	}
	for(i = 0; i < NUM_THREADS; i++) {
		pthread_join(tid[i], NULL);
	}
	return 0;
}