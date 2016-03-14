#include "cc_rules.h"
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
	int i;
	int j;
	int format;
	char digit;
	creditcard* input;

	// No command line argument
	if (argc == 1){
		printf("No argument\n");
		return 0;
	}

	// Read the command line argument (credit card number)
	for (i=1;i<argc;i++){
		// Error check for whether is valid formatted
		format = is_valid(argv[i]);
		switch(format){
			case -1:
			printf("Null input\n");
			break;

			case 0:
			printf("%s is not valid formatted.\n", argv[i]);
			break;

			case 1:
			printf("%s is valid formatted. ", argv[i]);
			// create struct creditcard in the heap
			input = malloc(sizeof(creditcard));
			// push char as int into input->number 
			for (j=0;j<12;j++){
				digit = argv[i][j];
				input->number[j] = atoi(&digit);
			}
			// check for the 9 rules
			if (check_credit_card(input) == 1){
				printf("It is a legal creditcard number.\n");
			}
			else {
				printf("But it is not legal.\n");
			}
			// free the struct to avoid memory leak
			free(input);
			break;
		}
	}
  
    return 0;
}
