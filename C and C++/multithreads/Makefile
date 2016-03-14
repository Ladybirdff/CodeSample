CC = clang
ARGS = -Wall

all: negative blur1 numbers blur2

negative: qdbmp
	$(CC) -o negative $(ARGS) negative.c qdbmp.o

blur1: qdbmp
	$(CC) -o blur1 $(ARGS) blur1.c qdbmp.o

blur2: qdbmp
	$(CC) -o blur2 $(ARGS) blur2.c qdbmp.o -lpthread

qdbmp:
	$(CC) -c $(ARGS) qdbmp.c

numbers:
	$(CC) -o numbers $(ARGS) numbers.c -lpthread

clean: 
	rm -rf negative numbers blur1 blur2 *.o

