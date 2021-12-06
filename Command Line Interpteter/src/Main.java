import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub
		/*Functions f = new Functions(); 
		//f.Pwd();
		//f.Ls();
		
		//f.makdir();
		f.Ls();
		//f.Cat();

		//f.rmdir();
		f.Cd();
		f.Ls();
		//f.Cat();
		//f.clear();
		//f.mv();
		//f.date();
		f.more();*/
		Functions f = new Functions(); 

		while(true)
		{
			/*System.out.println("Enter the NUM OF commend u want .."+"\n"
					+ "1)PWD\n2)LS\n3)CD\n4)MKDIR\n5)RMDIR\n6)CAT\n7)MORE\n8)CLEAR\n9)HELP\n"
					+ "10)ARGS\n11)DATE\n12)?\n13)>\n14)>>\n15)pipe\n16)CP\n17)MV\n18)RM\n"	);*/
			f.Pwd();
			Scanner s1 = new Scanner(System.in);
			String Input = s1.nextLine();
			if (Input.equals("pwd"))
			{
				f.Pwd();
			}
			else if (Input.equals("ls"))
			{
				f.Ls();
			}
			else if (Input.equals("cd"))
			{
				f.Cd();
			}
			else if (Input.equals("mkdir"))
			{
				f.mkdir();
			}
			else if (Input.equals("rmdir"))
			{
				f.rmdir();
			}
			else if (Input.equals("cat"))
			{
				f.Cat();
			}
			else if (Input.equals("more"))
			{
				f.more();
			}
			else if (Input.equals("clear"))
			{
				f.clear();
			}
			else if (Input.equals("help"))
			{
				f.help();
			}
			else if (Input.equals("args"))
			{
				f.args();
			}
			else if (Input.equals("date"))
			{
				f.date();
			}
			else if (Input.equals("?"))
			{
				f.funcHelp();
			}
			else if (Input.equals(">"))// >
			{
				f.opereator();
			}
			else if (Input.equals(">>")) // >> 
			{
				f.opereator2();
			}
			else if (Input.contains("|"))  // |
			{
				System.out.println("Enter the commends u want ..");
				// pwd | Ls | cd |
				Scanner s = new Scanner(System.in);
				String input = s.nextLine();
				/*for (int i = 0;i<input.length();i++)
				{
					String commend = "" ;
					while (input.charAt(i)!='|')
					{
						commend+=input.charAt(i);
						i++;
					}
					if (commend.equals("pwd"))
					{
						f.Pwd();
					}
					else if (commend.equals("ls"))
					{
						f.Ls();
					}
					else if (commend.equals("cd"))
					{
						f.Cd();
					}
					else if (commend.equals("mkdir"))
					{
						f.mkdir();
					}
					else if (commend.equals("rmdir"))
					{
						f.rmdir();
					}
					else if (commend.equals("cat"))
					{
						f.Cat();
					}
					else if (commend.equals("more"))
					{
						f.more();
					}
					else if (commend.equals("clear"))
					{
						f.clear();
					}
					else if (commend.equals("help"))
					{
						f.help();
					}
					else if (commend.equals("args"))
					{
						f.args();
					}
					else if (commend.equals("date"))
					{
						f.date();
					}
					else if (commend.equals("?"))
					{
						f.funcHelp();
					}
					else if (commend.equals(">"))// >
					{
						f.opereator();
					}
					else if (commend.equals(">>")) // >> 
					{
						f.opereator2();
					}	
					else if (commend.equals("cp")) // cp
					{
						f.cp();
					}
					else if (commend.equals("mv")) // mv
					{
						f.mv();
					}
					else if (commend.equals("rm")) //rm
					{
						f.rm();
					}
				}*/
			}
			else if (Input.equals("cp")) // cp
			{
				f.cp();
			}
			else if (Input.equals("mv")) // mv
			{
				f.mv();
			}
			else if (Input.equals("rm")) //rm
			{
				f.rm();
			}
			else if (Input.equals("E"))
			{
				System.exit(0);
			}
			else 
			{
				System.out.println("error , commend wasn't exist");
			}
		}
		
	}

}
