#include <stdio.h>
#include "linked_list.h"
#include "hash_table.h"


int print_menu() {

	int choice = 0 ;

	printf ("\nHash Table Utility Menu\n") ;
	printf ("\ttype: (0) to exit\n") ;
	printf ("\ttype: (1) to create table\n" ) ;
	printf ("\ttype: (2) to double table size\n" ) ;
	printf ("\ttype: (3) to add integer to table\n" ) ;
	printf ("\ttype: (4) to delete integer from the table\n" ) ;
	printf ("\ttype: (5) to look up bucket # of integer\n" ) ;
	printf ("\ttype: (6) to print table\n" ) ;
	scanf  ("%d", &choice ) ;

	return choice ;

}


int main () {

	int choice = 0 ;
	int number = 0 ;
	/*use a val to check the error of each function*/
	int error_check = 0;
	hash_table* my_hash_tbl = NULL ;
	struct_of_ints* ptr = NULL;

	
	while (1) {
  	   choice = print_menu() ;

	   switch (choice) {
		case 1 :
			printf ("\tEnter number of buckets in the hash table: ") ;
			scanf  ("%d", &number ) ;
			/* check if a hash table is already created*/
			if (my_hash_tbl != NULL){
				printf("\tYou have already created a hash table\n");
				break;
			}
			my_hash_tbl = create_hash_table (my_hash_tbl, number);
			if (my_hash_tbl != NULL) printf("\tA new hash table has been created.");
			break ;

		case 2 :
			printf("\tDoubling your table size.\n");
			my_hash_tbl = double_table_size (my_hash_tbl);
			if (my_hash_tbl == NULL) printf("\tFail!");
			else if (my_hash_tbl != NULL) printf("\tYour table has double size now.\n");
			break;

		case 3 :
			printf ("\tEnter integer to add to table: ") ;
			scanf  ("%d", &number ) ;
			error_check = add_to_table (my_hash_tbl, number);
			if (error_check == 0) printf("\t%d is added into the table.", number);
			break ;

		case 4 :
			printf ("\tEnter integer to delete from the table: ") ;
			scanf  ("%d", &number ) ;
			error_check = delete_from_table (my_hash_tbl, number);
			if (error_check == 0) printf("%d is removed from your table.\n", number);
			else if (error_check == -1) printf("Make sure your table is not empty!\n");
			else if (error_check == -2) printf("Can not delete %d since it is not in your table.\n", number);
			break;

		case 5 :
			printf ("\tEnter integer to find in the table: ") ;
			scanf  ("%d", &number ) ;
			ptr = find(my_hash_tbl, number);
			if (ptr == NULL) printf("Can not find such integer in your hash table.\n");
			else if (ptr != NULL){
				printf("Found it! %d is in bucket[%d]", number, map(my_hash_tbl, number));
			}
			break;

		case 6 :
			print_table (my_hash_tbl) ; 

			break ;

		default :
			printf ("\nExiting Program\n") ;
			/* remember to delete list before exiting */
			delete_table (my_hash_tbl);
			return 0 ;
			break ;
	   }	
	}


	return 0 ;

}
