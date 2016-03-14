#include <stdio.h>
#include <stdlib.h>
#include "linked_list2.h"
#include "hash_table2.h"

/* definition of hash table helper functions */


/*
 * this function creates a hash table, allocating memory on the heap for it
 * -returns pointer to the hash_table - or NULL if error
 */
hash_table* create_hash_table (hash_table* table, int num_of_buckets)
{
	int i ;
	hash_table *new_table;

	if (num_of_buckets<1) return NULL; /* invalid size for table */

	/* check if "table" isn't NULL, don't want to destroy an existing table */
	if (table != NULL) return table;

	/* allocate memory for 1 hashtable structure */
	new_table = malloc(sizeof(hash_table));
	if (new_table == NULL) return NULL;

	/* allocate memory for each bucket*/
	new_table->table = malloc(sizeof(struct_of_ints*) * num_of_buckets);
	if (new_table->table == NULL) return NULL;
	/* initialize the elements of the table to NULL */
	for (i=0;i<num_of_buckets;i++){
		new_table->table[i] = NULL;
	}
	/* set the table's size */
	new_table->num_of_buckets = num_of_buckets;

	return new_table;
}



/*
 * determines which "bucket" the value maps to
 * -returns bucket #
 */
unsigned int map (hash_table* table, char* chars)
{
	unsigned int bucket ;
	char letter = chars[0];

	/* apply hashing function to value that comes in, returning matching bucket # */
	/* change to uppercase*/
	if (letter >= 0x61 && letter <= 0x7A){
		letter = letter - 0x20;
	}
	/* 0 for A, 1 for B.......*/
	bucket = letter - 65;

	return bucket ;

}


/*
 * adds a value to the proper bucket in the hash table
 * -returns 0 upon success
 */
int add_to_table (hash_table* table, char* chars)
{
	unsigned int index;
	int length;

	if (table == NULL) return -1;
	/* determine proper bucket to put value into */
	index = map(table, chars);
	length = list_length(table->table[index]);
	/* add value to proper bucket */
	/* make sure each row can only hold four elements*/
	if (length < 4){
	table->table[index] = add_to_end (table->table[index], chars);
	return 0 ;
	}
	
	else if (length >= 4){
		return -2;
	}
	 
	return 0;
}


/*
 * deletes first instance of value from the hash table
 * -returns 0 upon success
 */
int delete_from_table (hash_table* table, char* chars)
{
	unsigned int index;

	if (table == NULL) return -1;

	/* determine proper bucket value is in */
	index = map(table, chars);

	/* delete data from bucket's linked list */
	if (find(table, chars) != NULL){
		table->table[index] = delete_node (table->table[index], chars);
		return 0 ;
	}

	else {
		return -2;
	}

}


/*
 * searches hash table for first instance of "value"
 * -returns actual struct_of_ints node when found, NULL if not found
 */
struct_of_ints* find (hash_table* table, char* chars) {

	unsigned int index;
	struct_of_ints* target;

	if (table == NULL) return NULL;

	/* determine bucket value may be in */
	index = map(table, chars);

	/* search corresponding bucket's linked list (using search_list function) */
	target = search_list (table->table[index], chars);
	/* return matching node or NULL if not found */

	return target ;
}


/*
 * frees all memory originally allocated to the hash table
 */
void delete_table (hash_table* table)
{
	int size;
	int i;

	/* free memory reserved for hash table and linked lists */
	if (table == NULL) return;

	size = table->num_of_buckets;

	for (i=0;i<size;i++){
		table->table[i] = delete_list (table->table[i]);
	}

	free(table->table);
	free(table);

}


/*
 * prints out hash table as if it were 2D array
 */
void print_table (hash_table* table) {

	/* print table as if it were a 2D array:
	
	   bucket[0]={ a, b, c } 
	         ...
	   bucket[n]={ x, y, z } 
	*/
	int size;
	int i;
	struct_of_ints* temp;

	if (table == NULL) return;

	size = table->num_of_buckets;

	for (i=0;i<size;i++){
		printf("\trow[%c]={ ", i+65);
		temp = table->table[i];
		if (temp != NULL){
			printf("%s", temp->string);
			temp = temp->next;
		}

		while(temp){
			printf(", ");
			printf("%s", temp->string);
			temp = temp->next;
		}
		printf(" }\n");
	}
}
