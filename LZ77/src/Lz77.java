import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class Lz77  {
	
	public Lz77() {
		// TODO Auto-generated constructor stub
	}
	//Tag t = new Tag();
	Vector<Tag> v = new Vector<Tag>();

	public void process (String Input)
	{
		String current = "" ;
		String searchText = "" ;
		char next = ' ' ;
		//int index = 0 ;
		int lastIndex = 0 ;
		//Vector<Tag> v = new Vector<Tag>();
		for (int i = 0 ; i < Input.length() ; i = searchText.length())
		{
			int y= i+1 ;
			current = Input.substring(i,y);
			lastIndex = searchText.lastIndexOf(current);
			if(lastIndex==-1)
			{
				Tag t = new Tag();

				next = current.charAt(0);
				t.setPointer(0);
				t.setLength(0);
				t.setNext(next);
				v.add(t);
				searchText+= current ;
			}
			else 
			{
				Tag t = new Tag();

				int index = i ;
				while(lastIndex!=-1&&(searchText.length()+current.length())!=Input.length())
				{
					t.setPointer(i-lastIndex);
					t.setLength(current.length());
					
					//if(index < Input.length()-1)
					//{
						next = Input.charAt(index+1);
					//}
					
					current = Input.substring(i,++y);
					lastIndex = searchText.lastIndexOf(current);
					if (lastIndex==-1)
					{
						searchText += current ;
						t.setNext(next);
						v.addElement(t);
					}
					else
					{
						index++;
						continue ;
					}
				}
				if ((searchText.length()+current.length())==Input.length()&&lastIndex!=-1)
				{
					t.setPointer(i-lastIndex);
					t.setLength(current.length());
					next='0';
					searchText+=current;
					t.setNext(next);
					v.addElement(t);
				}
			}
			
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
	    	 System.out.println(temp.getPointer()+" "+temp.getLength()+" " +temp.getNext()+'\n');
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
				buffer.write(t.getPointer()+" "+t.getLength()+" "+t.getNext());
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

				String pointer ="";
				String length="";
				char next = ' ' ;
				int i = 0 ;
				while (word.charAt(i) != ' ')
				{
					pointer+=word.charAt(i);
					i++;
				}
				t.setPointer(Integer.parseInt(pointer));
				i++;
				while(word.charAt(i)!=' ')
				{
					length+=word.charAt(i);
					i++;
				}
				t.setLength(Integer.parseInt(length));
				i++;
				next = word.charAt(i);
				t.setNext(next);
				v.addElement(t);
			}
			
			
			buffer.close();
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deProcess()
	{
		read();
		Tag t= new Tag();
		String outPut="";
		String current="";
		for (int i=0;i<v.size();i++)
		{
			t=v.get(i);
			int beginIndex = outPut.length()-t.getPointer();
			int endIndex =beginIndex+t.getLength();
			current=outPut.substring(beginIndex, endIndex);
			if(t.getNext()!='0')
			{
				outPut+=current+t.getNext();
			}
			else
			{
				outPut+=current;
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
