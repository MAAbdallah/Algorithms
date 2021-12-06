import java.util.Scanner;

public class Main {

	private static Scanner s;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*String Input="" ;
		s = new Scanner(System.in);
		Input = s.nextLine();*/
		
		Lz77 L = new Lz77();
		//L.process(Input); // convert Input to tags
		L.deProcess();  // convert tags to outPut
		//L.read();   // read from file
		L.print();  // read vector	
	}
}
