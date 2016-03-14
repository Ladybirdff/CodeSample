package library;

public class Book {
	
	private String title;
	private String author;
	public int dueDate;

	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.dueDate = -1;
	}

	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public int getDuedate() {
		return dueDate;
	}
	
	public void checkOut(int date){
		this.dueDate = 7 + date;
	}
	
	public void checkIn(){
		this.dueDate = -1;
	}
	
	@Override
	public boolean equals(Object obj) {
		Book book = (Book) obj; 
		if (this.title.equals(book.getTitle()) && this.author.equals(book.getAuthor())){
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return title + ", by " + author;
	}
	
}
