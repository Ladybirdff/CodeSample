package library;

import java.util.ArrayList;

public class Patron {
	
	private String name;
	private Library library;
	private ArrayList<Book> books; //checked out books by this patron

	public Patron(String name, Library library) {
		this.name = name;
		this.library = library;
		this.books = new ArrayList<Book>();
	}
	
	public String getName() {
		return name;
	}
	
	
	public void take(Book book){
		if (this.books.size() < 3){
			this.books.add(book);
		}
		else{
			System.out.println("You have already checked out 3 books.");
		}
			}
	
	public void giveBack(Book book){
		for (Book checkedbook : this.books){
			if (checkedbook.equals(book)){
				this.books.remove(checkedbook);
				return;
			}
		}
		System.out.println("You have not checked out this book.");
		return;
	}
	
	public ArrayList<Book> getBooks(){
		return this.books;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
