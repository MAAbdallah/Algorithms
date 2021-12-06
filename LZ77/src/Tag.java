
public class Tag {
	
	public Tag() {
		// TODO Auto-generated constructor stub
		  pointer = 0 ;
		  length = 0 ;
		  next = ' ' ;
	}
	private int pointer = 0 ;
	private int length = 0 ;
	private char next = ' ' ;
	
	public int getPointer() {
		return pointer;
	}
	public void setPointer(int pointer) {
		this.pointer = pointer;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public char getNext() {
		return next;
	}
	public void setNext(char next) {
		this.next = next;
	}
	
	
}
