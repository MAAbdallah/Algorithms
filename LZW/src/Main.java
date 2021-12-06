import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String Input="" ;
		Input = new Scanner(System.in).nextLine();
		
		Lzw L = new Lzw();
		L.process(Input); // convert Input to tags
		//L.deProcess();  // convert tags to outPut
		//L.read();   // read from file
		L.print();  // read vector
		//L.deProcess();

	}

}
