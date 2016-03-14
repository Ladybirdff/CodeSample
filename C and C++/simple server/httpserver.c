/* 
This code primarily comes from 
http://www.prasannatech.net/2008/07/socket-programming-tutorial.html
and
http://www.binarii.com/files/papers/c_sockets.txt
 */

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <pthread.h>

// define a linked list to hold the distinct names of files handled successful
typedef struct Filenames {
  char* name;
  struct Filenames* next;
} filenames;

// global variables
pthread_mutex_t lock;
char quit = '\0';
int pages_success = 0;
int pages_fail = 0;
int total_bytes = 0;
filenames* list = NULL;

// add a new node to the first of the global linked list
int add_first(char* str){
  filenames* new = malloc(sizeof(filenames));
  if (new == NULL) return -1;

  new->name = malloc(sizeof(char)*(strlen(str)+1));
  if (new->name == NULL) return -1;
  strcpy(new->name, str);

  new->next = list;
  list = new;
  return 0;
}

// look for whether a filename in the global linked list
// if it is true, return 1, otherwise return 0
int contains(char* str){
  filenames* temp = list;
  while(temp != NULL){
    if (strcmp(str, temp->name) == 0) return 1;
    temp = temp->next;
  }
  return 0;
}

// return a string in the heap containing all the file names in the global linked list
char* display(){
  int total_len = 0;
  filenames* temp = list;
  char* names;
  int counter = 0;

  if (list == NULL) return NULL;

// calculate the length of the return string
  while (temp != NULL){
    total_len += strlen(temp->name);
    counter++;
    temp = temp->next;
  }

// allocate memory and clean those memory slots
  names = malloc(sizeof(char) * (total_len + counter + 1));
  if (names == NULL) return NULL;
  memset(names, 0, total_len + counter + 1);

  temp = list;
  while (temp != NULL){
    strcat(names, temp->name);
    strcat(names, " ");
    temp = temp->next;
  }
  return names;

}

// deallocate all the memory of the global linked list
int delete(){
  filenames* temp;

  while(list != NULL){
    free(list->name);
    temp = list;
    list = list->next;
    free(temp);
  }
  return 0;
}

// a thread entry to ask for user input, if it is 'q', set the global variable quit
void* input_quit(void* p){

  char user_input[100];

  while (1){
    printf("enter 'q' to stop the server: ");
    scanf("%s", user_input);
    if ((strlen(user_input) == 1) && (user_input[0] == 'q')){
      // lock and set the global variable
      pthread_mutex_lock(&lock);
      quit = 'q';
      pthread_mutex_unlock(&lock);
      break;
    }
  }
    return NULL;
}

