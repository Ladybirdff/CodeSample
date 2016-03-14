CC = clang
ARGS = -Wall

all: main

main: state tweet
	$(CC) -o main $(ARGS) main.c state.o tweet.o -lm

state:
	$(CC) -c $(ARGS) state.c

tweet:
	$(CC) -c $(ARGS) tweet.c

clean: 
	rm -rf main *.o

