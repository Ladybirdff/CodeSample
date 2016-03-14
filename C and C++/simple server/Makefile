CC = clang
ARGS = -Wall

all: httpserver

httpserver:
	$(CC) -o httpserver $(ARGS) httpserver.c -lpthread

clean: 
	rm -rf httpserver *.o

