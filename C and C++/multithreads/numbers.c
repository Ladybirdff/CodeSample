#include <unistd.h>
#include <string.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

pthread_mutex_t lock;
int counter = 0;
char quit = '\0';
float last_five[5] = {0, 0, 0, 0, 0};
float max = 0;
float min = 0;
float sum = 0;

void* print_results(void* par){
	int i;
	float avg;

	while(1){
		sleep(10);
		pthread_mutex_lock(&lock);
		if (quit == 'q') break;
		printf("\n");
		if (counter == 0){
			printf("User has not input any number yet.\n");
		}
		else {
			avg = sum / (float)counter;

			printf("The maximum value input so far: %f\n", max);
			printf("The minimum value input so far: %f\n", min);
			printf("The average of all values input so far: %f\n", avg);

			printf("Last five values that were input:\n");
			if (counter < 5){
				for (i = 0; i < counter; i++){
					printf("%f ", last_five[i]);
				}
			}
			else {
				for (i = 0; i < 5; i++){
					printf("%f ", last_five[i]);
				}
			}
			printf("\n");
			printf("\n");

		}
		pthread_mutex_unlock(&lock);
	}
	pthread_mutex_unlock(&lock);
	return NULL;
}

int main(){
	int i;
	int ret_val;
	char user_input[100];
	pthread_t thread;
	float value;
	void* ret_thread;
	int input_len;
	int valid;

	ret_val = pthread_create(&thread, NULL, &print_results, NULL);
	if (ret_val != 0){
		printf("Error when starting a new thread.\n");
		return -1;
	}

	while (1){
		valid = 1;
		printf("Enter a number (floating point), enter 'q' to quit: ");
		scanf("%s", user_input);
		if ((strlen(user_input) == 1) && (user_input[0] == 'q')){
			pthread_mutex_lock(&lock);
			quit = 'q';
			pthread_mutex_unlock(&lock);
			break;
		}
		value = atof(user_input);
		input_len = strlen(user_input);
		if (value == 0){
			for (i = 0; i < input_len; i++){
				if (user_input[i] != '0'){
					printf("Invalid input!\n");
					valid = 0;
					break;
				}
			}
		}

		if (valid == 1){
			pthread_mutex_lock(&lock);
			counter++;
			sum = sum + value;
			if (counter == 1){
				min = value;
				max = value;
			}
			if (value > max) max = value;
			if (value < min) min = value;
			last_five[4] = last_five[3];
			last_five[3] = last_five[2];
			last_five[2] = last_five[1];
			last_five[1] = last_five[0];
			last_five[0] = value;
			pthread_mutex_unlock(&lock);
		}
	}

	ret_val = pthread_join(thread, &ret_thread);
	if (ret_val != 0){
		printf("Error when joining a thread.\n");
		return -1;
	}

	return 0;
}


