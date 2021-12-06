import java.util.*;
//static Queue<String> customequeue = new LinkedList<String>();


class node
{
    String name = "";
    int offset =0;

    public node(String name, int offset) {
        this.name = name;
        this.offset=offset;
    }
}
class Table {
    int Size =0;
    public static Map<Integer,String> onTable = new HashMap<Integer,String>();
    //public static  Vector<node> onTable = new Vector<node>()
    int availability=0;

    public Table(int s) {
        // TODO Auto-generated constructor stub
        Size = s;
        availability = Size ;
        for(int i=1;i<=Size;i++)
        {
            onTable.put(i, null);
        }

    }



    public void Enter (String name) throws InterruptedException {
        if(availability>0) {
            System.out.println(name + " arrive");
           // Thread.sleep(500);
        }
        else
        {
            System.out.println(name + " arrive and wait");
        }
    }


    public synchronized void set(String name) throws InterruptedException{
        if (availability>0)
        {
            //System.out.println(name + " arrive");
            int position = 0;
            for (int i=1;i<=Size;i++)
            {
                if (onTable.get(i)==null)
                {
                    position = i ;
                    break;
                }
            }
            onTable.put(position, name);
            //Thread.sleep(500);
            System.out.println("Table " + position + ": " + name+" sit");
            availability--;
        }
        else
        {
            wait();
            //System.out.println(name + " arrive and wait");
            this.set(name);
        }
    }

    public  void order(String name) throws InterruptedException {
        int position = 0;
        for (int i=1;i<=Size;i++)
        {
            if (name.equals(onTable.get(i)))
            {
                position = i ;
                break;
            }
        }
        //Thread.sleep(500);
        System.out.println("Table " + position + ": " + name+" order");
    }

    public  void eat(String name) throws InterruptedException {
        int position = 0;
        for (int i=1;i<=Size;i++)
        {
            if (name.equals(onTable.get(i)))
            {
                position = i ;
                break;
            }
        }
        //Thread.sleep(500);
        System.out.println("Table " + position + ": " + name+" eat");
    }


    public synchronized void Leave(String name) throws InterruptedException {
        int position = 0;
        for (int i=1;i<=Size;i++)
        {
            if (name.equals(onTable.get(i)))
            {
                position = i ;
                break;
            }
        }
        //Thread.sleep(500);
        System.out.println("Table " + position + ": " + name+" leave");
        onTable.put(position, null);
        availability++;
        notify();
    }
}

class Customer extends Thread
{
    String name ="";
    Table t ;
    public Customer(Table t,String name) {
        this.t=t;
        this.name=name;
    }
    public void run ()
    {
        /*try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        try {
            t.Enter(name);
            t.set(name);
            t.order(name);
            t.eat(name);
            t.Leave(name);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

public class Resturant {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int numTables =0;
        Scanner s0 = new Scanner(System.in);
        System.out.println("Enter num of tables : ");
        numTables=s0.nextInt();
        Table t = new Table(numTables);
        /*Customer c1 = new Customer(t,"Mohammed");
        Customer c2 = new Customer(t,"Aya");
        Customer c3 = new Customer(t,"Hidy");
        Customer c4 = new Customer(t,"Shimaa");
        Customer c5 = new Customer(t,"Mahmoud");
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();*/
        int Size =0;
        Scanner s1 = new Scanner(System.in);
        System.out.println("Enter total num of people : ");
        Size=s1.nextInt();
        Thread[] Array = new Thread[Size];
        for(int i=0;i<Size;i++)
        {
            Scanner s2 = new Scanner(System.in);
            System.out.println("Enter name the customer num "+(i+1)+" : ");
            String name = s2.nextLine();
             Array[i] = new Thread(new Customer(t,name));
        }
        for(int i=0;i<Size;i++)
        {
            Array[i].start();
        }


    }

}

