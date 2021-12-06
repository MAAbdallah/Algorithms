import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;


public class Functions {
	
	private static String Fname ;
	private static String Tname ;
	private static String path = System.getProperty("user.dir");
	//private static String path = "G:\";
	private static String oldpath ;
	
	
	
	public static String getPath() {
		return path;
	}
	public static void setPath(String path) {
		Functions.path = path;
	}
	
	public static String getOldpath() {
		return oldpath;
	}
	public static void setOldpath(String oldpath) {
		Functions.oldpath = oldpath;
	}
	public Functions() {
		// TODO Auto-generated constructor stub
	}
	public String Pwd()
	{
		System.out.println(getPath());	
		return getPath() ;
	}
	public void Cd()
	{
		String name ;
		Scanner s = new Scanner(System.in);
        name = s.nextLine();
        if (name.contains(":"))
        {
        	File f = new File(name);
        	if (f.isDirectory())
        	{
        		setPath(name);
        	}
        	else {
        		System.out.println("path doesn't exist");
        	}
        }
        else
        {
	        String p = getPath()+ "\\" + name ;
	        String arr [] = Ls();
	        boolean check = false ; 
	        for(int i =0;i<arr.length;i++)
	        {
	        	if (arr[i]==name)
	        	{
	        		check = true ; 
	                setPath(p);
	        	}
	        }
	        if (check == false)
	        {
	        	System.out.println("folder not exist");
	        }
        }
        
        
        //setOldpath(getPath());
		//String dir = System.getProperty("user.dir",p);		
	}
	public String [] Ls()
	{
		String dir = Pwd();
		File files = new File(dir);
		String[] arr= files.list();
		Arrays.sort(arr);
		for (int i =0;i<arr.length;i++)
		{
			System.out.println(arr[i]);
		}
		return arr ;
	}
	
