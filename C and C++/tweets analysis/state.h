typedef struct State {
  char* name;
  double latitude;
  double longitude;
} state;


state* read_states_file(char* filename);

char* determine_state(state states[], double latitude, double longitude);
