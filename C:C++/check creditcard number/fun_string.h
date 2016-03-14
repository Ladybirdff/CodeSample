#include <string.h>

/*
 * Definition of the fun_string struct.
 * Do not change this!
 */
typedef struct Fun_String {
  char* s;
} fun_string;


/*
 * Creates a fun_string on the heap and returns its pointer.
 * Sets its field to a copy of the string pointed to by "s".
 * 
 * Do not change this function!
 */
fun_string* fs_create(char* s) {
  if (s == NULL) return NULL;

  fun_string* new = malloc(sizeof(fun_string));
  if (new == NULL) return NULL;

  new->s = malloc(strlen(s) + 1);
  if (new->s == NULL) return NULL;

  strcpy(new->s, s);

  return new;
}

/*
 * Cleans up the memory used by the fun_string.
 *
 * Do not change this function!
 */
void fs_free(fun_string* f) {
  free(f->s);
  free(f);
}

  
/*
 * This function appends the string pointed to by "s" to the end of the "s" field
 * in the fun_string. 
 * If either input is null or if the fun_string's "s" field is null, 
 * or if an error occurs in allocating memory, 
 * the function should return -1; otherwise it should return 0.
 */
int fs_append(fun_string* f, const char* s) {
  int length1;
  int length2;
  int i;
  char* new_s;

  // error check
  if ((f == NULL) || (f->s == NULL) || (s == NULL)) return -1;

  length1 = strlen(f->s);
  length2 = strlen(s);
  // allocate memory in the heap to hold f->s appending s
  new_s = malloc(length1+length2+1);
  if (new_s == NULL) return -1;

  // copy f->s and append s at the end into new_s
  for (i=0;i<length1;i++){
    new_s[i] = f->s[i];
  }
  for (i=0;i<length2;i++){
    new_s[length1+i] = s[i];
  }
  // terminated with null
  new_s[length1+length2] = '\0';
  // free the original f->s and replace it with new_s
  free(f->s);
  f->s = new_s;
  return 0;
}


/*
 * This function takes the substring in src's "s" field using the start and end indices (0-based 
 * and inclusive) and sets the dest's "s" field to that string. 
 * If the dest's "s" field is already populated, the memory it is using
 * should be deallocated.
 * If either dest or src is null, or their "s" fields are null, or start/end refer to invalid
 * indexes in the src's "s" field, or if an error occurs in allocating memory,
 * the function should return -1; otherwise it should return 0.
 */
int fs_substring(fun_string* dest, const fun_string* src, int start, int end) {
  int length;
  int i;
  int j;
  char* substr;

  // error check
  if ((dest == NULL) || (dest->s == NULL) || (src == NULL) || (src->s == NULL)) return -1;
  length = strlen(src->s);
  if ((start < 0) || (start > length-1) || (end < 0) || (end > length-1) || (start > end)) return -1;

  // if dest is not the same as src, free dest->s directly if necessary
  if (dest != src){
    if (dest->s != NULL) free(dest->s);
    // allocate new memory for dest->s to hold the substring of src->s
    dest->s = malloc(end-start+2);
    if (dest->s == NULL) return -1;

    j = 0;
    for (i=start;i<=end;i++){
      dest->s[j] = src->s[i];
      j = j + 1;
    }
    // terminated with null
    dest->s[j] = '\0';
    return 0;
  }

  // if dest is the same as src, allocate memory for char* substr to hold the substring of src->s
  // and then free the original string, replacing it by the substring 
  else {
    substr = malloc(end-start+2);
    if (substr == NULL) return -1;

    j = 0;
    for (i=start;i<=end;i++){
      substr[j] = src->s[i];
      j = j + 1;
    }
    // terminated with null
    substr[j] = '\0';
    free(dest->s);
    dest->s = substr;
    return 0;
  }

}


/*
 * This function populates the dest's "s" field with alternating letters from src1 and src2's "s" fields
 * by "weaving" them together one at a time. 
 * For instance, if src1->s = "abc" and src2->s = "123", dest->s should become "a1b2c3".
 * If the lengths of src1 and src2's "s" fields are not the same, any remaining characters from the longer
 * string should simply be appended to the end of dest->s.
 * If the dest's "s" field is already populated, the memory it is using
 * should be deallocated.
 * If either dest or src1 or src2 is null, or src1 or src2's  "s" fields are null, 
 * or if an error occurs in allocating memory,
 * the function should return -1; otherwise it should return 0.
 */
