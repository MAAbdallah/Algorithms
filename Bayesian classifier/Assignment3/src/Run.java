import java.util.Scanner;

public class Run {

    public static void main(String[] args) {
        // write your code here
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1 for bayse ");
            System.out.println("2 for knearest ");
            System.out.println("3 for both and comparison  ");
            System.out.println("4 for exit  ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                MainBayse mainBayse = new MainBayse();
            }
            if (input.equals("2")) {
                MainKnear mainKnear = new MainKnear();
            }
            if (input.equals("3")) {

                MainBayse mainBayse = new MainBayse();
                double accurcyB = mainBayse.accurcy;
                long dB = mainBayse.duration/1000000;

                MainKnear mainKnear = new MainKnear();
                double accurcyK = mainKnear.accurcy;
                long dK = mainKnear.duration/1000000;

                System.out.println("baysen accurcy : " + accurcyB + "  and it's time proccesing "+dB);
                System.out.println("K-nearest accurcy : " + accurcyK+ "  and it's time proccesing "+dK);

                if (accurcyB > accurcyK) {
                    System.out.println("bayesin algorithm have high accurcy than K-nearest ");
                } else {
                    System.out.println("K-nearest  have high accurcy than bayesin algorithm ");
                }

                if (dB > dK) {
                    System.out.println("K-nearest  have speep time proccing than bayesin algorithm ");
                } else {
                    System.out.println("bayesin algorithm have speep time proccing than K-nearest ");
                }
            }
            if(input.equals("4")){return;}
        }
    }
}
