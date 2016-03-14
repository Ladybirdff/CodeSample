#include "state.h"
#include "tweet.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char** argv){
	
	FILE* tweets;
	state* states;
	int i;
	int match = 0;
	char buff[800];
	char* token;
	double lat;
	double lon;
	char* state;
	node* head = NULL;
	node* temp;

	// error check for the number of runtime arguments
	if (argc != 4){
		printf("ERROR! The number of arguments is not correct.\n");
		return -1;
	}

	// error check for openning tweets file
	tweets = fopen(argv[1], "r");
	if (tweets == NULL){
		printf("ERROR! Can not open tweets file to read.\n");
		return -1;
	}

	// error check for openning states file and return states array
	states = read_states_file(argv[2]);
	if (states == NULL){
		printf("ERROR! Can not open states file to read.\n");
		fclose(tweets);
		return -1;
	}

	// error check for the specified state is in the file of the list of state
	for (i = 0; i < 51; i++){
		if (strcmp(states[i].name, argv[3]) != 0){
			match++;
		}
	}
	if (match == 51){
		printf("ERROR! This specified state can not be found in the states file.\n");

		fclose(tweets);

		for (i = 0; i < 51; i++){
			if (states[i].name != NULL) free(states[i].name);
		}
		free(states);
		return -1;
	}

	// loop for reading tweets file line by line using fgets
	while (fgets(buff, 800, tweets) != NULL){
		// read latitude
		token = strtok(buff, "[,");
		lat = atof(token);
		// read longitude
		token = strtok(NULL, " ]");
		lon = atof(token);
		// determine which state this tweet on
		state = determine_state(states, lat, lon);
		// if the tweet is in the specified state, read for hashtags of it
		if (strcmp(state, argv[3]) == 0){
			token = strtok(NULL, " \t\n");
			while (token != NULL){
				// search for the linked list to find if a node of this hashtag have been created
				// if yes, increment the count, otherwise, create a new node
				if (strncmp(token, "#", 1) == 0){
					temp = search_node(head, token);
					if (temp == NULL){
						head = add_to_front(head, token);
					}
					else{
					 	temp->count = temp->count + 1;
					 }
				}
				// read for next hashtag
				token = strtok(NULL, " \t\n");
			}
		}
	}

	// sorting the linked list
	head = sort_descend(head);

	// print the top 10 popular hashtags
	temp = head;
	for (i = 0; i < 10; i++){
		if (temp != NULL){
			printf("%s %d\n", temp->name, temp->count);
			temp = temp->next;
		}
	}

	// deallocate memory
	head = delete_list(head);

	fclose(tweets);

	for (i = 0; i < 51; i++){
		if (states[i].name != NULL) free(states[i].name);
	}
	free(states);


}


