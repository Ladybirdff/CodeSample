import java.util.Scanner;


public class MainTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter a word or a phrase to check:");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		PalindromeChecker test = new PalindromeChecker(input);
		if (test.check()){
			System.out.println("It is palindrome.");
		} else {
			System.out.println("It is not palindrome.");
		}
	}

}
