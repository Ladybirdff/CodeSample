/**
 * implementation of LinkedList using MyGenericNode.
 * @author Weixi Ma
 *
 */
public class MyGenericLinkedList<T> {

	private MyGenericNode<T> head;
	private int size;

	/**
	 * The constructor - initializes an empty linked list.
	 * with null head and zero size
	 */
	public MyGenericLinkedList() {
		head = null;
		size = 0;
	}

	/**
	 * This method will create a new node.
	 * It will add it as the first node of the linked list.
	 * @param value the value to be added
	 */
	public void addFirst(T value) {

		MyGenericNode<T> node = new MyGenericNode<T>(value);
		node.next = head;
		head = node;

		// increment the size of linked list
		size++;
	}

	/**
	 * This method will create a new node.
	 * It will add it as the last node of the linked list.
	 * @param value the value to be added
	 */
	public void addLast(T value) {

		// if the size is 0, call addFirst
		if (size == 0) {
			addFirst(value);
		} else {
			MyGenericNode<T> node = new MyGenericNode<T>(value);

			MyGenericNode<T> i = head;

			// traverse the linked list to find the last node
			while (i.next != null) {
				i = i.next;
			}

			// add the last of list and increment size
			i.next = node;
			size++;
		}
	}

	/**
	 * This methods displays the contents of the linked list
	 * for debug purpose
	 */
	/*public void display() {

		if (size != 0) {


			MyGenericNode<T> i = head;

			System.out.println("Printing out the contents of the linked list");

			while (i.next != null) {
				System.out.print(i.value + " ---> ");
				i = i.next;
			}
			
			System.out.print(i.value);
			System.out.println();
		}
	}*/
	
	/**
	 * delete the first node of the linked list
	 * @return the value of the first node if the size is greater than 0
	 *         otherwise return null
	 */
	public T deleteFirst(){
		if (size > 0){
			T temp = head.value;
			head = head.next;
			size--;
			return temp;
		}
		else return null;
	}
		
	/**
	 * get the size of the linked list
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

}
