/* ============================================================================
 * Introduction to Operating Systems
 * CS 8803, GT OMSCS
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * 
 * "priority-readers-and-writers.h"
 * Defines entry points for "Priority Readers and Writers" in Problem Set 1.
============================================================================ */
#ifndef PS1_PRIORITY_READERS_AND_WRITERS_H_
#define PS1_PRIORITY_READERS_AND_WRITERS_H_

int main(int argc, char **argv);
void *readerMain(void *threadArgument);
void *writerMain(void *threadArgument);

#endif // PS1_PRIORITY_READERS_AND_WRITERS_H_