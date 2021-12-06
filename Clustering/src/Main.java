import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    //public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\Assignment2\\test1.xlsx";
    public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\Assignment2\\Course Evaluation .xlsx";
    public static int k=0;
    public static Vector<Node> orgnalData = new Vector<>();
    public static Vector<Centroid> oldCentroid = new Vector<>();
    public static Vector<Centroid> newCentroid = new Vector<>();
    public static double OutLayer = 0.0 ;
    public static void main(String[] args) {
	// write your code here
        ExcelReader();
        System.out.println("Enter K : ");
        Scanner scanner = new Scanner(System.in) ;
        k = scanner.nextInt();
        while (k>orgnalData.size())
        {
            System.out.println("please enter correct k , u can't enter greater than num of nodes .. ");
            k = scanner.nextInt();
        }
        System.out.println("Enter outlayer Value : ");
        OutLayer = scanner.nextDouble();
        prosses();
    }
    // Euclidean distance
    public static double CalcDistance(Node n1,Node n2)
    {
        double sum =0;
        for(int i=0;i<n1.myV.size();i++)
        {
            double x = n1.myV.get(i);
            double y = n2.myV.get(i);
            double subtraction = y - x ;
            double square = Math.pow((subtraction),2);
            sum+=square;
        }
        double distance = Math.sqrt(sum);
        return distance;
    }
    public static void prosses ()
    {
        for(int i =0;i<k;i++)
        {
            Random random = new Random();
            Centroid centroid = new Centroid();
            centroid.node = orgnalData.get(random.nextInt(orgnalData.size()));
            if(check(centroid))
            {
                centroid.id=i;
                oldCentroid.add(centroid);
            }
            else
            {
                i--;
            }
        }
        /*Centroid centroid = new Centroid();
        centroid.node=orgnalData.get(0);
        centroid.id=0;
        oldCentroid.add(centroid);
        Centroid centroid1 = new Centroid();
        centroid1.node=orgnalData.get(3);
        centroid1.id=1;
        oldCentroid.add(centroid1);*/

        updateCentroid(oldCentroid);
        System.out.println("old");
        printCentroid(oldCentroid);
        CalcMean();
        updateCentroid(newCentroid);
        System.out.println("new");
        printCentroid(newCentroid);

        while (true)
        {
            boolean check = checkCentroid();
            if(check==true)
            {
                System.out.println("stop");
                return;
            }
            else {
                oldCentroid.clear();
                oldCentroid.addAll(newCentroid);
                newCentroid.clear();
                System.out.println("old");
                printCentroid(oldCentroid);
                CalcMean();
                updateCentroid(newCentroid);
                System.out.println("new");
                printCentroid(newCentroid);
            }
        }
    }
    public static void updateCentroid(Vector<Centroid> v)
    {
        for(int i =0;i<orgnalData.size();i++)
        {
            Node node = orgnalData.get(i);
            for(int j=0;j<v.size();j++)
            {
                Centroid centroid = v.get(j);
                double d = CalcDistance(centroid.node,node);
                //System.out.print("distance : " + d+"  ");
                if(j==0)
                {
                    node.distance = d ;
                    node.centroidID = j;
                }
                else
                {
                    if (d<node.distance) {
                    node.distance = d ;
                    node.centroidID = j;
                    }
                    else { continue; }
                }
            }
            //System.out.println();
            if(node.distance<OutLayer) {
                ADDinCentroid(node, v);
            }
        }
    }
    public static void ADDinCentroid(Node node,Vector<Centroid> v)
    {
        for(int i=0;i<v.size();i++)
        {
            if(node.centroidID==v.get(i).id)
            {
                v.get(i).nodes.add(node);
                break;
            }
        }
    }
    public static boolean checkCentroid()
    {
        for(int i=0;i<newCentroid.size();i++)
        {
            Centroid centroid = newCentroid.get(i);
            Centroid centroid1 = oldCentroid.get(i);
            if(centroid.nodes.size()!=centroid1.nodes.size())
            {
                System.out.println("not same size");
                return false;
            }
            else {
                for (int j = 0; j < centroid.nodes.size(); j++) {
                    if (centroid.nodes.get(j).id != centroid1.nodes.get(j).id) {
                        System.out.println("not same nodes ");
                        return false;
                    }
                }
            }
        }
        return  true ;
    }
    public static void CalcMean()
    {
        int size = oldCentroid.get(0).nodes.get(0).myV.size();
        //System.out.println("size of column "+size);
        for(int i=0;i<oldCentroid.size();i++)
        {
            Vector<Double> mean = new Vector<>(size);
            for(int m =0;m<size;m++)
            {
                mean.add(0.0);
            }
            Centroid centroid = oldCentroid.get(i);
            int Size = centroid.nodes.size();
            //System.out.println("Size "+Size);
            for(int j=0;j<centroid.nodes.size();j++)
            {
                Node node = centroid.nodes.get(j);
                for(int q=0;q<node.myV.size();q++)
                {
                    //System.out.print("old "+mean.get(q)+" now "+node.myV.get(q)+" index "+q);
                    mean.set(q,node.myV.get(q)+mean.get(q));
                    //System.out.println("  "+mean.get(q));
                }
            }

            Centroid centroid1 = new Centroid();
            for(int q = 0;q<mean.size();q++)
            {
                centroid1.node.myV.add(mean.get(q)/(double)Size);
            }
            centroid1.id=i;
            newCentroid.add(centroid1);
            mean.clear();
        }
    }
    public static boolean check (Centroid centroid)
    {
        for(int i=0;i<oldCentroid.size();i++)
        {
            if(oldCentroid.get(i).node.id==centroid.node.id)
            {
                return false;
            }
        }
        return true ;
    }
    public static  void printNodes(Vector<Node> vector)
    {
        for (int i =0;i<vector.size();i++)
        {
            System.out.print("node id : "+vector.get(i).id+" it's values ");

            for(int j=0;j<vector.get(i).myV.size();j++)
            {
                System.out.print(vector.get(i).myV.get(j)+"  ");
            }
            System.out.println();
        }
    }
    public static void printCentroid(Vector<Centroid> vector)
    {
        for(int i=0;i<vector.size();i++)
        {
            System.out.print("centroid id : "+vector.get(i).id+" node id : "+vector.get(i).node.id+" it's values ");
            printNode(vector.get(i).node);
            printNodes(vector.get(i).nodes);
        }
    }
    public static void printNode(Node node)
    {
        for(int j=0;j<node.myV.size();j++)
        {
            System.out.print(node.myV.get(j)+"  ");
        }
        System.out.println();
    }

    public static void ExcelReader()
    {
        try {
            FileInputStream file = new FileInputStream(new File(FN));

            //Get the workbook instance for XLS file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                Node node = new Node();
                node.id=i++;
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch(cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            if(cell.getRowIndex()==0||cell.getColumnIndex()==0)
                            {
                                break;
                            }
                            node.myV.add(cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                            break;
                    }
                    if(!cellIterator.hasNext()&&node.myV.size()!=0)
                    {
                        orgnalData.add(node);
                    }
                }
                System.out.println("");
            }
            file.close();
            FileOutputStream out =
                    new FileOutputStream(new File(FN));
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
