import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Main {
   public static Vector<Integer> arr = new Vector<Integer>();
    public static void readFromfile()
    {
        try {
            FileReader in = new FileReader ("input.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                arr.add(Integer.parseInt(line));
            }
            buffer.close();

        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int num = 0;
        int i = 0;
        /*System.out.println("Enter the numbers ( to stop enter -1 ^^ )");
        while (true) {
            Scanner s = new Scanner(System.in);
            num = s.nextInt();
            if (num == -1) {
                break;
            } else {
                arr.add(num);
                i++;
            }
        }*/
        readFromfile();
        NonUniform n = new NonUniform(arr, 4);
    }


}
