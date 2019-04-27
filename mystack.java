package eg.edu.alexu.csd.datastructure.stack.cs30;

import java.util.EmptyStackException;

public class mystack implements IStack {

	private class Node {
		private Object data;
		private Node next;
		Node(){
			
		}
		Node(Object data,Node next){
			this.setData(data);
			this.setNext(next);
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
		
	}

	private Node head;
	private int size;
	mystack(){
		head=null;
		size=0;
	}
	@Override
	public Object pop() {
		if(this.isEmpty())
			throw new EmptyStackException();
		else {
			Object returned=head.getData();
			head=head.getNext();
			size--;
			return returned;
		}
	}

	@Override
	public Object peek() {
		if(this.isEmpty())
			throw new EmptyStackException();
		else
			return head.getData();
	}

	@Override
	public void push(Object element) {
		Node newNode=new Node(element,head);
		head=newNode;
		size++;
	}

	@Override
	public boolean isEmpty() {
		if(head==null)
			return true;
		return false;
	}

	@Override
	public int size() {
		return size;
	}

}
