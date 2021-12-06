package memory;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
       
        
Memory mem = new Memory();
		ArrayList<Nodes> memory = new ArrayList<Nodes>();
		Scanner inpt = new Scanner(System.in);
		System.out.print("Enter memory size : ");
		int memorySize = inpt.nextInt();

		int count = 1;
		for (int i = 0; i < memorySize; i++) {
			System.out.print("Size of partion " + (i + 1) + " : ");
			Nodes node = new Nodes();
			node.start_index = count;
			node.allocated_space = 0;
			node.size = inpt.nextInt();
			node.status = true;
			count += node.size;
			memory.add(node);

		}
		String string = "";
		System.out.println("(A) Allocation");
                System.out.println("(D) Deallocation");
		System.out.println("(F) Defragmentation");
		System.out.println("(S) Show memory status");
                System.out.println("(Q) Quit");
		System.out.print("choose a function to do : ");
		Scanner inpt2 = new Scanner(System.in);
		string = inpt2.nextLine();
		while (!string.equals("q") && !string.equals("Q")) {
			if (string.equals("s") || string.equals("S")) {
				mem.show_memory(memory);
			} else if (string.equals("a") || string.equals("A")) {
				System.out.print("Enter the process size : ");
				int prcs_sze = inpt.nextInt();
				System.out.println("1-Best Fit    ");
				System.out.println("2-Worst Fit  ");
				System.out.println("3-First Fit  ");
				int choice = inpt.nextInt();
				int retrn = 0;
				if (choice == 1) {
					retrn = mem.best_fit(memory, prcs_sze);
				} else if (choice == 2) {
					retrn = mem.worst_fit(memory, prcs_sze);
				} else if (choice == 3) {
					retrn = mem.first_fit(memory, prcs_sze);
				}
				if (retrn != -1) {
					mem.memory_defragment(memory, 1);
					System.out.println(
							"The process is allocated in adderess " 
                                                         + retrn + " and type 1 fragmentation done");
				} else {
					System.out.println("The memory is full and the process cann't be allocated");
				}

			}

			else if (string.equals("d") || string.equals("D")) {
				System.out.println("Enter the addrees : ");
				int add = inpt.nextInt();
				boolean fl = mem.memory_deallocate(memory, add);
				if (fl == true) {
					System.out.println("The process has been deallocated successfully "
                                                            + "and type 2 fragmentation done");
					mem.memory_defragment(memory, 2);

				} else {
					System.out.println("The address you entered is not found in the memory");
				}

			} else if (string.contains("f") || string.equals("F")) {
				System.out.println("1- spliting");
				System.out.println("2- mearge contiouse  ");
				System.out.println("3-non contiouse  ");
				int choice = inpt.nextInt();
				if (choice == 1) {
					mem.memory_defragment(memory, 1);
				} else if (choice == 2) {
					mem.memory_defragment(memory, 2);
				} else if (choice == 3) {
					mem.memory_defragment(memory, 3);
				}
			}
			System.out.println("(A) Allocation");
			System.out.println("(D) Deallocation");
			System.out.println("(F) Defragmentation");
			System.out.println("(S) Show memory status");
                        System.out.println("(Q) Quit");

			System.out.println("Choose a function to do : ");
			string = inpt2.nextLine();
		}
	}
    }
    

