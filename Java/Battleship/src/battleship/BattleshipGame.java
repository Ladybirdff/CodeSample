package battleship;

import java.util.Scanner;

public class BattleshipGame {
	private Ocean ocean;

	public BattleshipGame() {
		this.ocean = new Ocean();
		this.ocean.placeAllShipsRandomly();
	}

	public static int[] StringConvert(String str){
		// translate String[] input to int[]
		int[] intList;
		String [] strList = str.split(",");
		intList = new int[strList.length];
		for (int i=0; i<strList.length; i++ ){
			intList[i] = Integer.parseInt(strList[i]);
		}
		return intList;

	}

	public static boolean errorChecking(String str){
		if (str.length() ==3 && str.charAt(1) == ',' && Character.isDigit(str.charAt(0)) && Character.isDigit(str.charAt(2))){
			return true;
		}
		else {
			return false;
		}

	}

	public static void main(String[] args) {

		boolean contChoice = true;
		

		while (contChoice){
			BattleshipGame newgame = new BattleshipGame();
			Scanner scanner = new Scanner(System.in);
			System.out.println("Welcome to Solo Battleship Game!");
			boolean gameOver = false;
			while (!gameOver){
				newgame.ocean.print();
				System.out.println("Please make a move, Please enter two integer\nfrom 0 to 9 separated by comma, eg '1,2' ");
				String strInput = scanner.next();
				while(!errorChecking(strInput)){
					System.out.println("Illegal input, please enter again, format, eg '1,2'");
					strInput = scanner.next();
				}
				int row = StringConvert(strInput)[0];
				int column = StringConvert(strInput)[1];
				newgame.ocean.shootAt(row, column);
				newgame.ocean.changeDisplay(row, column);
				gameOver = newgame.ocean.isGameOver();
			}
			newgame.ocean.print();
			System.out.println("Game over!");
			System.out.println("Total shots fired: " + newgame.ocean.getShotsFired());
			System.out.println("Total hit: "+ newgame.ocean.getHitCount());
			System.out.println("Would you like to play again? Enter 'n' to exit, otherwise to continue.");
			String choice = scanner.next();
			if (choice.equals("n")){
				contChoice = false;
			}
		}
		System.out.println("Thanks to play, Bye~");
	}

}
