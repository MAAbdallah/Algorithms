package memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Memory {
  public static void show_memory(ArrayList<Nodes> x) {
		for (int i = 0; i < x.size(); i++) {
			System.out.println("Partiation number " + (i + 1) + " : ");
			x.get(i).print();
			System.out.println();
		}
	}

	public static int first_fit(ArrayList<Nodes> x, int size) {
		for (int i = 0; i < x.size(); i++) {
			if (x.get(i).size >= size && x.get(i).status == true) {
				x.get(i).status = false;
				x.get(i).allocated_space = size;
				return x.get(i).start_index;
			}
		}
		return -1;
	}

	public static int best_fit(ArrayList<Nodes> x, int size) {
		int min_size = 9999999;
		int indx = -1;
		for (int i = 0; i < x.size(); i++) {
			if (x.get(i).size >= size && x.get(i).status == true && x.get(i).size < min_size) {
				min_size = x.get(i).size;
				indx = i;
			}
		}
		if (indx != -1) {
			x.get(indx).status = false;
			x.get(indx).allocated_space = size;
			return (x.get(indx).start_index);
		}
		return -1;
	}

	public static int worst_fit(ArrayList<Nodes> x, int size) {
		int max_size = 0;
		int indx = -1;
		for (int i = 0; i < x.size(); i++) {
			if (x.get(i).size >= size && x.get(i).status == true && x.get(i).size > max_size) {
				max_size = x.get(i).size;
				indx = i;
			}
		}
		if (indx != -1) {
			x.get(indx).status = false;
			x.get(indx).allocated_space = size;
			return x.get(indx).start_index;
		}
		return -1;
	}

	public static boolean memory_deallocate(ArrayList<Nodes> x, int s) {
		for (int i = 0; i < x.size(); i++) {
			if (x.get(i).start_index == s) {
				x.get(i).allocated_space = 0;
				x.get(i).status = true;
				return true;
			}
		}
		return false;
	}

	public static void memory_defragment(ArrayList<Nodes> x, int C) {
		if (C == 1) {
			for (int i = 0; i < x.size(); i++) {
				if (x.get(i).size > x.get(i).allocated_space && x.get(i).status == false) {
					Nodes temp = new Nodes();
					temp.start_index = x.get(i).start_index + x.get(i).allocated_space;
					temp.size = x.get(i).size - x.get(i).allocated_space;
					temp.status = true;
					temp.allocated_space = 0;
					x.get(i).size = x.get(i).allocated_space;
					x.add(i + 1, temp);
				}
			}
		} else if (C == 2) {
			for (int i = 0; i < x.size(); i++) {
				if (x.get(i).status == true && i != x.size() - 1 && x.get(i + 1).status == true) {
					x.get(i).size = x.get(i).size + x.get(i + 1).size;
					x.remove(i + 1);
				}
			}
		} else if (C == 3) {
			memory_defragment(x, 1);
			int holespace = 0;
			int count = 1;
			for (int i = 0; i < x.size(); i++) {
				if (x.get(i).status == true) {
					holespace += x.get(i).size;
					x.remove(i);
					i--;
				} else {
					if (i != 0) {
						count += x.get(i - 1).size;
					}
					x.get(i).start_index = count;
				}
			}
			Nodes temp = new Nodes();
			temp.start_index = count;
			temp.size = holespace;
			temp.status = true;
			temp.allocated_space = 0;
			x.add(temp);
		}

		Collections.sort(x, new Comparator<Nodes>() {
			public int compare(Nodes t1, Nodes t2) 
			{
				return (int) (t1.start_index - t2.start_index);
			}

		});
	}  

}
