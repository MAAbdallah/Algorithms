import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;

public class FeedForward {

    Vector<Integer> arr = new Vector<Integer>();
    int levelnum = 1;
    int numBit = 1;
    Vector<Row> table = new Vector<Row>();
    Vector<RowCom> tableCom = new Vector<RowCom>();
    Vector<RowDecom> tableDeCom = new Vector<RowDecom>();

    Vector<Integer> comCode = new Vector<Integer>();
    Vector<Integer> deComCode = new Vector<Integer>();

    public FeedForward() {

    }

    public FeedForward(Vector<Integer> arrInput, int numBits) {
        for (int i = 0; i < arrInput.size(); i++) {
            arr.add(arrInput.get(i));
            //System.out.println(arr.get(i));
        }

        numBit = numBits;
        for (int i = 0; i < numBits; i++) {
            levelnum *= 2;
        }
        int step = (arrInput.get(arrInput.size()-1)-arrInput.get(0))/levelnum;

        int low =0;
        int high = step-1;
        for (int i=0;i<levelnum;i++)
        {
            Row row = new Row();
            row.code=i;
            row.start=low;
            row.end=high;
            int mid = (row.start+row.end+1)/2;
            row.Q1=mid;
            low=row.end+1;
            high=low+step-1;
            table.add(row);
        }
        writeT();
        Com();
    }
    public void writeT()
    {
        String FileName = "table.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter(out);
            for (int i = 0 ; i<table.size();i++)
            {
                Row r = new Row();
                r= table.get(i);
                buffer.write(r.code+"/"+r.start+"/"+r.end+"/"+r.Q1+"/");
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
    public  void readT()
    {
        try {
            FileReader in = new FileReader ("table.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                String[] parts = line.split("/");
                Row r = new Row();
                r.code=Integer.parseInt(parts[0]);
                r.start=Integer.parseInt(parts[1]);
                r.end=Integer.parseInt(parts[2]);
                r.Q1=Integer.parseInt(parts[3]);
                table.add(r);
            }
            buffer.close();
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public int FindCode(int diff)
    {
        if(diff<0)
        {
            diff*=-1;
        }
        for(int i=0;i<table.size();i++)
        {
            if(diff>=table.get(i).start&&diff<table.get(i).end)
            {
                return table.get(i).code;
            }
        }
        return -1 ;
    }
    public void Com ()
    {
        RowCom rowCom = new RowCom();
        rowCom.data=arr.get(0);
        rowCom.diff=-1;
        rowCom.Q=arr.get(0);
        tableCom.add(rowCom);
        for (int i=1;i<arr.size();i++)
        {
            rowCom = new RowCom();
            rowCom.data=arr.get(i);
            rowCom.diff=arr.get(i)-arr.get(i-1);
            int temp =FindCode(rowCom.diff);
            rowCom.Q = FindCode(rowCom.diff);
            if (rowCom.diff<0)
            {
                rowCom.state = true ;
            }
            tableCom.add(rowCom);
        }
        writeTcom();
        writecomCode();
    }
    public void writeTcom()
    {
        String FileName = "tableCom.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter(out);
            for (int i = 0 ; i<tableCom.size();i++)
            {
                RowCom r = new RowCom();
                r= tableCom.get(i);
                buffer.write(r.data+"/"+r.diff+"/"+r.Q+"/"+r.state+"/");
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
    public  void readTCom()
    {
        try {
            FileReader in = new FileReader ("tableCom.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                String[] parts = line.split("/");
                RowCom r = new RowCom();
                r.data=Integer.parseInt(parts[0]);
                r.diff=Integer.parseInt(parts[1]);
                r.Q=Integer.parseInt(parts[2]);
                r.state= Boolean.getBoolean(parts[3]);
                tableCom.add(r);
            }
            buffer.close();
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writecomCode()
    {
        String FileName = "ComCode.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter(out);
            for (int i = 0 ; i<tableCom.size();i++)
            {
                RowCom r = new RowCom();
                r= tableCom.get(i);
                buffer.write(String.valueOf(r.Q));
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
    public  void readComCode()
    {
        try {
            FileReader in = new FileReader ("ComCode.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                comCode.add(Integer.parseInt(line));
            }
            buffer.close();
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public int FindDeCode(int code)
    {
        for(int i=0;i<table.size();i++)
        {
            if(code==table.get(i).code)
            {
                return table.get(i).Q1;
            }
        }
        return -1 ;
    }
    public void Decom ()
    {
        readT();
        readTCom();
        readComCode();
        RowDecom rowDecom = new RowDecom();
        rowDecom.DeQ=-1;
        rowDecom.Decoded=comCode.get(0);
        rowDecom.error=0;
        tableDeCom.add(rowDecom);
        for (int i=1;i<comCode.size();i++)
        {
            rowDecom = new RowDecom();
            rowDecom.DeQ=FindDeCode(comCode.get(i));
            if (tableCom.get(i).state==false)
            {
                rowDecom.Decoded=tableDeCom.get(i-1).Decoded+rowDecom.DeQ;
            }
            else
            {
                rowDecom.Decoded=tableDeCom.get(i-1).Decoded-rowDecom.DeQ;
            }
            rowDecom.error= (int) Math.pow(rowDecom.Decoded-tableCom.get(i).data,2);
            tableDeCom.add(rowDecom);
        }
        writeTDecom();
        writeDecomCode();
    }
    public double getMSE()
    {
        double result =0;
        for (int i=1;i<tableDeCom.size();i++)
        {
            result+=tableDeCom.get(i).error;
        }
        return (result/(tableDeCom.size()-1)) ;
    }
    public void writeTDecom()
    {
        String FileName = "tableDeCom.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter(out);
            for (int i = 0 ; i<tableDeCom.size();i++)
            {
                RowDecom r = new RowDecom();
                r= tableDeCom.get(i);
                buffer.write(r.DeQ+"/"+r.Decoded+"/"+r.error+"/");
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
    public  void readTDeCom()
    {
        try {
            FileReader in = new FileReader ("tableDeCom.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                String[] parts = line.split("/");
                RowDecom r = new RowDecom();
                r.DeQ=Integer.parseInt(parts[0]);
                r.Decoded=Integer.parseInt(parts[1]);
                r.error=Integer.parseInt(parts[2]);
                tableDeCom.add(r);
            }
            buffer.close();
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeDecomCode()
    {
        String FileName = "DeComCode.txt";
        try
        {
            FileWriter out = new FileWriter (FileName);
            BufferedWriter buffer = new BufferedWriter(out);
            for (int i = 0 ; i<tableDeCom.size();i++)
            {
                RowDecom r = new RowDecom();
                r= tableDeCom.get(i);
                buffer.write(String.valueOf(r.Decoded));
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
    public  void readDeComCode()
    {
        try {
            FileReader in = new FileReader ("DeComCode.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                deComCode.add(Integer.parseInt(line));
            }
            buffer.close();
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Vector<Integer> getDeComCode()
    {
        return deComCode ;
    }
}
