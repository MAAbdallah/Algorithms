import java.io.*;
import java.util.Vector;

public class NonUniform {


    Vector<Integer> arr = new Vector<Integer>();
    int levelnum = 1;
    int numBit = 1;

    NonUniform(Vector arr, int numBits) {
        for (int i = 0; i < arr.size(); i++) {
            this.arr.add((Integer) arr.get(i));
        }
        numBit = numBits;
        //System.out.println(getAve(arr));

        for (int i = 0; i < numBits; i++) {
            levelnum *= 2;
        }

        process();
        readC();
        readT();
        decom();
    }

    public int getAve(Vector arr) {
        double result = 0.0;
        int sum = 0;
        for (int i = 0; i < arr.size(); i++) {
            int temb = (int) arr.get(i);
            sum += temb;
        }
        result = (double) sum / (double) arr.size();
        double remender = result - (int) result;
        int Fresult = (int) result;
        if (remender != 0.0) {
            Fresult += 1;
        }
        return Fresult;
    }

    public Vector<Node2> association(Vector<Integer> v) {
        Vector<Node> diff = new Vector<Node>();
        for (int i = 0; i < arr.size(); i++) {
            Node n = new Node();
            n.data = arr.get(i);
            int min = arr.get(i) - v.get(0);
            if (min < 0) {
                min *= -1;
            }
            n.ave = v.get(0);
            for (int j = 0; j < v.size(); j++) {
                int d = arr.get(i) - v.get(j);
                if (d < 0) {
                    d *= -1;
                }
                if (d <= min) {
                    min = d;
                    n.ave = v.get(j);
                }
            }
            //System.out.println(n.ave+" : "+n.data);
            diff.add(n);
        }
        return association2(diff);

    }

    public boolean search(int data, Vector<Integer> v) {
        for (int i = 0; i < v.size(); i++) {
            if (data == v.get(i)) {
                return true;
            }

        }
        return false;
    }

    public Vector<Node2> association2(Vector<Node> v) {
        Vector<Node2> v2 = new Vector<Node2>();
        for (int i = 0; i < v.size(); i++) {
            Node2 n = new Node2();
            int ave = v.get(i).ave;
            for (int j = 0; j < v.size(); j++) {
                if (ave == v.get(j).ave && !search(v.get(j).data, n.v)) {
                    n.ave = ave;
                    n.v.add(v.get(j).data);
                }
            }
            boolean check = false;
            for (int c = 0; c < v2.size(); c++) {
                if (n.ave == v2.get(c).ave) {
                    check = true;
                }
            }
            if (check == false) {
                v2.add(n);
            }
        }
			/*for(int i=0;i<v2.size();i++)
			{
				for(int j=0;j<v2.get(i).v.size();j++)
				{
					System.out.println(v2.get(i).ave+" : "+v2.get(i).v.get(j));
				}
			}*/
        return v2;
    }

    Vector<Node2> v2 = new Vector<Node2>();
    Vector<Node2> oldV2 = new Vector<Node2>();
    Vector<Ranges> ranges = new Vector<Ranges>();

