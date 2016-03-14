package library;

public class Calendar {
	private int date;
	public Calendar() {
		this.date = 0;
	}
	
	public int getDate(){
		return this.date;
	}
	
	public void advance(){
		this.date++;	
	}
}