// start a server with a certain port number and root directory
int start_server(int PORT_NUMBER, char* ROOT_DIR)
{

      // structs to represent the server and client
      struct sockaddr_in server_addr,client_addr;    
      
      int sock; // socket descriptor

      // 1. socket: creates a socket descriptor that you later use to make other system calls
      if ((sock = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
	      perror("Socket");
	      exit(1);
      }
      int temp;
      if (setsockopt(sock,SOL_SOCKET,SO_REUSEADDR,&temp,sizeof(int)) == -1) {
	      perror("Setsockopt");
	      exit(1);
      }

      // configure the server
      server_addr.sin_port = htons(PORT_NUMBER); // specify port number
      server_addr.sin_family = AF_INET;         
      server_addr.sin_addr.s_addr = INADDR_ANY; 
      bzero(&(server_addr.sin_zero),8); 
      
      // 2. bind: use the socket and associate it with the port number
      if (bind(sock, (struct sockaddr *)&server_addr, sizeof(struct sockaddr)) == -1) {
	perror("Unable to bind");
	exit(1);
      }

      // 3. listen: indicates that we want to listen to the port to which we bound; second arg is number of allowed connections
      if (listen(sock, 1) == -1) {
	perror("Listen");
	exit(1);
      }
          
      // once you get here, the server is set up and about to start listening
      printf("\nServer configured to listen on port %d\n", PORT_NUMBER);
      fflush(stdout);
     
  // buffer to read data into
  char request[1024];
  int bytes_received;
  char* token;
  // allocate memory error message
  char* malloc_fail = "HTTP/1.1 500 Internal Server Error\n\n<html>500 Internal Server Error</html>";
  char* full_path;
  FILE* file;
  int file_length;
  // not found message
  char* not_found = "HTTP/1.1 404 Not Found\n\n<html>404 Not Found</html>";
  int sin_size;
  int fd;
  // a good header to send back a html file
  char* header = "HTTP/1.1 200 OK\nContene-Type: text/html\n\n";
  char check;
  char stats[500];
  char* names = NULL;
  
  // loop to listen resquest from clients
  while(1){
    // check if quit
    pthread_mutex_lock(&lock);
    check = quit;
    pthread_mutex_unlock(&lock);
    if (check == 'q') break;

    // 4. accept: wait here until we get a connection on that port
    sin_size = sizeof(struct sockaddr_in);
    fd = accept(sock, (struct sockaddr *)&client_addr,(socklen_t *)&sin_size);
    if (fd != -1) {
  	printf("Server got a connection from (%s, %d)\n", inet_ntoa(client_addr.sin_addr),ntohs(client_addr.sin_port));
        
    // 5. recv: read incoming message into buffer
    bytes_received = recv(fd,request,1024,0);
  	// null-terminate the string
  	request[bytes_received] = '\0';
    printf("request: %s\n", request);

    // seperate the request to get the file name
    token = strtok(request, " ");
    token = strtok(NULL, " ");

    // if request for /favicon.ico ignore it
    if (strcmp(token, "/favicon.ico") == 0){
      printf("Ignore getting favicon.ico.\n");
      close(fd);
      printf("Server closed connection\n");
      continue;
    }

    // if request for /stats, send back statistics infomation
    if (strcmp(token, "/stats") == 0){
      // get distinct file names in the global linked list
      names = display();
      // construct a repl message and send it back
      sprintf(stats, "HTTP/1.1 200 OK\nContene-Type: text/html\n\n<html><h1>Statistics</h1><p>Number of page requests handled successful: %d</p><p>Number of page requests can not be handled: %d</p><p>Number of bytes sent: %d</p><p>Names of files handled successful: %s</p></html>", pages_success, pages_fail, total_bytes, names);
      send(fd, stats, strlen(stats), 0);
      // deallocate memory
      free(names);
      close(fd);
      printf("Server closed connection\n");
      continue;
    }

    // allocate memory to hold the full path to get the html file
    full_path = malloc(sizeof(char) * (strlen(ROOT_DIR) + strlen(token) + 1));
    // error check
    if (full_path == NULL){
      pages_fail++;
      send(fd, malloc_fail, strlen(malloc_fail), 0);
      printf("Server sent message: %s\n", malloc_fail);
      close(fd);
      printf("Server closed connection\n");
      continue;
      // close(sock);
      // printf("Server shutting down\n");
      // exit(1);
    }

    // construct a full path
    strcpy(full_path, ROOT_DIR);
    strcat(full_path, token);
    full_path[strlen(full_path)] = '\0';
    printf("path: %s\n", full_path);

    // open the target file and error handle
    file = fopen(full_path, "r");
    if (file == NULL){
      pages_fail++;
      free(full_path);
      send(fd, not_found, strlen(not_found), 0);
      printf("Server sent message: %s\n", not_found);
      close(fd);
      printf("Server closed connection\n");
      continue;
    }
  	
    // get the length of the target file
    fseek(file, 0, SEEK_END);
    file_length = ftell(file);
    
    // allocate memory for the string to read the target file and the reply string
    char* read_file = malloc(sizeof(char) * (file_length + 1));
    char* reply = malloc(sizeof(char) * (strlen(header) + file_length + 1));
    // error handle
    if (read_file == NULL || reply == NULL){
      pages_fail++;
      send(fd, malloc_fail, strlen(malloc_fail), 0);
      printf("Server sent message: %s\n", malloc_fail);
      close(fd);
      printf("Server closed connection\n");
      continue;
      // close(sock);
      // printf("Server shutting down\n");
      // exit(1);
    }

    // update the statics global variables
    pages_success++;
    if (contains(token) == 0) add_first(token);
    // read the target file and construct the reply message
    rewind(file);
    fread(read_file, sizeof(char), file_length, file);
    strcpy(reply, header);
    strcat(reply, read_file);
    total_bytes = total_bytes + sizeof(char) * strlen(reply);
  	
  	// 6. send: send the message over the socket
  	// note that the second argument is a char*, and the third is the number of chars
  	send(fd, reply, strlen(reply), 0);
  	printf("Server sent message: %s\n", reply);
    // close the file and deallocate memory
    fclose(file);
  	free(reply);
    free(read_file);
    free(full_path);
	}
	// 7. close: close the connection
	close(fd);
	printf("Server closed connection\n");
      }

      // 8. close: close the socket
      close(sock);
      printf("Server shutting down\n");
  
      return 0;
} 



int main(int argc, char *argv[])
{ 
  int ret_val;
  pthread_t thread;

  // check the number of arguments
  if (argc != 3) {
      printf("\nUsage: %s [port_number] [root directory]\n", argv[0]);
      exit(-1);
  }

  // check the port number
  int port_number = atoi(argv[1]);
  if (port_number <= 1024) {
    printf("\nPlease specify a port number greater than 1024\n");
    exit(-1);
  }

  // start a new thread for user input
  ret_val = pthread_create(&thread, NULL, &input_quit, NULL);
  if (ret_val != 0){
    printf("Error when starting a new thread.\n");
    return -1;
  }
  // start the server
  start_server(port_number, argv[2]);

  // join all the threads
  ret_val = pthread_join(thread, NULL);
  if (ret_val != 0){
    printf("Error when joining a thread.\n");
    return -1;
  }

  // deallocate memory for the global linked list
  delete();

  return 0;
}

