#include <stdio.h>
#include <stdlib.h>
#include "linked_list.h"
#include "hash_table.h"

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
 * this function allows the user to double the # of buckets at run time
 * -returns pointer to the hash_table - or NULL if error
 */
hash_table* double_table_size (hash_table* table)
{	
	int size;
	int i;
	hash_table* new_table = NULL;
	struct_of_ints* temp;

	/* allocate memory for a new hash table - double the size */

	/* students may use "realloc()" if they understand it properly! - not required */

	/* move data from old hash table to new hash table - if necessary */

	/* don't forget to free old hash table memory before returning */

	if (table == NULL) return NULL ;

	size = 2 * table->num_of_buckets;

	/* create a new hash table with double size and copy all the elements to it and delete the old one*/
	new_table = create_hash_table(new_table, size);
	if (new_table == NULL) return NULL;

	for (i=0;i<table->num_of_buckets;i++){
		temp = table->table[i];
		while (temp != NULL){
			add_to_table (new_table, temp->value);
			temp = temp->next;
		}
	}

	delete_table (table);

	return new_table;

}


/*
 * determines which "bucket" the value maps to
 * -returns bucket #
 */
unsigned int map (hash_table* table, int value)
{
	unsigned int bucket ;
	unsigned int size ;

	/* apply hashing function to value that comes in, returning matching bucket # */
	size = table->num_of_buckets;

	if (value >= 0){
		bucket = value % size;
	}
	/* make sure it return a positiv # when the value is negative*/
	else if (value < 0){
		bucket = (value * (-1)) % size;
	}

	return bucket ;

}


/*
 * adds a value to the proper bucket in the hash table
 * -returns 0 upon success
 */
int add_to_table (hash_table* table, int value)
{
	unsigned int index;

	if (table == NULL) return -1;
	/* determine proper bucket to put value into */
	index = map(table, value);

	/* add value to proper bucket */
	table->table[index] = add_to_end (table->table[index], value);

	return 0 ; 

}


/*
 * deletes first instance of value from the hash table
 * -returns 0 upon success
 */
int delete_from_table (hash_table* table, int value)
{
	unsigned int index;

	if (table == NULL) return -1;

	/* determine proper bucket value is in */
	index = map(table, value);

	/* delete data from bucket's linked list */
	if (find(table, value) != NULL){
		table->table[index] = delete_node (table->table[index], value);
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
struct_of_ints* find (hash_table* table, int value) {

	unsigned int index;
	struct_of_ints* target;

	if (table == NULL) return NULL;

	/* determine bucket value may be in */
	index = map(table, value);

	/* search corresponding bucket's linked list (using search_list function) */
	target = search_list (table->table[index], value);
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
		printf("\tbucket[%d]={ ", i);
		temp = table->table[i];
		if (temp != NULL){
			printf("%d", temp->value);
			temp = temp->next;
		}

		while(temp){
			printf(", ");
			printf("%d", temp->value);
			temp = temp->next;
		}
		printf(" }\n");
	}
}
