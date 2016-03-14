#include <stdio.h>
#include <stdlib.h>
#include "linked_list2.h"
#include <string.h>

/* definition of linked list helper functions */




/*
 * this function adds a node to the end of the linked list 
 * -returns head_node - or NULL if error
 */
struct_of_ints* add_to_end (struct_of_ints* head_node, char* chars ) {

	int i = strlen(chars) + 1;

	struct_of_ints* ptr = head_node;
	/* allocate memory for the new node*/
	struct_of_ints* new_node = NULL;
	new_node = malloc (sizeof(struct_of_ints));

	/* check whether the list is empty or not, if so make the new node to be the head_node*/
	if (head_node == NULL){
		if (new_node == NULL) return NULL; 			/*if cannot allocate memory for a new node, return null*/
		new_node->prev = NULL;
		new_node->next = NULL;
		/* allocate memory for the string*/
		new_node->string = malloc(sizeof(char)*i);
		/* check for malloc*/
		if (new_node->string == NULL) return NULL;
		/* copy the string*/
		strcpy(new_node->string, chars);
		head_node = new_node;
		return head_node;
	}

	/* pointe to the last node of the list*/
	while (ptr->next){
		ptr = ptr->next;
	}

	if (new_node == NULL) return NULL ;  			/*if cannot allocate memory for a new node, return null*/
	/* make the new node to be the last node of the list*/
	new_node->prev  = ptr  ;
	new_node->next  = NULL ;
	/* allocate memory for the string*/
	new_node->string = malloc(sizeof(char)*i);
	/* copy string*/
	strcpy(new_node->string, chars);

	ptr->next = new_node;

	/* return a new head_node*/
	return head_node ;
}


/*
 * deletes the first instance of the "value" from the list
 * -returns deleted node - or NULL if node isn't in list
 */
struct_of_ints*	delete_node (struct_of_ints* head_node, char* chars ) {

	struct_of_ints* ptr;
	struct_of_ints* temp1;
	struct_of_ints* temp2;

	/* call search_lsit to search the node which is going to be deleted*/
	ptr = search_list (head_node, chars);

	if (ptr==NULL) return NULL ;		/* if can not find the node, return null*/

	/* pointe to next and prev nodes*/
	temp1 = ptr->prev;
	temp2 = ptr->next;

	/* if the list contains only one node that to be deleted, make the head node null*/
	if (temp1 == NULL && temp2 == NULL){
		head_node = NULL;
	}

	/* if the target node have next and prev nodes, set those two nodes' next and prev to skip the target*/
	if (temp1 != NULL && temp2 != NULL){
		temp1->next = temp2;
		temp2->prev = temp1;
	}

	/* if the target node is the first node, set the head node to the second node*/
	if (temp1 == NULL && temp2 != NULL){
		temp2->prev = NULL;
		head_node = temp2;
	}

	/* if the target node is the last node, set up next of the node before it to be a new last node*/
	if (temp2 == NULL && temp1 != NULL){
		temp1->next = NULL;
	}

	/* free the node which is deleted as well as the string*/
	free(ptr->string);
	free(ptr);

	/* return a new head node*/
	return head_node;
}

/*
 * searches list for first instance of the "value" passed in
 * -returns node if found - or a NULL if node isn't in list
 */
struct_of_ints* search_list (struct_of_ints* head_node, char* chars ) {

	struct_of_ints* ptr;

	/* if the list is empty, return null*/
	if (head_node == NULL) return NULL ;

	/* loop through the list to find a node*/
	ptr = head_node;
	while (strcmp(ptr->string, chars) != 0){
		ptr = ptr->next;
		if (ptr == NULL){return NULL;}
	}

	return ptr;

}


/*
 * delete every node in the linked list 
 * returns NULL if successful, otherwise head node is returned
 */
struct_of_ints* delete_list (struct_of_ints* head_node ) {

	struct_of_ints* temp;

	/*loop through the list to free each node as well as the string*/
	while (head_node){
		temp = head_node;
		head_node = head_node->next;
		free(temp->string);
		free(temp);
	}

	return head_node ;
}

/* detemine the length of a linked list*/
int list_length(struct_of_ints* head_node){
	int length = 0;
	struct_of_ints* ptr = head_node;

	while (ptr != NULL){
		length = length + 1;
		ptr = ptr->next;
	}

	return length;
}
