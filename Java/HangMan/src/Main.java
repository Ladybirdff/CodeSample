import java.util.ArrayList;

/**
 * the main class to run the hang man game
 * @author Weixi Ma
 *
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EvilHangMan game = new EvilHangMan();
		ArrayList<String> list = game.readFile("dictionary.txt");
		game.initalizeDictionary(list);
//		System.out.println(game.getDictionary());
		int len = game.askForWordLen();
		int guesses = game.askForNumGuess();
		boolean look = game.askForLookRemainWords();
		game.gameInitialize(len, guesses, look);
//		System.out.println(game.getRevealedLetters() + ": " + game.getWordsList());
		
		while (game.gameOver() != true){
			String letter = game.guessLetter();
			game.changeWordFamilies(letter);
			game.setResult(letter);
//			System.out.println(game.getRevealedLetters() + ": " + game.getWordsList());
		}
	}

}
