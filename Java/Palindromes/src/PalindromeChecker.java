import java.util.ArrayList;

/**
 * use the MyStack class to check whether the input is a palindrome or not.
 * need to account correctly for whitespace, upper- and lower- case letters, and all punctuation
 * @author weixi ma
 *
 */
public class PalindromeChecker {

	private String input;
	private MyStack<Character> stack;

	/**
	 * the constructor for the palindrome checker class
	 * if parse a null string, consider it as an empty string
	 * @param input the string is going to be checked
	 */
	public PalindromeChecker(String input){
		if (input == null){
			this.input = "";
		}else {
			this.input = input;
		}
		this.stack = new MyStack<Character>();
	}

	/**
	 * a private method to "clean" the input string
	 * and then push each character of it into the stack
	 */
	private void cleanAndPush(){

		ArrayList<Character> list = new ArrayList<Character>();
		String newString = new String();

		// check whether it is an empty string
		if (this.input.length() > 0){
			// add each letter and number of the string into the array list
			for (int i = 0; i< this.input.length(); i++){
				if (Character.isDigit(input.charAt(i)) || Character.isLetter(input.charAt(i))){
					list.add(input.charAt(i));
				}
			}
			// make each letter to be lower case
			for (int j = 0; j < list.size(); j++){
				if (Character.isUpperCase(list.get(j))){
					char c = list.get(j);
					list.set(j, Character.toLowerCase(c));
				}
				stack.push(list.get(j));
				newString += list.get(j);
			}
			// replace input string with a new "clean" string
			// without any punctuation and all letters are lower case
			this.input = newString;
		}
	}

	/**
	 * check for whether the input string is palindrome
	 * @return true if yes, otherwise false
	 */
	public boolean check(){
		String reverse = new String();
		// clean the input string and push characters into stack
		this.cleanAndPush();
		// consider empty string is palindrome
		if (stack.isEmpty()) return true;
		
		// pop all the characters, and get a reverse string
		else{
			int stop = stack.size();
			for (int i = 0; i < stop; i++){
				reverse += stack.pop();
			}
			// compare the two string
			if (reverse.equals(this.input)) return true;
			else return false;
		}
	}

}
