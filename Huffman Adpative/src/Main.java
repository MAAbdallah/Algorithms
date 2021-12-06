import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String input = s.nextLine();
		Huffman h = new Huffman();
		h.generate(input);
		h.build();
		h.buildTextCode();
		//h.print();
		h.readFromfile();
		//h.printCodes();
		//h.printText();
		h.Deprocess();
	}

}
