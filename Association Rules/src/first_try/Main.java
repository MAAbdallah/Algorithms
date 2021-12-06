package first_try;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import second_try.Data;

import java.io.*;
import java.util.*;


public class Main {
    public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\DMAssignment1\\test.xlsx";
    public static ArrayList<Item> arrayList = new ArrayList();
    public static int minSub =0;
    public static ArrayList<Data> OldSubData = new ArrayList();
    public static ArrayList<Data> NewSubData = new ArrayList();
    public static int minCoff =0;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        // minSub = scanner.nextInt();
         //minCoff = scanner.nextInt()/100;
       /* ExcelReader();
        print();
        UpdateList();
        print();*/
       for(int i =0;i<5;i++)
       {
           Data data = new Data();
           data.items.add((double) i+1);
           OldSubData.add(data);
       }
        double[] array = new double[Union().size()];
        int i = 0;
        for (double s: Union())
            array[i++] = s;
        double[] data = new double[array.length];
       combinationUtil(array,data,0,array.length-1,0,3);
        Print(NewSubData);
    }
    public static void Print(ArrayList<Data> data)
    {
        for(int i=0;i<data.size();i++)
        {
            System.out.println(data.get(i).id+"  "+data.get(i).items+"  "+data.get(i).Freq);
        }
    }
     public static Set<Double> Union()
    {
        Set<Double> temp = new HashSet<>();
        for(int i =0;i<OldSubData.size();i++)
        {
            temp.addAll(OldSubData.get(i).items);
        }
        return temp;
    }
    public static void combinationUtil(double arr[], double data[], int start,
                                int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            for (int j=0; j<r; j++)
                System.out.print(data[j]+" ");
            System.out.println("");
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
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

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    Item item = new Item();
                    switch(cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            if(cell.getRowIndex()==0||cell.getColumnIndex()==0)
                            {
                                break;
                            }
                            item.Sitem= String.valueOf(cell.getNumericCellValue());
                            item.rowIndex=cell.getRowIndex();
                            checkInArray(item);
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                            if(cell.getRowIndex()==0||cell.getColumnIndex()==0)
                            {
                                break;
                            }
                            item.Sitem=cell.getStringCellValue();
                            item.rowIndex=cell.getRowIndex();
                            checkInArray(item);
                            break;
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
    public static boolean checkInArray(Item item)
    {
        for(int i =0;i<arrayList.size();i++)
        {
            if(item.Sitem.equals(arrayList.get(i).Sitem))
            {
                if(item.rowIndex!=arrayList.get(i).rowIndex)
                {
                    arrayList.get(i).count+=1;
                    arrayList.get(i).rowIndex=item.rowIndex;
                    return true;
                }
                else
                {
                    return true;
                }
            }
        }
        item.count+=1;
        arrayList.add(item);
        return false ;
    }
    public static void UpdateList()
    {
        for(int i=0;i<arrayList.size();i++)
        {
            if(arrayList.get(i).count<minSub)
            {
                arrayList.remove(i);
            }
        }
    }
    public static void print ()
    {
        int count = 0;
        for(int i =0;i<arrayList.size();i++)
        {
            count++;
            System.out.print(arrayList.get(i).Sitem+" "+arrayList.get(i).count+"\t\t");
            if(count==5)
            {count=0;System.out.println();}
        }
        System.out.println();
    }
}
