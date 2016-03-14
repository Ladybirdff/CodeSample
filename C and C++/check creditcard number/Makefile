CC = clang
ARGS = -Wall

all: creditcard fun_string

creditcard: cc_rules
	$(CC) -o ccvalidate $(ARGS) cc_validate.c cc_rules.o

cc_rules:
	$(CC) -c $(ARGS) cc_rules.c

fun_string:
	$(CC) -o fun_string $(ARGS) fun_string.c

clean: 
	rm -rf ccvalidate fun_string *.o

