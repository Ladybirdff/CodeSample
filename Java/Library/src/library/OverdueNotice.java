package library;



public class OverdueNotice {

	private Patron patron;
	private int todaysDate;


	public OverdueNotice(Patron patron, int todaysDate) {
		this.patron = patron;
		this.todaysDate = todaysDate;
	}

	@Override
	public String toString(){
		String s = "";
		for (Book book : this.patron.getBooks()){
			if (book.getDuedate() < this.todaysDate){
				s += book.toString() + " ***This book is overdue!" + "; ";
			}
			else{
				s += book.toString() + " is going to be due at " + book.getDuedate() + "; ";
			}
		}
		return s;
	}
}
