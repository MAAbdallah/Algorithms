import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class Com {
    Vector<String> chracters = new Vector<String>()  ;
    Vector<String> input = new Vector<String>() ;
    Vector<Double>  prob = new Vector<Double>() ;


    public static Map<String,Double> Low_Range = new HashMap<String,Double>();
    public static Map<String,Double> High_Range = new HashMap<String,Double>();


    /*public Com(String [] c,double [] p,String [] in) {
            for (int i=0;i<c.length;i++)
            {
                String x = "";
                x += c[i] ;
                chracters.add(x);
                prob.add(p[i]);
            }
            for(int i=0;i<in.length;i++)
            {

                String x ="";
                x += in[i];
                //System.out.println(x);
                input.add(x);
            }
            WriteTable();
            double temb = 0.0;
            for(int i=0;i<chracters.size();i++)
            {
                double low = temb;
                double high = temb+prob.get(i);
                temb=high ;
                Low_Range.put(chracters.get(i),low);
                High_Range.put(chracters.get(i),high);
            }
            writeSize();
            calc(0,1);
        }*/
   public Com(String in) {
        for (int i=0;i<in.length();i++)
        {
            String x ="";
            x += in.charAt(i);
            //System.out.println(x);
            input.add(x);
        }
        process();
       writeSize();
       calc(0,1);
    }
    public  void writeSize()
    {
        String FileName = "size.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter  (out);
            buffer.write(String.valueOf(input.size()));

            buffer.close();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void process ()
    {
        Vector<String> character = new Vector<String>();
        Vector<Integer> frequence = new Vector<Integer>();

        String c = "";
        int sum =0;
        for (int i=0;i<input.size();i++)
        {
            int f = 0;
            c = input.get(i);
            f++;
            for (int j = i+1;j<input.size();j++)
            {
                String x = input.get(j) ;
                if (c.equals(x))
                {
                    f++;
                    //System.out.println(c+" ++");
                }
                else
                {
                    continue ;
                }
            }

            if (!character.contains(c))
            {
                sum+=f;

                character.add(c);
                frequence.add(f);
                System.out.println("char : "+c+" freq: "+ f);
            }
        }
        System.out.println("sum: "+sum);
        /*for (int i=0;i<character.size();i++)
        {
            System.out.println(character.get(i) + " " + frequence.get(i));
        }*/
        for (int i = 0 ; i<character.size();i++)
        {
            double p = ((double)frequence.get(i)/(double)sum);
            System.out.println("char: "+character.get(i)+ " prob: "+p);
            chracters.add(character.get(i));
            prob.add(p);
        }
        /*for (int i=0;i<prob.size();i++)
        {
            System.out.println(prob.get(i));
        }*/
        WriteTable();

        double temb = 0.0;
        for(int i=0;i<chracters.size();i++)
        {
            double low = temb;
            double high = temb+prob.get(i);
            temb=high ;
            Low_Range.put(chracters.get(i),low);
            High_Range.put(chracters.get(i),high);
            System.out.println(character.get(i)+" lowR: "+low +"/ "+"highR: "+high);
        }
    }
    public void WriteTable()
    {
        String FileName = "table.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter  (out);
            for (int i = 0 ; i<chracters.size();i++)
            {
                buffer.write(chracters.get(i)+prob.get(i)+"|");
                buffer.newLine();
            }
            buffer.close();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public  void writeInFile(double x)
    {
        String FileName = "com.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter  (out);
                buffer.write(String.valueOf(x));

            buffer.close();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void calc (double low , double high)
    {
        if (input.size()==0)
        {
            Random rand = new Random();
             double  result = low + (high-low)*rand.nextDouble();
            System.out.println("result is : "+result);
            writeInFile(result);
            return;
        }
        for(int i=0;i<input.size();i++)
        {
            String temb = input.get(i);
            /*int index =-1;
            for(int j =0;j<chracters.size();j++)
            {
                if(temb.equals(Low_Range.get(j)))
                {
                    index=j;
                    break;
                }
            }*/
            input.remove(i);
            double lowR = Low_Range.get(temb);
            double highR = High_Range.get(temb);


            double L = low + (high-low) * lowR;
            //System.out.println(low + " " +high+"-"+low+" " + lowR+" = "+L);
            double H = low + (high-low) * highR;
            //System.out.println(low + " " +high+"-"+low+" " + highR+" = "+H);

            calc(L,H);
        }
    }

}
