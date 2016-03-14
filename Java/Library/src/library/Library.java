package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Library {
	private boolean okToPrint;
	private ArrayList<Book> collection;
	private HashMap<String, Patron> patrons;
	private Calendar calendar;
	private boolean LibOpen;
	private Patron servedPatron;
	private ArrayList<Book> checkedInBook;
	private ArrayList<Book> searchedBook;
	private ArrayList<Book> checkedOutBook;
	private String input;
	private boolean operation;

	public Library() {
		this.LibOpen = false;
		this.okToPrint = true;
		this.collection = readBookCollection();
		this.patrons = new HashMap<String, Patron>();
		this.calendar = new Calendar();
		this.searchedBook = new ArrayList<Book>();
		this.checkedInBook = new ArrayList<Book>();
		this.checkedOutBook = new ArrayList<Book>();
		this.input = "";

	}

	private ArrayList<Book> readBookCollection() { 
		File file = new File("books.txt"); 
		ArrayList<Book> collection = new ArrayList<Book>(); 
		try {
			FileReader fileReader = new FileReader(file); 
			BufferedReader reader = new BufferedReader(fileReader);

			while (true) { 
				String line = reader.readLine(); 
				if (line == null) break; 
				line = line.trim(); 
				if (line.equals("")) continue; // ignore possible blank lines 
				String[] bookInfo = line.split(" :: "); 
				collection.add(new Book(bookInfo[0], bookInfo[1]));
			} 
		} 
		catch (IOException e) { 
			System.out.println(e.getMessage());
		} 
		return collection;
	}

	public Library(ArrayList<Book> collection){
		this.LibOpen = false;
		this.okToPrint = false;
		this.collection = collection;
		this.patrons = new HashMap<String, Patron>();
		this.calendar = new Calendar();
		this.searchedBook = new ArrayList<Book>();
		this.checkedInBook = new ArrayList<Book>();
		this.checkedOutBook = new ArrayList<Book>();

	}

	public boolean getOkToPrint(){
		return okToPrint;
	}

	public ArrayList<Book> getCollection(){
		return collection;
	}

	public HashMap<String, Patron> getPatrons(){
		return patrons;		
	}
	public Calendar getCalendar() {
		return calendar;
	}


	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args){
		Library library = new Library();
		library.operation =true;
		while (library.operation == true){
			library.start();
		}
	}

	public void start(){
		Scanner scanner;
		int number;
		this.println("####################");
		this.println("1. Open the library");
		this.println("2. Issue a library card");
		this.println("3. Serve a Patron");
		this.println("4. Check in books");
		this.println("5. Search a book");
		this.println("6. Check out books");
		this.println("7. Close the library");
		this.println("8. Exit this program");
		this.println("Enter a Command (Integer only.)");
		this.println("####################");
		scanner = new Scanner(System.in);
		number = scanner.nextInt();
		scanner.nextLine();
		switch (number){
		case 1:
			this.open();
			break;
		case 2:
			if (this.LibOpen == true){
				this.println("What is the name of the patron you want to issue a card to?");
				this.input = "";
				this.input = scanner.nextLine();
				this.issueCard(this.input);
			}
			else{
				this.println("Oh! You forget to open the library first!");
			}
			break;
		case 3:
			if (this.LibOpen == true){
				this.println("What is the name of the patron you want to serve?");
				this.input = scanner.nextLine();
				this.serve(input);
			}
			else{
				this.println("Oh! You forget to open the library first!");
			}
			break;
		case 4:
			if (this.LibOpen == true){
				this.serve(this.servedPatron.getName());
				int[] inputList;
				this.println("Which book do you want to check in, please input list of number of the book separated by comma, eg: 1,2");
				this.input = scanner.nextLine();
				String[] strList = this.input.split(",");
				inputList = new int[strList.length];
				for (int i=0; i<strList.length; i++ ){
					inputList[i] = Integer.parseInt(strList[i]);
				}
				this.checkIn(inputList);
			}
			else{
				this.println("Oh! You forget to open the library first!");
			}
			break;
		case 5:
			if (this.LibOpen == true){
				this.println("Please type the key word");
				this.input = scanner.nextLine();
				this.search(this.input);
			}
			else{
				this.println("Oh! You forget to open the library first!");
			}
			break;
		case 6:
			if (this.LibOpen == true){
				this.serve(this.servedPatron.getName());
				int[] inputList2;
				this.println("Which book do you want to check out, please input list of number separated by comma, eg: 1,2");
				this.input = scanner.nextLine();
				String[] strList2 = this.input.split(",");
				inputList2 = new int[strList2.length];
				for (int i=0; i<strList2.length; i++ ){
					inputList2[i] = Integer.parseInt(strList2[i]);
				}
				this.checkOut(inputList2);
			}
			else{
				this.println("Oh! You forget to open the library first!");
			}
			break;
		case 7:
			if (this.LibOpen = true){
				this.close();
				this.println("Library is closed");
			}
			else {
				this.println("The libaray is not open yet.");
			}
			break;
		case 8:
			this.quit();
			this.println("System is now closed");
			break;
		default: 
			this.println("Please enter an integer from 1 to 8 in order to choose the commmand!");
			break;
		}
	}

	public void print(String message){
		if (this.okToPrint == true){
			System.out.print(message);
		}
		else return;		
	}

	public void println(String message){
		if (this.okToPrint == true){
			System.out.println(message);
		}
		else return;

	}

	public ArrayList<OverdueNotice> open(){
		ArrayList<OverdueNotice> notices;
		if (this.LibOpen == false){
			this.calendar.advance();
			notices = this.createOverdueNotices();
			for (OverdueNotice i : notices){
				this.println(i.toString());			
			}
			this.LibOpen = true;
			this.println("Now the library is open.");
			this.println("Today is Day " + this.calendar.getDate());
			return notices;

		}
		else{
			this.println("The library was already open before!");
			return null;
		}
	}

	public ArrayList<OverdueNotice> createOverdueNotices(){
		ArrayList<OverdueNotice> notices;
		notices = new ArrayList<OverdueNotice>();
		for (String singlePatron : this.patrons.keySet()){
			for (Book bookOfPatron: this.patrons.get(singlePatron).getBooks()){
				if (bookOfPatron.getDuedate() + 1 == this.calendar.getDate()){
					OverdueNotice notice = new OverdueNotice(patrons.get(singlePatron), this.getCalendar().getDate());
					notices.add(notice);
					break;
				}	
			}
		}
		return notices;
	}

	public Patron issueCard(String nameOfPatron){
		Patron newPatron;
		newPatron = new Patron(nameOfPatron, this);
		if (!patrons.containsKey(nameOfPatron) && !nameOfPatron.isEmpty()){
			patrons.put(nameOfPatron, newPatron);
			this.println("Card is successfully issued to " + nameOfPatron);
		}
		else {
			this.println(nameOfPatron + " is our patron already, can't issue the card.");
		}
		return newPatron;
	}


	public Patron serve(String nameOfPatron){
		if (patrons.containsKey(nameOfPatron)){
			this.servedPatron = patrons.get(nameOfPatron);
			if (patrons.get(nameOfPatron).getBooks().isEmpty()){
				this.println(nameOfPatron + " didn't check out any books. But you can do so now.");
			}
			else{
				this.println("Currently books checked out by " + nameOfPatron + " :");
				int count = 1;
				for (Book i : this.servedPatron.getBooks()){
					this.println(count + ". " + i.toString());
					count++;
				}
			}
		}
		else {
			this.println("Sorry, " + nameOfPatron + " is not our patron.");
		}
		return servedPatron;
	}

	public ArrayList<Book> checkIn(int... bookNumbers){
		this.checkedInBook.clear();
		ArrayList<Book> patronsBook;
		patronsBook = servedPatron.getBooks();
		this.println("These books below are successfully checked in:");
		for (int i : bookNumbers){
			patronsBook.get(i-1).checkIn();
			this.println(patronsBook.get(i-1).toString());
		}
		for (int num : bookNumbers){
			this.collection.add(patronsBook.get(num-1));
			this.checkedInBook.add(patronsBook.get(num-1));
			servedPatron.giveBack(patronsBook.get(num-1));
		}
		return this.checkedInBook;

	}

	public ArrayList<Book> search (String part){
		searchedBook.clear();
		if (part.length() > 3){
			for (Book i : collection){
				if (i.getAuthor().toLowerCase().contains(part.toLowerCase()) || i.getTitle().toLowerCase().contains(part.toLowerCase())){
					if (!searchedBook.contains(i)){
						searchedBook.add(i);
					}
				}
			}
			int count = 1;
			for (Book i : searchedBook){
				this.println(count + ". " + i.toString());
				count++;
			}
			if (searchedBook.isEmpty()){
				this.println("There is no such book in the library.");
			}
		}
		else {
			this.println("Please input key word with minimum length of 4 characters.");
		}

		return searchedBook;

	}

	public ArrayList<Book> checkOut(int... bookNumbers){
		if (searchedBook != null){
			checkedOutBook.clear();
			this.println("These books are checked out to " + this.servedPatron.getName() + " :");
			for (int i : bookNumbers){
				collection.remove(searchedBook.get(i-1));
				servedPatron.take(searchedBook.get(i-1));
				searchedBook.get(i-1).checkOut(this.calendar.getDate());
				this.println(searchedBook.get(i-1).toString());
				checkedOutBook.add(searchedBook.get(i-1));		
			}
			return checkedOutBook;
		}
		else{
			this.println("Ops no book is checked out please enter numbers correctly!");
			return checkedOutBook;
		}
	}

	public void close(){
		this.LibOpen = false;
	}

	public void quit(){
		this.operation = false;
	}



}
