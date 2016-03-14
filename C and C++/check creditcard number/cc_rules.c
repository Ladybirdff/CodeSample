#include "cc_rules.h"
#include <string.h>


/*
  Check that the string contains exactly 12 characters and that all of them
  are digits between 0 and 9.
*/
int is_valid (char* input) {
  int i;

  if (input == NULL) return -1;

  // check whether the string length is 12 and each char is 0 to 9
  if (strlen(input) == 12){
    for (i=0;i<12;i++){
      if ((input[i]<0x30) || (input[i]>0x39)){
        return 0;
      }
    }
    return 1;
  }
  else {
    return 0;
  }
}

/*
  Rule 1: The first digit must be a 4
*/
int check_rule_1 (creditcard* cc) {
  if (cc == NULL) return -1;
  if (cc->number[0] == 4){
    return 1;
  }
  else {return 0;}
}

/*
  Rule 2: If the third digit is even, the fourth digit must be odd
  If the third digit is odd, the fourth digit must be even
*/
int check_rule_2 (creditcard* cc) {
  if (cc == NULL) return -1;
  // If the third digit is even, the fourth digit must be odd
  if (cc->number[2] % 2 == 0){
    if (cc->number[3] % 2 == 1){
      return 1;
    }
    else {return 0;}
  }
  // If the third digit is odd, the fourth digit must be even
  else {
    if (cc->number[3] % 2 == 0){
      return 1;
    }
    else {return 0;}
  }
}
  

/*
  Rule 3: The fourth digit must be one greater than the fifth digit  
*/
int check_rule_3 (creditcard* cc) {
  if (cc == NULL) return -1;
  if (cc->number[3] > cc->number[4]){
    return 1;
  }
  else {return 0;}
}


/* 
   Rule 4: The second digit must either be a zero or equal to the sum of the
   ninth and tenth digits
 */  
int check_rule_4 (creditcard* cc) {
  if (cc == NULL) return -1;
  if ((cc->number[1] == 0) || (cc->number[1] == cc->number[8] + cc->number[9])){
    return 1;
  }
  else {return 0;}
}



/*
  Rule 5: The product of the first, fifth, and ninth digits must be 24  
*/
int check_rule_5 (creditcard* cc) {
  if (cc == NULL) return -1;
  if (cc->number[0] * cc->number[4] * cc->number[8] == 24){
    return 1;
  }
  else {return 0;}
}


/*
  Rule 6: The sum of all digits must be evenly divisible by 4   
*/
int check_rule_6 (creditcard* cc) {
  int i;
  int sum = 0;
  if (cc == NULL) return -1;
  // calulate the sum of all digits
  for (i=0;i<12;i++){
    sum = sum + cc->number[i];
  }
  if (sum % 4 == 0){
    return 1;
  }
  else {return 0;}
}


/* 
   Rule 7: The sum of the first four digits must be one less than the sum 
   of the last four digits
 */  
int check_rule_7 (creditcard* cc) {
  int i;
  int sum1 = 0;
  int sum2 = 0;
  if (cc == NULL) return -1;
  // sum of first 4 digits
  for (i=0;i<4;i++){
    sum1 = sum1 + cc->number[i];
  }
  // sum of last 4 digits
  for (i=8;i<12;i++){
    sum2 = sum2 + cc->number[i];
  }
  if (sum1 < sum2){return 1;}
  else {return 0;}
}


/* 
   Rule 8: If you treat the first two digits as a two-digit number, and 
   the seventh and eighth digits as a two digit number, their sum must be 100
 */  
int check_rule_8 (creditcard* cc) {
  int num1;
  int num2;
  if (cc == NULL) return -1;
  // first 2 digits as a number
  num1 = (cc->number[0])*10 + cc->number[1];
  // seventh and eighth digits as a number
  num2 = (cc->number[6])*10 + cc->number[7];
  if (num1 + num2 == 100){return 1;}
  else {return 0;}
}

/*
  A helper function to determine the number of factors of an integer
*/
int num_factor (int x) {
  int y = 1;
  int counter = 0;
  while (x >= y){
    if (x % y == 0){
      counter = counter + 1;
    }
    y = y + 1;
  }
  return counter;
}


/* 
   Rule 9: If you treat the last two digits as a two-digit number, it must
   not be prime.
 */
int check_rule_9 (creditcard* cc) {
  int num;
  if (cc == NULL) return -1;
  // last 2 digits number
  num = (cc->number[10])*10 +cc->number[11];
  // 0 and 1 are not prime as well
  if ((num == 0) || (num == 1) || (num_factor(num) > 2)) {return 1;}
  else {return 0;}
}

/*
  All the rules above must be satisfied to be a valid card number
*/
int check_credit_card (creditcard* cc) {
  return check_rule_1(cc) &&
    check_rule_2(cc) &&
    check_rule_3(cc) &&
    check_rule_4(cc) &&
    check_rule_5(cc) &&
    check_rule_6(cc) &&
    check_rule_7(cc) &&
    check_rule_8(cc) &&
    check_rule_9(cc);
}
  

