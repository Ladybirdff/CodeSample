#
# Makefile for CIT 593 HW #13
#

all: program1

linked_list.o: linked_list.c linked_list.h
	clang -c linked_list.c

hash_table.o: hash_table.c hash_table.h linked_list.h
	clang -c hash_table.c

# CIT 593: TO DO 
# add a rule to create a target called: program1
program1: program1.c linked_list.o hash_table.o
	clang -o program1 program1.c linked_list.o hash_table.o

clean:
	rm -rf *.o

clobber: clean
	rm -rf a.out program1
	
