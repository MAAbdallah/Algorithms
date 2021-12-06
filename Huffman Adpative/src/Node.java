
public class Node {
	
	private String data = "";
	private int freq = 0 ;
	Node left = null ;
	Node right = null;
	
	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public Node(String data , int freq) {
		// TODO Auto-generated constructor stub
		left = right = null;
		this.data= data ;
		this.freq = freq ;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	@Override
	public String toString ()
	{
		return data + " " + freq ;
	}
	
}
