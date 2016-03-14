#include <stdio.h>
#include <stdlib.h>
#include "linked_list.h"

/* definition of linked list helper functions */


/*
 * this function adds a node to the beginning of the linked list
 * -returns head_node - or NULL if error
 */
struct_of_ints* add_to_top (struct_of_ints* head_node, int value ) {

	/* allocate memory for the new node */
	struct_of_ints* new_node = NULL ;
	new_node = malloc (sizeof(struct_of_ints)) ;

	/* check if malloc worked, otherwise, populate new node */
	if (new_node == NULL) return NULL ;
	new_node->value = value ;
	new_node->prev  = NULL  ;
	new_node->next  = head_node ;

	/* update former head pointer's links */
	if (head_node) head_node->prev = new_node ;

	/* return new head node */
	return new_node ;
}

/*
 * this function adds a node to the end of the linked list 
 * -returns head_node - or NULL if error
 */
struct_of_ints* add_to_end (struct_of_ints* head_node, int value ) {

	struct_of_ints* ptr = head_node;

	/* allocate memory for the new node*/
	struct_of_ints* new_node = NULL;
	new_node = malloc (sizeof(struct_of_ints));

	/* if the list is empty, set the head node pointing to the new node*/
	if (head_node == NULL){
		if (new_node == NULL) return NULL;    /* check new node is null or not*/
		new_node->value = value;
		new_node->prev = NULL;
		new_node->next = NULL;
		head_node = new_node;
		return head_node;                     /* return a new head node*/
	}

	/* point to the last node of the list*/
	while (ptr->next){
		ptr = ptr->next;
	}

	if (new_node == NULL) return NULL ;		/* check new node is null or not*/
	/* add the new node to the end of the list*/
	new_node->value = value ;
	new_node->prev  = ptr  ;
	new_node->next  = NULL ;

	ptr->next = new_node;

	/* return a new head_node*/
	return head_node ;
}


/*
 * deletes the first instance of the "value" from the list
 * -returns head_node - or NULL if node isn't in list
 */
struct_of_ints*	delete_node (struct_of_ints* head_node, int value ) {

	/* local variables*/
	struct_of_ints* ptr;
	struct_of_ints* temp1;
	struct_of_ints* temp2;

	/* call search_lsit to search the node which is going to be deleted*/
	ptr = search_list (head_node, value);

	if (ptr==NULL) return NULL ;	/* if can not find the node, return null*/

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

	/* free the node which is deleted*/
	free(ptr);

	/* return a new head node*/
	return head_node;
}

/*
 * searches list for first instance of the "value" passed in
 * -returns node if found - or a NULL if node isn't in list
 */
struct_of_ints* search_list (struct_of_ints* head_node, int value ) {

	struct_of_ints* ptr;

	/* if the list is empty, return null*/
	if (head_node == NULL) return NULL ;

	/* loop through the list to find a node*/
	ptr = head_node;
	while (ptr->value != value){
		ptr = ptr->next;
		if (ptr == NULL){return NULL;}
	}

	return ptr;

}

/* define a new function to find a node having the max value, assuming would not pass a null head_node into the function
*return a pointer to the node having the max value
*/
struct_of_ints* find_max (struct_of_ints* head_node){
	struct_of_ints* temp;
	struct_of_ints* max;

	/*set max to be the first node of the list first*/
	max = head_node;
	temp = head_node->next;

	/*loop through the list, if a node is larger than the current max, make it to be max*/
	while (temp != NULL){
		if (temp->value > max->value){
			max = temp;
		}
		temp = temp->next;
	}
	return max;
}


/*
 * sorts linked list in acending order: 1, 2, 3...
 * -returns new head of linked list after sort, or NULL if list is empty
 */
struct_of_ints* sort_list (struct_of_ints* head_node ) {
	int length = 0;
	int i;
	struct_of_ints* max;
	struct_of_ints* new_head = NULL;
	struct_of_ints* temp = head_node;

	/*if the list is empty, return null*/
	if (head_node == NULL) return NULL ;

	else{
		/*temp pointes to the last node of the list*/
		while (temp){
			length++;
			temp = temp->next;
		}
		/*find the first, second ... max node of the original list, and then put them on top of a new list one by one,
		*the new list will be in acending order, as well as delete the original list*/
		for (i=0;i<length;i++){
			max = find_max(head_node);
			new_head = add_to_top(new_head, max->value);
			head_node = delete_node(head_node, max->value);
		}

		/*return a new list in acending order*/
		return new_head;
	}
}

/* define a new function to find a node having the min value, assuming would not pass a null head_node into the function
*return a pointer to the node having the min value
*/
struct_of_ints* find_min (struct_of_ints* head_node){
	struct_of_ints* temp;
	struct_of_ints* min;

	/*set min to be the first node of the list first*/
	min = head_node;
	temp = head_node->next;

	/*loop through the list, if a node is smaller than the current min, make it to be min*/
	while (temp != NULL){
		if (temp->value < min->value){
			min = temp;
		}
		temp = temp->next;
	}
	return min;
}

/*
 * sorts linked list in decending order: 3, 2, 1...
 * -returns new head of linked list after sort, or NULL if list is empty
 */
struct_of_ints* sort_list_rev (struct_of_ints* head_node ) {
	int length = 0;
	int i;
	struct_of_ints* min;
	struct_of_ints* new_head = NULL;
	struct_of_ints* temp = head_node;

	/*if the list is empty, return null*/
	if (head_node == NULL) return NULL ;

	else{
		/*temp pointes to the last node of the list*/
		while (temp){
			length++;
			temp = temp->next;
		}

		/*find the first, second ... min node of the original list, and then put them on top of a new list one by one,
		*the new list will be in decending order, as well as delete the original list*/
		for (i=0;i<length;i++){
			min = find_min(head_node);
			new_head = add_to_top(new_head, min->value);
			head_node = delete_node(head_node, min->value);
		}

		/*return a new list in decending order*/
		return new_head;
	}
}


/*
 * prints entire linked list to look like an array
 */
void print_list (struct_of_ints* head_node ) {

	int i = 0 ;
	struct_of_ints* ptr;

	/*print the head_node first*/
	if (head_node) printf ("\tlist[%d]=%d \n", i, head_node->value ) ;

	ptr = head_node->next;

	/*loop through the list to print*/
	while (ptr){
		i++;
		printf ("\tlist[%d]=%d \n", i, ptr->value) ;
		ptr = ptr->next;
	}

	/* CIT 593 to do: this code only prints the first node,
			  print out the rest of the list! */

}


/*
 * delete every node in the linked list 
 * returns NULL if successful, otherwise head node is returned
 */
struct_of_ints* delete_list (struct_of_ints* head_node ) {

	struct_of_ints* temp;

	/*loop through the list to free each node*/
	while (head_node){
		temp = head_node;
		head_node = head_node->next;
		free(temp);
	}

	return head_node ;
}
