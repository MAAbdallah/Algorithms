import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class VectorMain {

	public static void main(String[] args) throws IOException {
		VectorQuantizer com =  new VectorQuantizer();
		File file=new File("C:\\Users\\Aabdallah Mostafa\\Desktop\\VectorQuantizer\\cameraMan.jpg");
		try {
			com.compress(file,7,10,10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file2=new File("C:\\Users\\Aabdallah Mostafa\\Desktop\\VectorQuantizer\\compressed.txt");
		try {
			com.decompress(file2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}


