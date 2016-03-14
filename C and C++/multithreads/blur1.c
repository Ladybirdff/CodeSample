/**************************************************************

	The program reads an BMP image file and creates a new
	image that is the blur of the input file.

**************************************************************/

#include "qdbmp.h"
#include <stdio.h>
#include <stdlib.h>

/* Creates a blur image of the input bitmap file */
int main( int argc, char* argv[] )
{
	UCHAR	r, g, b;
	UINT	width, height;
	UINT	x, y;
	BMP*	bmp;
	BMP*	bmp_new;
	int 	size;
	int 	count;
	int     i, k, start_i, stop_i, start_k, stop_k;
	int 	r_sum, g_sum, b_sum;
	UCHAR	r_new, g_new, b_new;

	/* Check arguments */
	if ( argc != 4 )
	{
		fprintf( stderr, "Usage: %s <input file> <output file> <blur box size>\n", argv[ 0 ] );
		return 0;
	}

	size = atoi(argv[3]);
	if (size <= 0){
		printf("Please enter a positive integer as the blur box size argument.\n");
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

	/* Iterate through all the image's pixels */
	for ( x = 0 ; x < width ; ++x )
	{
		for ( y = 0 ; y < height ; ++y )
		{
			r_sum = 0;
			g_sum = 0;
			b_sum = 0;
			count = 0;
			/* Get pixel's RGB values */
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

			/* Invert RGB values */
			BMP_SetPixelRGB( bmp_new, x, y, r_new, g_new, b_new );
		}
	}

	/* Save result */
	BMP_WriteFile( bmp_new, argv[ 2 ] );
	BMP_CHECK_ERROR( stdout, -2 );


	/* Free all memory allocated for the image */
	BMP_Free( bmp );
	BMP_Free( bmp_new );

	return 0;
}

