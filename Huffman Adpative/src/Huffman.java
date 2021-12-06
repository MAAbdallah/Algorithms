import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Huffman {
	
	// create vector
	Vector<Node> v = new Vector<Node>();    //  the tree of nodes    when writing
	Vector<Code> c = new Vector<Code>();    //  each char & his code  when wriring & reading 
	Vector<String> text = new Vector<String>();   // Input text     when reading
	
	// process fun 
	// get each char & freq  & push it in v 
	public void generate (String Input)
	{
		//Map myMap = new HashMap();
		//String character [] = null ;/*{"a","b","c","d","e","f"};*/
		//int frequence [] = null ; /*{5,9,12,13,16,45};*/
		Vector<String> character = new Vector<String>();
		Vector<Integer> frequence = new Vector<Integer>();

		String c = "";
		for (int i=0;i<Input.length();i++)
		{
			int f = 0;

			int y = i+1;
			c = Input.substring(i, y);
			text.add(c);
			f++;
			for (int j = i+1;j<Input.length();j++)
			{
				int q = j+1;
				String x = Input.substring(j, q) ;
				if (c.equals(x))
				{
					f++;
				}
				else 
				{
					continue ;
				}
			}
			if (!character.contains(c))
			{
				character.add(c);
				frequence.add(f);
			}
			
		}
		for (int i = 0 ; i<character.size();i++)
		{
			Node temb = new Node(character.get(i),frequence.get(i));
			v.addElement(temb);
		}
		//System.out.println(myMap+"\t");
	}
	
	// print vector 
	public void print ()
	{
		for (int i =0;i<v.size();i++)
		{
			System.out.println(v.get(i));
		}
	}
	// print codes 
	public void printCodes ()
	{
		for (int i =0;i<c.size();i++)
		{
			System.out.println(c.get(i));
		}
	}
	// print text
	public void printText ()
	{
		for (int i =0;i<text.size();i++)
		{
			System.out.print(text.get(i));
		}
	}
	
	// write char & code
	public void writeInFile(String FileName , String data , String code )
	{
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FileName, true)))) {

			//Node n = new Node();
			//n= v.get(i);
			out.write(data+code+"|");
		}catch (IOException e) {
		    System.err.println(e);
		}
		
	}
	
	public void buildTextCode()
	{
		// to clear the txt file 
		 try {
				FileWriter out = new FileWriter ("textCode.txt");
				BufferedWriter buffer = new BufferedWriter  (out);
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		readFromfile();
		//printCodes();
		for (int i = 0;i<text.size();i++)
		{
			String temp = text.get(i);
			int index = -1;
			for (int j=0;j<c.size();j++)
			{
				if (c.get(j).data.equals(temp))
				{
					index = j;
					break;
				}
			}
			
			String Code = c.get(index).code;
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("textCode.txt", true)))) {

				out.write(Code+"|");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	// print tree 
	public void printTree (Node top,String code)
	{
		if (top==null)
		{
			return ;
		}
		if (top.getData()!="*")
		{
			System.out.println(top.getData()+" : "+code);
			writeInFile("com.txt",top.getData(),code);
		}
		printTree(top.left,code+"0");
		printTree(top.right,code+"1");
	}
	
	// sort nodes using bubble sort
	public void sort ()
	{
		for (int i=0; i<v.size(); i++)
    	{
	        Node temp = new Node() ;
	        for(int j=0; j<v.size()-1; j++)
	        {
	            if(v.get(j).getFreq()<v.get(j+1).getFreq())
	            {
	                continue;
	            }
	            else
	            {
	            	//swap(v.get(j),v.get(j+1));
	            	Collections.swap(v, j, j+1);
	            }
        	}
    	}
	}
	
	/*public static  void swap(Node node1, Node node2) {
		// TODO Auto-generated method stub
		Node temp = node1;	
        node1 = node2;
        node2 = temp;
	}*/
	
	public void build()
	{
		Node left = null , right = null ;
		Node top = new Node ();
		
		// to clear the txt file 
		 try {
				FileWriter out = new FileWriter ("com.txt");
				BufferedWriter buffer = new BufferedWriter  (out);
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			sort();
			if (v.size()==1)
			{
				left=v.get(0);
				v.remove(0);
				top=new Node("*",left.getFreq());
				top.left=left;
				v.add(top);
			}
			else 
			{
				 // build the tree 
				while(v.size()!=1)
				{
					left=v.get(0);
					v.remove(0);
					right=v.get(0);
					v.remove(0);
					top=new Node("*",left.getFreq()+right.getFreq());
					top.left=left;
					top.right=right;
					v.add(top);
					sort();
				}
			}
		printTree(top,"");
	}
	
	public void readFromfile()
	{
		try {
			FileReader in = new FileReader ("com.txt");
			BufferedReader buffer = new BufferedReader(in);
			String line="";
			line = buffer.readLine();
			Code co ;
			for (int i=0;i<line.length();i++)
			{
				int y = i+1;
				String x = line.substring(i, y);   // carry the char 
				i++;
				String code = "";
				while(line.charAt(i)!='|')
				{
					code += line.charAt(i);  
					i++;
				}
				co= new Code(x,code);	
				c.addElement(co);
			}
			buffer.close();

		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String readcode()
	{
		String line="";

		try {
			FileReader in = new FileReader ("textCode.txt");
			BufferedReader buffer = new BufferedReader(in);
			line = buffer.readLine();
			buffer.close();

		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	public void Deprocess()
	{
		String outPut="";
		String codes = readcode();
		for(int i=0;i<codes.length();i++)
		{
			String temp="";
			while (codes.charAt(i)!='|')
			{
				temp+=codes.charAt(i);
				i++;
			}
			int index = -1;
			for (int j=0;j<c.size();j++)
			{
				if (c.get(j).code.equals(temp))
				{
					index = j;
					break;
				}
			}
			
			String data = c.get(index).data;
			outPut+=data;
		}
		System.out.println(outPut);
		
		try {
			FileWriter out = new FileWriter ("Decom.txt");
			BufferedWriter buffer = new BufferedWriter  (out);
			buffer.write(outPut);
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
