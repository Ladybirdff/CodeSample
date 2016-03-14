#include "state.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <math.h>

/*
  Given the name of the file containing the states, read the file line by line
  and return an array of state structs. Make sure these structs are on the heap,
  not on the stack!
  You can assume that there are no more than 51 states; if the input file contains 
  fewer than 51 lines, just set the name field of the remaining structs to NULL.
  This function should return NULL if any error occurs or if the input is NULL.
*/
state* read_states_file(char* filename) {

  FILE* file;
  state* states;
  int i;
  char buff[255];
  char* token;
  double lat;
  double lon;

  if (filename == NULL) return NULL;

  file = fopen(filename, "r");
  if (file == NULL) return NULL;

  states = malloc(sizeof(state)*51);
  if (states == NULL) return NULL;

  // read states.txt line by line and read information
  for (i = 0; i < 51; i++){
    if (!feof(file)){
    fgets(buff, 255, file);
    token = strtok(buff, ",");
    states[i].name = malloc(strlen(token) + 1);
    if (states[i].name == NULL) return NULL;
    strcpy(states[i].name, token);

    token = strtok(NULL, ",");
    lat = atof(token);
    states[i].latitude = lat;

    token = strtok(NULL, "\n");
    lon = atof(token);
    states[i].longitude = lon;
    }
    // if there are less than 51 states, creating null name
    else states[i].name = NULL;
  }

  fclose(file);

  return states;

}

/*
  a helper function to calculate the distance betweent a given pointe (latitude, longitude) and a state
*/
double cal_distance(state target, double lat, double lon){

  double x;
  double y;
  double a;
  double b;
  double sum;

  x = target.latitude - lat;
  y = target.longitude - lon;
  a = pow(x, 2);
  b = pow(y, 2);
  sum = a + b;

  return sqrt(sum);
}

/*
 Given the array of state structs and the latitude and longitude of a tweet, 
 return the name of the state whose latitude and longitude is closest 
 (using Euclidean distance).
 You can assume that the array has 51 elements but some names may be NULL.
 This function should return NULL if any error occurs or if the input is NULL.
*/
char* determine_state(state states[], double latitude, double longitude) {

  double min;
  double distance;
  char* state;
  int i;

  if (states == NULL) return NULL;

  // set the first minimum value
  for (i = 0; i < 51; i++){
    if (states[i].name != NULL){
      distance = cal_distance(states[i], latitude, longitude);
      min = distance;
      state = states[i].name;
      break;
    }
  }

  // find the shortest disatnce
  for (; i < 51; i++){
    if (states[i].name != NULL){
      distance = cal_distance(states[i], latitude, longitude);
      if (distance < min){
        min = distance;
        state = states[i].name;
      }
    }
  }
  return state;
}






