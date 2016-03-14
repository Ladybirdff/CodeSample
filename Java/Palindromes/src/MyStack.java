import java.util.EmptyStackException;

/**
 * implement a Stack using Linked Lists
 * supports the following methods – push, pop, size, isEmpty
 * @author Weixi Ma
 *
 */
public class MyStack<T>{
	
	MyGenericLinkedList<T> list;
		
	/**
	 * the constructor for the stack
	 */
	public MyStack(){
		list = new MyGenericLinkedList<T>();
	}

	/**
	 * push an element on the top of the stack
	 * call the addFirst method of the linked list class to add a first node
	 * @param value the value to be pushed on the stack
	 */
	public void push(T value){
		list.addFirst(value);
	}

	/**
	 * pop an element off the top of the stack if the stack is not empty
	 * otherwise throw an EmptyStackException
	 * call the deleteFirst method of the linked list class to delete the first node 
	 * @return the value of the element which is popped
	 */
	public T pop(){
		if (list.getSize() == 0){
			throw new EmptyStackException();
		}
		T temp = list.deleteFirst();
		return temp;
	}

	/**
	 * get the size of the stack
	 * call the size getter of linked list
	 * @return the size
	 */
	public int size(){
		return list.getSize();
	}

	/**
	 * check whether the stack is empty by looking the size
	 * @return true if it empty, false otherwise
	 */
	public boolean isEmpty(){
		if (list.getSize() > 0) return false;
		else return true;
	}
	
}