    //boolean first = true ;
    public void process() {
        Node2 temp = new Node2();
        temp.ave = getAve(arr);
        temp.v = arr;
        v2.add(temp);
        oldV2 = v2;
        Vector<Integer> v = new Vector<Integer>();
        for (int i = 0; i < v2.size(); i++) {
            int ave = getAve(v2.get(i).v);
            v.add(ave - 1);
            v.add(ave + 1);
        }
        v2 = association(v);
        System.out.println("*leafs num : " + v.size() + " accioiation : " + v2.size() + " in : " + levelnum);
        //levelnum--;
        while (v.size()<levelnum) {
            v = new Vector<Integer>();
            for (int i = 0; i < v2.size(); i++) {
                int ave = getAve(v2.get(i).v);
                v.add(ave - 1);
                v.add(ave + 1);
            }
            oldV2 = v2;
            v2 = association(v);
            System.out.println("leafs num : " + v.size() + " accioiation : " + v2.size() + " in : " + levelnum);
            //levelnum--;
        }

        for(int i=0;i<v2.size();i++)
        {
            System.out.println(v2.get(i).ave+" :");
            for (int j=0;j<v2.get(i).v.size();j++)
            {
                System.out.print(v2.get(i).v.get(j)+"  ");
            }
            System.out.println();
        }

        while (true) {
            int counter = 0;
            if (v2.size()==oldV2.size()) {
                for (int i = 0; i < v2.size(); i++) {
                    if (oldV2.get(i).v.equals(v2.get(i).v)) {
                        counter++;

                    }
                }
            }
                if (counter != v2.size()) {
                    v = new Vector<Integer>();
                    for (int i = 0; i < v2.size(); i++) {
                        int ave = getAve(v2.get(i).v);
                        v.add(ave);
                    }
                    oldV2 = new Vector<Node2>();
                    for(int i=0;i<v2.size();i++)
                    {
                        Node2 n = new Node2();
                        n.v = v2.get(i).v;
                        n.ave= v2.get(i).ave;
                        oldV2.add(n);
                    }
                    v2 = association(v);
                    System.out.println(v2.size());
                } else {
                    break;
                }
        }
        double low = 0.0;
        Ranges r = new Ranges();
        for(int i = 0;i<v2.size()-1;i++)
        {
            r = new Ranges();
            r.low=low;
            double range = ((double) v2.get(i).ave + (double) v2.get(i+1).ave) / 2;
            r.high=range;
            low=r.high;
            //System.out.println(r.low+"  "+r.high);
            r.Q=i;
            r._Q=v2.get(i).ave;
            ranges.add(r);
        }
        r = new Ranges();
        r.Q=v2.size()-1;
        r._Q=v2.get(v2.size()-1).ave;
        r.low=low;
        r.high=127;
        ranges.add(r);
        for (int i=0;i<ranges.size();i++)
        {
            System.out.println(ranges.get(i).low+"  "+ranges.get(i).high+" Q : "+ranges.get(i).Q+" Q-1 : "+ranges.get(i)._Q);
        }
        /*for(int i=0;i<v2.size();i++)
         {
            for(int j=0;j<v2.get(i).v.size();j++)
            {
                System.out.println(v2.get(i).ave+" : "+v2.get(i).v.get(j));
            }
         }*/
        writeInFile();
        com();

    }
    public void writeInFile()
    {
        String FileName = "table.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter  (out);
            for (int i = 0 ; i<ranges.size();i++)
            {
                Ranges r = new Ranges();
                r= ranges.get(i);
                buffer.write(r.low+":"+r.high+"|"+r.Q+"*"+r._Q+"#");
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
     Vector<Integer> q = new Vector<Integer>();
    public void com()
    {

        for(int i=0;i<arr.size();i++)
        {
            int index = 0;
            for(int j=0;j<ranges.size();j++)
            {
                if(arr.get(i)>=ranges.get(j).low&&arr.get(i)<ranges.get(j).high)
                {
                    index=j;
                    break;
                }
            }
            q.add(ranges.get(index).Q);
        }
        writecom();
    }
    public void writecom()
    {
        String FileName = "com.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter  (out);
            for (int i = 0 ; i<q.size();i++)
            {
                buffer.write(String.valueOf(q.get(i)));
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
     Vector<Integer> q2 = new Vector<Integer>();

    public  void readC()
    {
        try {
            FileReader in = new FileReader ("com.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                q2.add(Integer.parseInt(line));
            }
            buffer.close();

        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    Vector<Ranges> rangesout = new Vector<Ranges>();

    public  void readT()
    {
        try {
            FileReader in = new FileReader ("table.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                int i=0;
                String low ="";
                String high ="";
                String Q ="";
                String _Q ="";
                Ranges r = new Ranges();
                while (line.charAt(i)!=':')
                {
                    low+=line.charAt(i);
                    i++;
                }
                i++;
                while (line.charAt(i)!='|')
                {
                    high+=line.charAt(i);
                    i++;
                }
                i++;while (line.charAt(i)!='*')
                {
                    Q+=line.charAt(i);
                    i++;
                }
                i++;
                while (line.charAt(i)!='#')
                {
                    _Q+=line.charAt(i);
                    i++;
                }
                r.high=Double.parseDouble(high);
                r.low=Double.parseDouble(low);
                r.Q=Integer.parseInt(Q);
                r._Q=Integer.parseInt(_Q);
                rangesout.add(r);
            }
            buffer.close();
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     Vector<Integer> outP = new Vector<Integer>();
    public  void decom()
    {
        for (int i=0;i<q2.size();i++)
        {
            int index =0;
            for (int j=0;j<rangesout.size();j++)
            {
                if(q2.get(i)==rangesout.get(j).Q)
                {
                    index=j;
                    break;
                }
            }
            //System.out.println(rangesout.get(index)._Q);
            outP.add(rangesout.get(index)._Q);
        }
        writedecom();
    }
    public void writedecom()
    {
        String FileName = "decom.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter  (out);
            for (int i = 0 ; i<outP.size();i++)
            {
                buffer.write(String.valueOf(outP.get(i)));
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

}