	public void Cat() 
	{
		Scanner s = new Scanner(System.in);
        Tname = s.nextLine();
		String name = getPath()+"\\"+Tname;
		try {
			FileReader in = new FileReader (name);
			BufferedReader buffer = new BufferedReader(in);
			String line="";
			while((line = buffer.readLine()) != null)
			{
				System.out.println(line);
			}
			buffer.close();

		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mkdir()
	{
        File f1 = null;
        boolean bool1 = false;
        
        Scanner s = new Scanner(System.in);
        Fname = s.nextLine();
        
        String name = Fname;
        try {
            // returns pathnames for files and directory
            f1 = new File(getPath()+"\\"+name);

            // create
            bool1 = f1.mkdir();

            // print
            System.out.print("Is Directory created? \n" + bool1 + "\n");

        } catch (Exception e) {
            // if any error occurs
            e.printStackTrace();
        }
	}
	
	
	public void rmdir() {
		Scanner s = new Scanner(System.in);
        Fname = s.nextLine();
        
        File directory = new File(Fname);
        
        int size = directory.list().length;
        if (size == 0)
        {
	        //make sure directory exists
	        if (!directory.exists()) {
	
	            System.out.println("Directory does not exist.");
	        } else {
	
	            directory.delete();
	            System.out.println("The Directory is deleted");
	        }
        }
        else 
        {
        	System.out.println("you can't remove floder has folders ");
        }

    }
	
	public void clear ()
	{
		for (int i =0;i<20;i++)
		{
			System.out.println();
		}
	}
	
	public static void date()
	{
		Date date = new Date();
		System.out.println(date);
	}
	
	public static void args()
	{
		System.out.print("clear : No Specific parameters.\n"+"cd : 1-No Parameter to back into root dirctory.\n 2-directory for destination\n"
				+"ls : 1-No Parameter to list current direction\n 2-direction for listing"+"\npwd : No parameters"+
				"\nmkdir : one directary for make it new directory"+"\nrmdir : one directory for delete empty directory"
				+"\ncp : two parameters for copying file or directory from first one to the second"+
				"\nmv : two parameters for moving file directory from first one to the second"+"\nrm : one parameter dirctory for deleting it"
				+"\ndate : no parameter"+ "\n cat : to Display the message in folder"+"\nhelp : No parameters");
	}
	
		public static void help()
	   {
		   System.out.println("clear command \t called to clear the current terminal screen \n"+
	   "cd command \t changes the current directory to another one \n"+"ls command \t list each given file or directory name \n"+
				   "pwd command \t Display current user directory \n"+"cp command \t If the last argument names an existing directory, cp copies each other given file into a file with the same name in that directory. Otherwise, if only two files are given, it copies the first onto the second \n"+
	      "mv command \t If the last argument names an existing directory, mv moves each other given file into a file with the same name in that directory. Otherwise, if only two files are given, it moves the first onto the second \n"+
				    "rm command \t removes each specified file \n"+"mkdir command \t creates a directory with each given name \n"+
	      "rmdir command \t removes each given empty directory \n"+"date command \t is to display or to set the date and time of the system \n "+
				    "cat command \t Concatenate files and print on the standard output \n"+"more command \t Let us display and scroll down the output in one direction only \n"+
	      "less command \t Like more but more enhanced. It support scroll forward and backward\n"+"| \t\t Use pipes “ | “ to redirect the output of the previous command as in input to another command\n"+
				    "args command \t list all parameters on the command line, numbers or strings for specific command \n"+
	 "date command \t output current system date and time \n"+
				    "help command \t list all user commands and the syntax of their arguments\n");
	   }
		
		public  static  void funcHelp() // ??
		{
			System.out.println("enter the commend u want ..");
			Scanner s = new Scanner(System.in);
			String co = s.nextLine();
	        if(co.equals("clear")){
	            System.out.println("clear command \t called to clear the current terminal screen \n");
	        }
	        else if(co.equals("cd")){
	          System.out.println(" cd command \t changes the current directory to another one \n");  
	        }
	        else if(co.equals("ls")){
	          System.out.println("ls command \t list each given file or directory name \n");  
	        }
	         else if(co.equals("pwd")){
	          System.out.println("pwd command \t Display current user directory \n");  
	        }
	         else if(co.equals("cp")){
	          System.out.println("cp command \t If the last argument names an existing directory, cp copies each other given file into a file with the same name in that directory. Otherwise, if only two files are given, it copies the first onto the second \n");  
	        }
	         else if(co.equals("mv")){
	          System.out.println("mv command \t If the last argument names an existing directory, mv moves each other given file into a file with the same name in that directory. Otherwise, if only two files are given, it moves the first onto the second \n");  
	        }
	         else if(co.equals("rm")){
	          System.out.println("rm command \t removes each specified file \n");  
	        }
	         else if(co.equals("mkdir")){
	          System.out.println("mkdir command \t creates a directory with each given name \n");  
	        }
	         else if(co.equals("rmdir")){
	          System.out.println("rmdir command \t removes each given empty directory \n");  
	        }
	         else if(co.equals("date")){
	          System.out.println("date command \t is to display or to set the date and time of the system \n ");  
	        }
	         else if(co.equals("cat")){
	          System.out.println("cat command \t Concatenate files and print on the standard output \n ");  
	        }
	         else if(co.equals("more")){
	          System.out.println("more command \t Let us display and scroll down the output in one direction only \n ");  
	        }
	         else if(co.equals("less")){
	          System.out.println("less command \t Like more but more enhanced. It support scroll forward and backward\n");  
	        }
	          else if(co.equals("args")){
	          System.out.println("args command \t list all parameters on the command line, numbers or strings for specific command \n");  
	        }
	 
	    }
		
		
	public void mv () // change file name 
	{

		/* Scanner s1 = new Scanner(System.in);
	     String Oldname  = s1.nextLine();
		 Scanner s2 = new Scanner(System.in);
	     String Newname  = s2.nextLine();
			String dir = Pwd();

	     File files = new File(dir);
	     String arr[] = files.list();
	     	for (int i = 0;i<arr.length;i++)
	     	{
	     		if (arr[i]==Oldname)
	     		{
	     			File f = new File(Oldname);
	     			f = new File (Newname);
	     		}
	     	}*/
		
		
	}
	
	public void cp()
	{
		
	}
	
	
	public void more ()
	{
		Scanner s1 = new Scanner(System.in);
        Tname = s1.nextLine();
		String name = getPath()+"\\"+Tname;
		try {
			FileReader in = new FileReader (name);
			BufferedReader buffer = new BufferedReader(in);
			String line="";
			int lines = 1;
			int counter = 10 ;
			boolean check = true ; 
			while(((line = buffer.readLine()) != null) && (check== true))
			{
				System.out.println(line);
				lines++;
				if (lines >counter)
				{
					System.out.println("Do you want to continue ?\n Y to continue , N to stop");

					Scanner s2 = new Scanner(System.in);
			        String Input = s2.nextLine();

			        if (Input.equals("y"))
			        {
			        	lines=1;
			        	counter*=2;
						//System.out.println(line);
						continue ;
			        }
			        else if (Input.equals("n"))
			        {
			        	check = false ;
			        }
				}


			}
			
			buffer.close();

		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rm ()
	{
			Scanner s = new Scanner(System.in);
			Fname = s.nextLine();
        
        	File dir = new File(getPath()+"\\"+Fname);
            
	        //make sure directory exists
	        if (!dir.exists()) 
	        {
	            System.out.println("Directory does not exist.");
	        } 
	        else 
	        {
	            dir.delete();
	            System.out.println("The Directory is deleted");
	        }
	}
	
	public void opereator () // >
	{
		Scanner s = new Scanner(System.in);
		Tname = s.nextLine();
		String arr[] = Ls();
		 try {
			FileWriter out = new FileWriter (Tname);
			BufferedWriter buffer = new BufferedWriter  (out);
			for (int i=0;i<arr.length;i++)
			{
				buffer.write(arr[i]);
					buffer.newLine();
			}
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void opereator2() throws IOException // >>
	{

		Scanner s = new Scanner(System.in);
		Tname = s.nextLine();
		String arr[] = Ls();

		/*File f = new File(Tname);
		FileWriter out = new FileWriter (Tname);
		//BufferedWriter buffer = new BufferedWriter  (out);

		for (int i=0;i<arr.length;i++)
		{
			Files.write(arr[i], Tname.getBytes(), StandardOpenOption.APPEND);
		}
		out.close();*/
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Tname, true)))) {
		    for(int i=0;i<arr.length;i++)
		    {
		    	out.write(arr[i]);
		    }
		}catch (IOException e) {
		    System.err.println(e);
		}
	}
	
	public void pipe()
	{
		
	}
	
}
