import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * a class to implement the evil hang man algorithm, given a certain dictionary text file
 * @author Weixi Ma
 *
 */
public class EvilHangMan {

	private Scanner myScanner;
	// total number of chances to guess
	private int totalGuesses;
	// indicate the chosen length of words
	private int fixedLen;
	// Map data structure to hold to whole dictionary, keys are the lengths of words
	private Map<Integer,ArrayList<String>> dictionary;
	// represent the current guess status, eg. "----"
	private String revealedLetters;
	// ArrayList data structure to hold to the chosen word family
	private ArrayList<String> wordsList;
	// user's guessed letters
	private ArrayList<String> lettersGuessed;
	// whether to look the number of remaining words
	private boolean lookRemainWords;

	/**
	 * the constructor for EvilHangMan class
	 */
	public EvilHangMan(){
		myScanner =  new Scanner(System.in);
		wordsList = new ArrayList<String>();
		lettersGuessed = new ArrayList<String>();
		dictionary = new HashMap<Integer, ArrayList<String>>();
	}

	/**
	 * read a text file containing words, and return an ArrayList
	 * @param fileName the file name of reading text file
	 * @return an ArrayList containing all the words
	 */
	public ArrayList<String> readFile(String fileName){
		File file = new File(fileName);
		ArrayList<String> list = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String word;
			// read the text file line by line
			while ((word = bufferedReader.readLine()) != null){
				list.add(word);
			}
			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * initialize the dictionary instance variable
	 * partitioning words by their lengths
	 * @param list an ArrayList holding all words
	 */
	public void initalizeDictionary(ArrayList<String> list){
		for (String word : list){
			// if already contains the length (key), update the corresponding ArrayList
			if (this.dictionary.containsKey(word.length())){
				ArrayList<String> words = this.dictionary.get(word.length());
				words.add(word);
				this.dictionary.put(word.length(), words);
				// else, put the new key value pair in the dictionary
			}else{
				ArrayList<String> newWords = new ArrayList<String>();
				newWords.add(word);
				this.dictionary.put(word.length(), newWords);
			}
		}
	}

	/**
	 * a private method to check if a string is an integer
	 * @param str the string to be checked
	 * @return true if it is an integer, otherwise false
	 */
	private boolean isInteger(String str){
		try{
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}

	/**
	 * ask for user to input the length of words to guess
	 * @return an integer representing the length
	 */
	public Integer askForWordLen(){
		System.out.println("Enter the length of the word you want to guess:");
		String inputLen = myScanner.next();
		// check for invalid input
		while(true){
			if (this.isInteger(inputLen)){
				Integer len = Integer.parseInt(inputLen);
				// check if the current dictionary containing words with such length
				if (this.dictionary.containsKey(len)){
					return len;
				}
			}
			System.out.println("Invalid input or the computer dosen't have a word in this length, try again:");
			inputLen = myScanner.next();
		}
	}

	/**
	 * ask for user to input the total number of guesses
	 * @return an integer representing the number of guesses
	 */
	public Integer askForNumGuess(){
		System.out.println("Enter the number of chances you can guess (not greater than 23):");
		String str = myScanner.next();
		// check for invalid input
		while(true){
			if (this.isInteger(str)){
				Integer num = Integer.parseInt(str);
				// check if it is less or equals 23
				if (num <= 23){
					return num;
				}
			}
			System.out.println("Invalid input, try again:");
			str = myScanner.next();
		}
	}

	/**
	 * ask user whether he want to look the number of remaining words
	 * @return true if yes, otherwise false
	 */
	public boolean askForLookRemainWords(){
		System.out.println("Do you want to see total number of words left? (y/n)");
		String input = myScanner.next();
		// check for invalid input
		while (!(input.equals("y")) && !(input.equals("n"))){
			System.out.println("Invalid input, try again.");
			input = myScanner.next();
		}
		if (input.equals("y")) return true;
		else return false;
	}

	/**
	 * set some instance variables of the class
	 * @param wordLen length of words to be guessed
	 * @param numGuess total number of chances to guess
	 * @param seeRemainWords whether see the remaining number of words
	 */
	public void gameInitialize(int wordLen, int numGuess, boolean seeRemainWords){
		this.fixedLen = wordLen;
		
		// according to the word length, initialize the guessing word status, like "----"
		char[] key = new char[this.fixedLen];
		for (int i = 0; i< this.fixedLen; i++){
			key[i] = '-';
		}
		this.revealedLetters = String.valueOf(key);

		this.wordsList = this.dictionary.get(this.fixedLen);

		this.totalGuesses = numGuess;

		this.lookRemainWords = seeRemainWords;
	}

	/**
	 * ask user to input a letter to guess
	 * @return a string containing that letter
	 */
	public String guessLetter(){
		System.out.println("Guess a letter: ");
		String letter = myScanner.next();
		while (this.checkGuessLetter(letter) != true){
			System.out.println("Invalid input, make another guess:");
			letter = myScanner.next();
		}
		return letter.toLowerCase();
	}
	
	/**
	 * a private method to check a string whose content is valid
	 * @param str a string to be checked
	 * @return true if it is valid, otherwise false
	 */
	private boolean checkGuessLetter(String str){
		// the length should be one and is not a number
		if (str.length() != 1) return false;
		else if (!Character.isLetter(str.charAt(0))) return false;
		else {
			// cannot guess a same letter twice
			if (!this.lettersGuessed.contains(str.toLowerCase())){
				return true;
			}else return false;
		}
	}

	/**
	 * change the word family according to the guessing letter
	 * @param str a string represent the guessing letter
	 */
	public void changeWordFamilies(String str){
		// update the guessed letters
		this.lettersGuessed.add(str);
		// the original partitioning "key"
		String chosenKey = this.revealedLetters;
		// the words to be partitioned into different families
		ArrayList<String> chosenFamily = this.wordsList;

		// generate new word families according to the letter, and store them in a Map
		Map<String, ArrayList<String>> newWordFamilies = new HashMap<String, ArrayList<String>>();
		for (String word : chosenFamily){
			// update the key for each word
			char[] familyKey = chosenKey.toCharArray();
			for (int i = 0; i < word.length(); i++){
				if (word.charAt(i) == str.charAt(0)){
					familyKey[i] = str.charAt(0);
				}
			}
			// update or put a new key, value pair into the map
			if (newWordFamilies.keySet().contains(String.valueOf(familyKey))){
				ArrayList<String> list = newWordFamilies.get(String.valueOf(familyKey));
				list.add(word);
				newWordFamilies.put(String.valueOf(familyKey), list);
			}else{
				ArrayList<String> newList = new ArrayList<String>();
				newList.add(word);
				newWordFamilies.put(String.valueOf(familyKey), newList);
			}
		}
		// choose the biggest size word family
		int maxSize = 0;
		for (String key : newWordFamilies.keySet()){
			if (newWordFamilies.get(key).size() >= maxSize){
				maxSize = newWordFamilies.get(key).size();
				this.revealedLetters = key;
				this.wordsList = newWordFamilies.get(key);
			}
		}
	}

	/**
	 * tell the user the result of his last guess
	 * @param str a string represent the last guessed letter
	 */
	public void setResult(String str){
		System.out.println(this.lettersGuessed);
		System.out.println("The word being guessed: " + this.revealedLetters);
		// correct guess or not, update the remaining number of guesses
		if (revealedLetters.contains(str)){
			System.out.println("You guessed a correct letter!");
		} else {
			System.out.println("Wrong!");
			this.totalGuesses--;
		}
		System.out.println("Remain number of guess: " + this.totalGuesses);
		// show number of remaining words if necessary
		if (this.lookRemainWords){
			int totalNum = this.wordsList.size();
			System.out.println("The total number of words remaining in the list is: " + totalNum);
		}
	}

	/**
	 * check whether is game over
	 * @return true if yes, otherwise false
	 */
	public boolean gameOver(){
		// run out of guesses, pick a random word in the current family
		// and claim it is the word chosen at first
		if (this.totalGuesses == 0){
			Random random = new Random();
			int index = random.nextInt(this.wordsList.size());
			System.out.println("Sorry, you ran out of guessing.");
			System.out.println("The word is " + this.wordsList.get(index));
			return true;
		}
		// user guessed all letters in the word
		else if(this.totalGuesses > 0 && this.wordsList.size() == 1 && this.revealedLetters.equals(this.wordsList.get(0))){
			System.out.println("You win!");
			System.out.println("The word is: " + this.revealedLetters);
			return true;
		}
		else return false;
	}

	/**
	 * get instance variable fixedLen, for debug purpose
	 * @return fixedLen
	 */
	public int getFixedLen() {
		return fixedLen;
	}

	/**
	 * get instance variable totalGuesses, for debug purpose
	 * @return totalGuesses
	 */
	public int getTotalGuesses() {
		return totalGuesses;
	}

	/**
	 * get instance variable lookRemainWords, for debug purpose
	 * @return lookRemainWords
	 */
	public boolean getLookRemainWords(){
		return lookRemainWords;
	}

	/**
	 * get instance variable dictionary, for debug purpose
	 * @return dictionary
	 */
	public Map<Integer, ArrayList<String>> getDictionary() {
		return dictionary;
	}
	
	/**
	 * get instance variable letterGuessed, for debug purpose
	 * @return letterGuessed
	 */
	public ArrayList<String> getLettersGuessed() {
		return lettersGuessed;
	}

	/**
	 * get instance variable revealedLetters, for debug purpose
	 * @return revealLetters
	 */
	public String getRevealedLetters() {
		return revealedLetters;
	}

	/**
	 * get instance variable wordList, for debug purpose
	 * @return wordList
	 */
	public ArrayList<String> getWordsList() {
		return wordsList;
	}

}
