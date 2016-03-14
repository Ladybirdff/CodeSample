#include "tweet.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

/*
	create a new node adding to the top of a linked list, if the head of a linked list is NULL,
	make the new node as the head
*/
node* add_to_front(node* head, char* hashtag){

	node* new;

	if (hashtag == NULL) return NULL;

	new = malloc(sizeof(node));
	if (new == NULL) return 0;

	new->name = malloc(strlen(hashtag) + 1);
	if (new->name == NULL) return 0;

	strcpy(new->name, hashtag);
	new->count = 1;
	new->next = NULL;

	if (head == NULL) return new;
	else {
		new->next = head;
	}
	return new;
}

/*
	according to a given hashtag, search the linked list for a node containing it,
	return a pointer to that node or NULL
*/
node* search_node(node* head, char* hashtag){

	node* temp = head;

	if ((head == NULL) || (hashtag == NULL)) return NULL;

	while (temp != NULL){
		if (strcmp(temp->name, hashtag) == 0) return temp;
		temp = temp->next;
	}
	return NULL;
}

/*
	sort the linked list in a descending order
	using bubble sort
*/
node* sort_descend(node* head){
	
	int i;
	int length = 0;
	node* temp;
	node* a;
	node* b;

	if (head == NULL) return NULL;

	// count for the length of the linked list
	temp = head;
	while (temp){
		length++;
		temp = temp->next;
	}

	// max loop times for bubble sort is same as its length
	for (i = 0; i < length; i++){
		a = head;
		b = head;

		// swap two nodes according to the count feild
		while (b->next != NULL){
			if (b->count < b->next->count){
				temp = b->next;
				b->next = b->next->next;
				temp->next = b;

				if (b == head){
					head = temp;
				}
				else a->next = temp;

				b = temp;
			}
			
			a = b;
			b = b->next;
		}
	}
	return head;
}

/*
	deallocate memory for the linked list, free each node 
*/
node* delete_list(node* head){

	node* temp;

	while (head){
		temp = head;
		head = head->next;
		free(temp->name);
		free(temp);
	}
	return head;
}




