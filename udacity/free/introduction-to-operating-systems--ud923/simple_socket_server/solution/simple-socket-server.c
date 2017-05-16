/* ============================================================================
 * Introduction to Operating Systems
 * CS 8803, GT OMSCS
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * 
 * "simple-socket-server.c"
 * Implements the "Simple Socket: Server" in Problem Set 1.
============================================================================ */
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

/* CONSTANTS =============================================================== */
#define SERVER_PORT 8888

int main(int argc, char **argv) {

  int socket_fd = 0;
  int client_socket_fd = 0;
  
  int set_reuse_addr = 1; // ON == 1  
  int max_pending_connections = 1;

  struct sockaddr_in server;
  struct sockaddr_in client;
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
    close(client_socket_fd);
  }

  // Close the socket and return 
  close(socket_fd);
  return 0;
}