import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Decom {

    Vector<String> chracters = new Vector<String>()  ;
    Vector<Double>  prob = new Vector<Double>() ;
    Vector<String> output = new Vector<String>() ;

    public static Map<String,Double> Low_Range = new HashMap<String,Double>();
    public static Map<String,Double> High_Range = new HashMap<String,Double>();
    double ComCode = 0.0;
    int Size = 0;

    public Decom()
    {
       ComCode= readCode();
        Size = readSize() ;
        readTable();
        generateMap();
        outPut(0,1);
    }
    public void generateMap()
    {
        double temb = 0.0;
        for(int i=0;i<chracters.size();i++)
        {
            double low = temb;
            double high = temb+prob.get(i);
            temb=high ;
            Low_Range.put(chracters.get(i),low);
            High_Range.put(chracters.get(i),high);
        }
    }
    public double readCode()
    {
        String FileName = "com.txt";
        String word ="";

        try{

            FileReader in = new FileReader (FileName);
            BufferedReader buffer = new BufferedReader(in);
            word = buffer.readLine();
            buffer.close();
        }
        catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Double.parseDouble(word);
    }
    public  void readTable() {
        String FileName = "table.txt";
        String word = "";
        try {
            FileReader in = new FileReader(FileName);
            BufferedReader buffer = new BufferedReader(in);
            while ((word = buffer.readLine()) != null)
            {
                int i =0;
                chracters.add(String.valueOf(word.charAt(i)));
                String temb = "";
                i++;
                while (word.charAt(i)!='|')
                {
                    temb+=word.charAt(i);
                    i++;
                }
                prob.add(Double.parseDouble(temb));
            }
            buffer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block

        }
       /* for (int i =0;i<chracters.size();i++)
        {
            System.out.println(chracters.get(i)+" "+prob.get(i));
        }*/
    }
    public int readSize() {
        String FileName = "size.txt";
        String word = "";

        try {

            FileReader in = new FileReader(FileName);
            BufferedReader buffer = new BufferedReader(in);
            word = buffer.readLine();
            buffer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Integer.parseInt(word);
    }

    public  void writeInFile(String x)
    {
        String FileName = "Decom.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter  (out);
            buffer.write(x);

            buffer.close();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void outPut(double low , double high)
    {
        for (int i = 0;i<Size;i++)
        {
            double code = (ComCode - low) / (high - low);
            double range = high - low ;
            double Lower = 0.0 ;
            double Upper = 0.0 ;
            for (int j=0;j<chracters.size();j++) {
                if ((code > Low_Range.get(chracters.get(j))) && (code <= High_Range.get(chracters.get(j)))) {
                    output.add(chracters.get(j));
                    Lower = low+ range * Low_Range.get(chracters.get(j));
                    Upper = low + range *  High_Range.get(chracters.get(j));
                    //System.out.println("bl777");
                    break;
                }
            }
            low=Lower;
            high=Upper;
        }
        String temb = "";
        for(int i=0;i<output.size();i++)
        {
            temb+=output.get(i);
        }
        System.out.println(temb);
        writeInFile(temb);
    }

}
