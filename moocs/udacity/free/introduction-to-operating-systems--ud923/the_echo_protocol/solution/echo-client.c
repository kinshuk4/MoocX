/* ============================================================================
 * Introduction to Operating Systems
 * CS 8803, GT OMSCS
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * 
 * "echo-client.c"
 * Implements the client for "The Echo Protocol" in Problem Set 1.
============================================================================ */
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <strings.h>
#include <stdlib.h>
#include <unistd.h>

/* CONSTANTS =============================================================== */
#define SERVER_ADDR "localhost"
#define SERVER_PORT 8888
#define BUFFER_SIZE 1024
#define MESSAGE "ramblin wreck"

int main(int argc, char **argv) {

  int socket_fd = 0;
  struct sockaddr_in server_socket_addr;
  char buffer[BUFFER_SIZE];

  // Converts localhost into 0.0.0.0
  struct hostent *he = gethostbyname(SERVER_ADDR);
  unsigned long server_addr_nbo = *(unsigned long *)(he->h_addr_list[0]);

  // Create socket (IPv4, stream-based, protocol likely set to TCP)
  if (0 > (socket_fd = socket(AF_INET, SOCK_STREAM, 0))) {
    fprintf(stderr, "client failed to create socket\n");
    exit(1);
  }

  // Configure server socket address structure (init to zero, IPv4,
  // network byte order for port and address) 
  bzero(&server_socket_addr, sizeof(server_socket_addr));
  server_socket_addr.sin_family = AF_INET;
  server_socket_addr.sin_port = htons(SERVER_PORT);
  server_socket_addr.sin_addr.s_addr = server_addr_nbo;

  // Connect socket to server
  if (0 > connect(socket_fd, (struct sockaddr *)&server_socket_addr, sizeof(server_socket_addr))) {
    fprintf(stderr, "client failed to connect to %s:%d!\n", SERVER_ADDR, SERVER_PORT);
    close(socket_fd);
    exit(1);
  } else {
    fprintf(stdout, "client connected to to %s:%d!\n", SERVER_ADDR, SERVER_PORT);
  }

  // Send echo message
  if (0 > send(socket_fd, MESSAGE, strlen(MESSAGE), 0)) {
    fprintf(stderr, "client failed to send echo message");
    close(socket_fd);
    exit(1);
  }

  // Process response from server
  bzero(buffer, BUFFER_SIZE);
  if(0 > read(socket_fd, buffer, BUFFER_SIZE)) {
    fprintf(stderr, "client could not read response from server\n");
    close(socket_fd);
    exit(1);
  } else {
    fprintf(stdout, "echo from server: %s\n", buffer);
  }

  // Close the socket and return the response length (in bytes)
  close(socket_fd);
  return 0; 
}