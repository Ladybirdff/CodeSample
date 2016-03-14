#ifndef LINKED_LIST_H
#define LINKED_LIST_H


/* linked list node definition */

typedef struct struct_of_ints_struct {
	int value ;
	struct struct_of_ints_struct *prev ;  /* pointer to previous node in list */
	struct struct_of_ints_struct *next ;  /* pointer to next node in list */
} struct_of_ints ;


/* linked list helper function declarations */
struct_of_ints* add_to_top    (struct_of_ints* head_node, int value ) ;
struct_of_ints* add_to_end    (struct_of_ints* head_node, int value ) ;
struct_of_ints*	delete_node   (struct_of_ints* head_node, int value ) ;
struct_of_ints* search_list   (struct_of_ints* head_node, int value ) ;
struct_of_ints* sort_list     (struct_of_ints* head_node ) ;
struct_of_ints* sort_list_rev (struct_of_ints* head_node ) ;
void		print_list    (struct_of_ints* head_node ) ;
struct_of_ints* delete_list   (struct_of_ints* head_node ) ;


#endif