int fs_weave(fun_string* dest, const fun_string* src1, const fun_string* src2) {
  int length1;
  int length2;
  int i;
  int j = 0;
  char* newstr;

  // error check
  if ((dest == NULL) || (src1 == NULL) || (src2 == NULL) || (src1->s == NULL) || (src2->s == NULL)) return -1;

  length1 = strlen(src1->s);
  length2 = strlen(src2->s);

  // allocate memory to hold the weaving string
  newstr = malloc(length1+length2+1);
  if (newstr == NULL) return -1;

  // first weaving the string, whose length is determined by the shortest length of src1->s and src2->s
  // appending the remaining chars at the end 
  if (length1 >= length2){
    for (i=0;i<length2;i++){
      newstr[j] = src1->s[i];
      j++;
      newstr[j] = src2->s[i];
      j++;
    }
    for (;i<length1;i++){
      newstr[j] = src1->s[i];
      j++;
    }
    // terminated with null
    newstr[j] = '\0';
  }
  else {
    for (i=0;i<length1;i++){
      newstr[j] = src1->s[i];
      j++;
      newstr[j] = src2->s[i];
      j++;
    }
    for (;i<length2;i++){
      newstr[j] = src2->s[i];
      j++;
    }
    // terminated with null
    newstr[j] = '\0';
  }

  // free the original dest->s if necessary
  if (dest->s != NULL) free(dest->s);
  dest->s = newstr;
  return 0;
}


/*
 * This function populates the dest's "s" field with the characters in the src's "s" field but in the
 * reverse order.
 * If the dest's "s" field is already populated, the memory it is using
 * should be deallocated.
 * If either dest or src is null, or src's "s" field is null, 
 * or if an error occurs in allocating memory,
 * the function should return -1; otherwise it should return 0.
 */
int fs_reverse(fun_string* dest, const fun_string* src) {
  int length;
  int i;
  char* newstr;

  // error check
  if ((dest == NULL) || (src == NULL) || (src->s == NULL)) return -1;

  length = strlen(src->s);

  // allocate memory to hold the reversed string
  newstr = malloc(length+1);
  if (newstr == NULL) return -1;

  // reverse the string 
  for (i=0;i<length;i++){
    newstr[i] = src->s[length-1-i];
  }
  // terminated with null
  newstr[length] = '\0';

  // free original dest->s if necessary
  if (dest->s != NULL) free(dest->s);
  dest->s = newstr;
  return 0;
}

/*
 * This function counts the number of distinct characters that the "s" fields of s1 and s2 have in common.
 * Each character should only be counted once, even if it appears multiple times in the "s" fields, e.g.
 * if s1->s = "hello" and s2->s = "world", the function should return 2 (for 'l' and 'o'). 
 * If either s1 or s2 is null, or their "s" field is null, 
 * or if an error occurs in allocating memory,
 * the function should return -1; otherwise it should return 0.
 */
int fs_chars_in_common(const fun_string* s1, const fun_string* s2) {
  int length1;
  int length2;
  int i;
  int j;
  int counter = 0;
  int uni_num = 0;
  char* unique;

  // error check
  if ((s1 == NULL) || (s2 == NULL) || (s1->s == NULL) || (s2->s == NULL)) return -1;

  length1 = strlen(s1->s);
  length2 = strlen(s2->s);

  // allocate memory to hold distinct chars of s1
  unique = malloc(length1+1);
  for (i=0;i<length1;i++){
    unique[i] = '\0';
  }

  // put distinct chars of s1 into "unique" string 
  for (i=0;i<length1;i++){
    for (j=0;j<length1;j++){
      if (unique[j] == '\0'){
        unique[j] = s1->s[i];
        // pointing to the next available position to hold a char
        uni_num++;
        break;
      } 
      if (s1->s[i] == unique[j]) break;
    }
  }

  // calulate the distince chars in commone
  for (i=0;i<uni_num;i++){
    for (j=0;j<length2;j++){
      if (unique[i] == s2->s[j]){
        counter++;
        break;
      }
    }
  }

  // delocate memory
  free(unique);
  return counter;

}
