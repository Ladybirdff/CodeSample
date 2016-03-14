#include <stdio.h>
#include "linked_list2.h"
#include "hash_table2.h"


int print_menu() {

	int choice = 0 ;

	printf ("\nHash Table Utility Menu for airline reservation system\n") ;
	printf ("\ttype: (0) to exit\n") ;
	printf ("\ttype: (1) to create an airline reservation system hash table\n" ) ;
	printf ("\ttype: (2) to add a reservation for a passenger\n" ) ;
	printf ("\ttype: (3) to remove a passenger's reservation from the table\n" ) ;
	printf ("\ttype: (4) to look up row # of a passenger\n" ) ;
	printf ("\ttype: (5) to print the whole reservation\n" ) ;
	scanf  ("%d", &choice ) ;

	return choice ;

}


int main () {

	int choice = 0 ;
	int number = 0 ;
	int error_check = 0;
	char string[30];
	hash_table* my_hash_tbl = NULL ;
	struct_of_ints* ptr = NULL;

	
	while (1) {
  	   choice = print_menu() ;

	   switch (choice) {
		case 1 :
			if (my_hash_tbl != NULL){
				printf("\tThe airline reservation hash table is already created!\n");
				break;
			}
			my_hash_tbl = create_hash_table (my_hash_tbl, 26);
			if (my_hash_tbl != NULL) printf("\tA new hash table for airline reservation has been created.");
			break ;

		case 2 :
			printf ("\tEnter a passenger to the plane: ") ;
			scanf  ("%s", string);
			error_check = add_to_table (my_hash_tbl, string);
			if (error_check == 0) printf("\t%s is added into the table.", string);
			else if (error_check == -2) printf("\t%s can not be added into the table since the row is full.", string);
			break ;

		case 3 :
			printf ("\tEnter a passenger to remove from the table: ") ;
			scanf  ("%s", string);
			error_check = delete_from_table (my_hash_tbl, string);
			if (error_check == 0) printf("\t%s is removed from your table.\n", string);
			else if (error_check == -1) printf("\tMake sure your table is not empty!\n");
			else if (error_check == -2) printf("\tCan not delete %s since it is not in your table.\n", string);
			break;

		case 4 :
			printf ("\tEnter a passenger's name to find in the table: ") ;
			scanf  ("%s", string);
			ptr = find(my_hash_tbl, string);
			if (ptr == NULL) printf("\tCan not find such the passenger in your hash table.\n");
			else if (ptr != NULL){
				printf("\tFound it! %s is in row[%c]", string, map(my_hash_tbl, string)+65);
			}
			break;

		case 5 :
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
