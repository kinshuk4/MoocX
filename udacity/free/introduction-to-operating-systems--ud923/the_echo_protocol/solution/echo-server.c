/* ============================================================================
 * Introduction to Operating Systems
 * CS 8803, GT OMSCS
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * 
 * "echo-server.c"
 * Implements the server for "The Echo Protocol" in Problem Set 1.
============================================================================ */
#include <arpa/inet.h>
#include <ctype.h>
#include <netdb.h>
#include <netinet/in.h> 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

/* CONSTANTS =============================================================== */
#define SERVER_PORT 8888
#define BUFFER_SIZE 1024

void capitalizeBuffer(char *buffer) {
  do {
      *buffer = toupper((unsigned char) *buffer);
  } while (*buffer++);
}

int main(int argc, char **argv) {

  int socket_fd = 0;
  int client_socket_fd = 0;
  
  char buffer[BUFFER_SIZE];
  int num_bytes = 0;

  int set_reuse_addr = 1; // ON == 1  
  int max_pending_connections = 1;

  struct sockaddr_in server;
  struct sockaddr_in client;
  struct hostent *client_host_info;
  char *client_host_ip;
  socklen_t client_addr_len; 

  // Create socket (IPv4, stream-based, protocol likely set to TCP)
  if (0 > (socket_fd = socket(AF_INET, SOCK_STREAM, 0))) {
    fprintf(stderr, "server failed to create the listening socket\n");
    exit(1);
  }

  // Set socket to use wildcards - i.e. 0.0.0.0:21 and 192.168.0.1:21
  // can be bound separately (helps to avoid conflicts) 
  if (0 != setsockopt(socket_fd, SOL_SOCKET, SO_REUSEADDR, &set_reuse_addr, sizeof(set_reuse_addr))) {
    fprintf(stderr, "server failed to set SO_REUSEADDR socket option (not fatal)\n");
  }

  // Configure server socket address structure (init to zero, IPv4,
  // network byte order for port and address)
  // Address uses local wildcard 0.0.0.0.0 (will connect to any local addr)
  bzero(&server, sizeof(server));
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = htonl(INADDR_ANY);
  server.sin_port = htons(SERVER_PORT);

  // Bind the socket
  if (0 > bind(socket_fd, (struct sockaddr *)&server, sizeof(server))) {
    fprintf(stderr, "server failed to bind\n");
    exit(1);
  }

  // Listen on the socket for up to some maximum pending connections
  if (0 > listen(socket_fd, max_pending_connections)) {
    fprintf(stderr, "server failed to listen\n");
    exit(1);
  } else {
    fprintf(stdout, "server listening for a connection on port %d\n", SERVER_PORT);
  }

  // Get the size client's address structure
  client_addr_len = sizeof(client);

  // Accept a new client
  if (0 > (client_socket_fd = accept(socket_fd, (struct sockaddr *)&client, &client_addr_len))) 
  {
    fprintf(stderr, "server accept failed\n");
  } else {
    fprintf(stdout, "server accepted a client!\n");    
  }

  // Determine who sent the echo so that we can respond
  client_host_info = gethostbyaddr((const char *)&client.sin_addr.s_addr, sizeof(client.sin_addr.s_addr), AF_INET);
  if (client_host_info == NULL) {
    fprintf(stderr, "server could not determine client host address\n");
  } 
  client_host_ip = inet_ntoa(client.sin_addr);
  if (client_host_ip == NULL) {
    fprintf(stderr, "server could not determine client host ip\n");
  }
  fprintf(stdout, "server established connection with %s (%s)\n", client_host_info->h_name, client_host_ip);
    
  // Read echo from the client
  bzero(buffer, BUFFER_SIZE);
  num_bytes = read(client_socket_fd, buffer, BUFFER_SIZE);
  if (num_bytes == 0) {
    fprintf(stderr, "server could not read from socket\n");
  } else {
    fprintf(stdout, "server received %d bytes: %s\n", num_bytes, buffer);
  }
   
  // Optional: Modifying client message before echo'ing (correctness test)
  capitalizeBuffer(buffer);

  // Echo back to the client
  if (0 > write(client_socket_fd, buffer, strlen(buffer))) {
    fprintf(stderr, "server could not write back to socket\n");
  } else {
    fprintf(stdout, "server sending message back to client\n");
  }

  // Close the socket and return 
  close(socket_fd);
  return 0;
}