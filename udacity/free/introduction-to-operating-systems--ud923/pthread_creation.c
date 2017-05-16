#include <stdio.h>
#include <pthread.h>

void *foo (void *arg) {
	printf("Hello World!");
	pthread_exit(NULL);
}

int main (void) {

	pthread_t aThread;
	pthread_attr_t attr;
	pthread_attr_init(&attr);
	pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);
	pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);
	pthread_create(&aThread, &attr, foo, NULL);
	return 0;

}