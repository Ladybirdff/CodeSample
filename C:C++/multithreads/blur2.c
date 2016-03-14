/**************************************************************

	The program reads an BMP image file and creates a new
	image that is the blur of the input file.

**************************************************************/

#include "qdbmp.h"
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

BMP*	bmp;
BMP*	bmp_new;
UINT	width, height;
int     num_thread;
int 	size;

// thread entry, take an int* argument to determine with section of x direction to read
void* blur(void* num){
	UCHAR	r, g, b;
	UINT	x, y;
	int 	count;
	int     i, k, start_i, stop_i, start_k, stop_k;
	int 	r_sum, g_sum, b_sum;
	UCHAR	r_new, g_new, b_new;
	int     start;
	int     stop;
	int     unit = width / num_thread;
	int     unit_num = *(int*)num;

	// calculate the start and stop point in x direction for each thread to read
	start = unit_num * unit;
	stop = start + unit;
	if (unit_num + 1 == num_thread){
		stop = width;
	}

	for ( x = start ; x < stop ; ++x )
	{
		for ( y = 0 ; y < height ; ++y )
		{	
			// for each pixel, get the average RGB of other pixels around it
			// within the blur box
			r_sum = 0;
			g_sum = 0;
			b_sum = 0;
			count = 0;
			start_i = x - size;
			stop_i = x + size;
			start_k = y - size;
			stop_k = y + size;
			for (i = start_i; i <= stop_i; i++){
				for (k = start_k; k <= stop_k; k++){
					if ((i >= 0) && (i < width) && (k >= 0) && (k < height)){
						BMP_GetPixelRGB(bmp, i, k, &r, &g, &b);
						count++;
						r_sum = r_sum + r;
						g_sum = g_sum + g;
						b_sum = b_sum + b;
					}
				}
			}
			r_new = r_sum / count;
			g_new = g_sum / count;
			b_new = b_sum / count;

			// set a new pixel to the new bmp file
			BMP_SetPixelRGB( bmp_new, x, y, r_new, g_new, b_new );
		}
	}
	return NULL;
}

/* Creates a blur image of the input bitmap file using multi-threads*/
int main( int argc, char* argv[] )
{
	pthread_t* threads;
	int i;
	int ret_val;
	int* nums;

	/* Check arguments */
	if ( argc != 5 )
	{
		fprintf( stderr, "Usage: %s <input file> <output file> <blur box size> <number of thread>\n", argv[ 0 ] );
		return 0;
	}

	// set the blur box size
	size = atoi(argv[3]);
	if (size <= 0){
		printf("Please enter a positive integer as the blur box size argument.\n");
		return 0;
	}

	// set the number of threads
	num_thread = atoi(argv[4]);
	if (num_thread <= 0){
		printf("Please enter a positive integer as the number of threads.\n");
		return 0;
	}

	// allocate memory for the threads array and nums array in the heap
	threads = malloc(sizeof(pthread_t) * num_thread);
	if (threads == NULL){
		printf("Cannot allocate memory in the heap.\n");
		return 0;
	}
	nums = malloc(sizeof(int) * num_thread);
	if (nums == NULL){
		printf("Cannot allocate memory in the heap.\n");
		return 0;
	}

	/* Read an image file */
	bmp = BMP_ReadFile( argv[ 1 ] );
	BMP_CHECK_ERROR( stdout, -1 );
	bmp_new = BMP_ReadFile( argv[ 1 ] );
	BMP_CHECK_ERROR( stdout, -1 );

	/* Get image's dimensions */
	width = BMP_GetWidth( bmp );
	height = BMP_GetHeight( bmp );

	// create threads using a loop and parse argument to each thread
	for (i = 0; i < num_thread; i++){
		nums[i] = i;
		ret_val = pthread_create(&threads[i], NULL, &blur, (void*)&nums[i]);
		if (ret_val != 0){
			printf("Error when starting a new thread.\n");
			return 0;
		}
	}
	// join all the threads
	for (i = 0; i < num_thread; i++){
		ret_val = pthread_join(threads[i], NULL);
		if (ret_val != 0){
			printf("Error when joining a thread.\n");
			return 0;
		}
	}
	
	/* Save result */
	BMP_WriteFile( bmp_new, argv[ 2 ] );
	BMP_CHECK_ERROR( stdout, -2 );
	/* Free all memory allocated for the image */
	free(threads);
	free(nums);
	BMP_Free( bmp );
	BMP_Free( bmp_new );

	return 0;
}

