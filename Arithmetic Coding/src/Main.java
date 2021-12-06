import java.util.Scanner;

public class Main {
    public  static  void main(String [] arg)
    {
        // com with out prob
        System.out.println("Enter your input : ");
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        Com c = new Com(input);

        // com with prob
        /*String [] input = {"a","c","b","a"};
        String [] cahracters = {"a","b","c"};
        double [] prob = {0.8,0.02,0.18};
        Com c = new Com(cahracters,prob,input);*/

        //decom
        Decom d = new Decom();
    }
}
