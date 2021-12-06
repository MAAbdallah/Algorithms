import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Lzw {

	Vector<String> dec = new Vector<String>(); // 0 to 127  search text 

	public Lzw() {
		// TODO Auto-generated constructor stub
		for(int i= 0 ; i<128 ; i++)
        {
            char c = (char)i;
           String st="";
           st+=c;
           dec.add(st);
            //System.out.println(i+text.get(i));
        } 
	}
	
	public boolean search(String s)
    {
        if(dec.contains(s))
            return true;
      return false;
    }
	
	Vector<Tag> v = new Vector<Tag>();
	
	public void process (String Input)
	{
		for (int i = 0 ;i<Input.length();)
		{
			int counter =0;
			Tag t = new Tag();
			int y = i+1;
			String current = Input.substring(i, y);
			while (search(current)&&y<Input.length())
			{
				int index = dec.indexOf(current);
				t.setPointer(index);
				current=Input.substring(i-counter, ++y);
				i++;
				counter++;
			}
			if (search(current)&&y>=Input.length())
			{
				int index = dec.indexOf(current);
				t.setPointer(index);
				i++;
			}
			if (!search(current))
			{
				dec.addElement(current);
			}
			v.addElement(t);
		}
		writeInFile();
	}
	
	public void print()
	{
	     System.out.println("Vector Tags are:");
	     for (int i = 0 ; i<v.size();i++)
	     {
	    	 Tag temp = new Tag();
	    	 temp = v.get(i);
	    	 //System.out.println(temp.getPointer());
	    	 //System.out.println(v.get(i).getPointer());
	    	 System.out.println(temp.getPointer()+"\n");
	     } 
	}
	
	public void writeInFile()
	{
		String FileName = "com.txt";
		try
		{
			FileWriter out = new FileWriter (FileName);
			BufferedWriter buffer = new BufferedWriter  (out);
			for (int i = 0 ; i<v.size();i++)
			{
				Tag t = new Tag();
				t= v.get(i);
				int index = t.getPointer() ;
				//System.out.println(index);
				buffer.write(index+" ");
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
	
	public void read ()
	{
		// take tags in file put it in vector
		
		String FileName = "com.txt";
		String word ="";

		try{

			FileReader in = new FileReader (FileName);
			BufferedReader buffer = new BufferedReader(in);
			while((word = buffer.readLine()) != null)
			{
				Tag t = new Tag();
				int i = 0;
				String pointer = "";
				while (word.charAt(i) != ' ')
				{
					pointer+=word.charAt(i);
					i++;
				}
				//System.out.println(word+"ok");
				t.setPointer(Integer.parseInt(pointer));
				
				v.addElement(t);
			}
			
			
			buffer.close();
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//  wil be change 
	public void deProcess()
	{
		read();
		Tag t= new Tag();
		String outPut="";
		String current="";
		for (int i=0;i<v.size();i++)
		{
			Tag temp = new Tag();
			temp = v.get(i);
			if(temp.getPointer()>dec.size()-1)
			{
				Tag save = new Tag();
				save = v.get(i-1);
				String x = dec.get(save.getPointer());
				String s = x+x.charAt(0);
				dec.add(s);
				outPut+= s;
			}
			else if (i>0)
			{
				current = dec.get(temp.getPointer());

				Tag save = new Tag();
				save = v.get(i-1);
				String x = dec.get(save.getPointer());
				String s = x+current.charAt(0);
				if(!search(s))
				{
					dec.add(s);
				}
				outPut+=current;	
			}
			else
			{
				current = dec.get(temp.getPointer());

				outPut += current ;
			}
		}
		System.out.println(outPut);
		
		String FileName = "Decom.txt";
		try
		{
			FileWriter out = new FileWriter (FileName);
			BufferedWriter buffer = new BufferedWriter  (out);
			buffer.write(outPut);
			buffer.newLine();
			
			buffer.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
