#include <stdio.h>
#include <stdlib.h>
#include "fun_string.h"
#include <assert.h>

int main() {

  int ret_val = 0;


  /*
    tests for fs_append
  */
  fun_string *cat = fs_create("cat"); 

  ret_val = fs_append(cat, "dog"); // make sure append works
  assert(ret_val == 0);
  assert(strcmp(cat->s, "catdog") == 0);

  ret_val = fs_append(cat, "cat"); // and that you can do it more than once
  assert(ret_val == 0);
  assert(strcmp(cat->s, "catdogcat") == 0);

  ret_val = fs_append(cat, NULL); // check that it handles error inputs
  assert(ret_val == -1);
  assert(strcmp(cat->s, "catdogcat") == 0); // but doesn't change the fun_string

  ret_val = fs_append(NULL, "cat"); // check that it handles error inputs
  assert(ret_val == -1);
  assert(strcmp(cat->s, "catdogcat") == 0); // but doesn't change the fun_string

  // clean up
  fs_free(cat);

  /*
    tests for fs_substring
  */
  fun_string* fun = fs_create(""); //{ "" };
  fun_string* elephant = fs_create("elephant"); //{ "elephant" };
  
  ret_val = fs_substring(fun, elephant, 2, 5); // check that substring works
  assert(ret_val == 0);
  assert(strcmp(fun->s, "epha") == 0);
  assert(strcmp(elephant->s, "elephant") == 0); // and that it doesn't modify the second arg

  ret_val = fs_substring(fun, elephant, -1, 5); // error checking
  assert(ret_val == -1);
  ret_val = fs_substring(fun, elephant, 2, 12); // error checking
  assert(ret_val == -1);
  ret_val = fs_substring(fun, elephant, 5, 2); // error checking
  assert(ret_val == -1);  
  ret_val = fs_substring(NULL, elephant, 2, 5); // error checking
  assert(ret_val == -1);  
  ret_val = fs_substring(fun, NULL, 2, 5); // error checking
  assert(ret_val == -1);  

  ret_val = fs_substring(fun, elephant, 0, strlen(elephant->s)-1); // one last check for boundary conditions
  assert(ret_val == 0);
  assert(strcmp(fun->s, "elephant") == 0);

  ret_val = fs_substring(elephant, elephant, 3, 4); // should work if src and dest are the same
  assert(ret_val == 0);
  assert(strcmp(elephant->s, "ph") == 0);

  // clean up
  fs_free(fun);
  fs_free(elephant);


  /*
    tests for fs_weave
  */
  fun_string* abcde = fs_create("abcde"); //{ "abcde" };
  fun_string* xyz = fs_create("xyz"); //{ "xyz" };
  fun_string* result = fs_create(""); //{ "" };

  ret_val = fs_weave(result, abcde, xyz); // check that weave works
  assert(ret_val == 0);
  assert(strcmp(result->s, "axbyczde") == 0);
  assert(strcmp(abcde->s, "abcde") == 0); // and that it didn't modify args
  assert(strcmp(xyz->s, "xyz") == 0);
  
  ret_val = fs_weave(xyz, xyz, xyz); // should work if dest is same as one src
  assert(ret_val == 0);
  assert(strcmp(xyz->s, "xxyyzz") == 0);
  
  ret_val = fs_weave(NULL, xyz, xyz); // error handling
  assert(ret_val == -1);
  ret_val = fs_weave(xyz, NULL, xyz); // error handling
  assert(ret_val == -1);
  ret_val = fs_weave(xyz, xyz, NULL); // error handling
  assert(ret_val == -1);

  // clean up
  fs_free(result);
  fs_free(xyz);
  fs_free(abcde);

  /*
    tests for reverse
  */
  fun_string* target = fs_create("");// { "" };
  fun_string* reverse = fs_create("reverse"); //{ "reverse" };

  ret_val = fs_reverse(target, reverse); // check that reverse works
  assert(ret_val == 0);
  assert(strcmp(target->s, "esrever") == 0);
  assert(strcmp(reverse->s, "reverse") == 0); // and that it didn't modify args

  ret_val = fs_reverse(reverse, reverse); // should work if dest is same as src
  assert(ret_val == 0);
  assert(strcmp(reverse->s, "esrever") == 0);

  ret_val = fs_reverse(NULL, reverse); // error handling
  assert(ret_val == -1);
  ret_val = fs_reverse(reverse, NULL); // error handling
  assert(ret_val == -1);

  // clean up
  fs_free(target);
  fs_free(reverse);



  /*
    tests for chars_in_common
  */
  fun_string* hello = fs_create("hello"); //{ "hello" };
  fun_string* world = fs_create("world"); //{ "world" };
  fun_string* mouse = fs_create("mouse"); //{ "mouse" };
  fun_string* HELLO = fs_create("HELLO"); //{ "HELLO" };
  
  ret_val = fs_chars_in_common(world, mouse); // all letters are distinct
  assert(ret_val == 1);
  assert(strcmp(world->s, "world") == 0); // doesn't change args
  assert(strcmp(mouse->s, "mouse") == 0); // doesn't change args

  ret_val = fs_chars_in_common(hello, world); // some duplicate letters in first arg
  assert(ret_val == 2);

  ret_val = fs_chars_in_common(world, hello); // some duplicate letters in second arg
  assert(ret_val == 2);

  ret_val = fs_chars_in_common(hello, HELLO); // case in-sensitive
  assert(ret_val == 0);

  ret_val = fs_chars_in_common(NULL, mouse); // error handling
  assert(ret_val == -1);

  ret_val = fs_chars_in_common(mouse, NULL); // error handling
  assert(ret_val == -1);
  
  fs_free(hello);
  fs_free(world);
  fs_free(mouse);
  fs_free(HELLO);

  
  /*
    DONE!
  */
  printf("Made it here! All tests passed!\n");
  
  return 0;
}
