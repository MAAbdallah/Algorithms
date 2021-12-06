package memory;

public class Nodes {
  
     public int start_index;
    public int size;
    public int allocated_space;  
    public boolean status;

    public void print() {
        System.out.println("the starting index : " + start_index + " ,"
                             + " partition size : " + size + " , allocated space : " 
                             + allocated_space + " , status : " + status);
    }

}
