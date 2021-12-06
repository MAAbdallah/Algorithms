package second_try;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class NewMain {
    public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\DMAssignment1\\test.xlsx";
    public static ArrayList<Data> OrgnalData = new ArrayList();
    public static ArrayList<Data> FirstData = new ArrayList();
    public static ArrayList<Data> OldSubData = new ArrayList();
    public static ArrayList<Data> NewSubData = new ArrayList();
    public static ArrayList<Asscion> asscions = new ArrayList<>();
    public static int minSup =0;
    public static int minCoff =0;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        minSup = scanner.nextInt();
        minCoff = scanner.nextInt();
        /*ExcelReader();
        Print(OrgnalData);
        FirstItemSet();
        Print(OldSubData);
        //System.out.println(Union());
        int len = ConvertToArray().length;
        double[] data = new double[len];
        combinationUtil(ConvertToArray(),data,0,len-1,0,3);
        Print(NewSubData);*/
        Process();
    }
    public static void Print(ArrayList<Data> data)
    {
        for(int i=0;i<data.size();i++)
        {
            System.out.println(data.get(i).id+"  "+data.get(i).items+"  "+data.get(i).Freq);
        }
    }
    public static void filterSet(ArrayList<Data> data)
    {
        for(int i=0;i<data.size();i++)
        {
            if(data.get(i).Freq < minSup)
            {
                data.remove(i);
                i--;
            }
        }
    }
    public static void FirstItemSet()
    {
        for (int i=0;i<OrgnalData.size();i++)
        {
            Iterator<Double> itera = OrgnalData.get(i).items.iterator();
            while (itera.hasNext())
            {
                Set<Double> temp = new HashSet<>();
                temp.add(itera.next());
                //System.out.println(temp);
                boolean check = Check(temp);
                if(check==false)
                {
                    Data data= new Data();
                    data.items=temp;
                    data.Freq=1;
                    OldSubData.add(data);
                }
                else
                {
                    for(int j=0;j<OldSubData.size();j++)
                    {
                        if(OldSubData.get(j).items.equals(temp))
                        {
                            OldSubData.get(j).Freq++;
                        }
                    }
                }
            }
        }
        filterSet(OldSubData);
    }
    public static void Process()
    {
        //step 0
        ExcelReader();
        System.out.println("Orgnal data");
        Print(OrgnalData);
        // step 1
        FirstItemSet();
        System.out.println("First set item");
        Print(OldSubData);
        FirstData.addAll(OldSubData);
        // step 2
        int i =1;
        while (true) {
            System.out.println("union "+Union());
            int len = ConvertToArray(Union()).length;
            double[] data = new double[len];
            combinationUtil(ConvertToArray(Union()), data, 0, len - 1, 0, ++i);
            System.out.println(i+" set items");
            Print(NewSubData);
            CalFreq();
            System.out.println(i+" set items after get freq");
            Print(NewSubData);
            filterSet(NewSubData);
            System.out.println(i+" set items after filteration");
            Print(NewSubData);
            // step 3
            if(NewSubData.size()==0)
            {
                //assision
                System.out.println("assision");
                Assision();
                break;
            }
            else
            {
                System.out.println("get new item set ");
                OldSubData.clear();
                OldSubData.addAll(NewSubData);
                NewSubData.clear();
            }
        }
        //Print(OldSubData);
        //Print(NewSubData);
    }
    public static void Assision()
    {
        for(int i=0;i<OldSubData.size();i++)
        {
            //System.out.println(i+" "+  ConvertToArray(OldSubData.get(i).items)[0]+"  "+ConvertToArray(OldSubData.get(i).items)[1]);
            Asscion asscion1 = new Asscion();
            Asscion asscion2 = new Asscion();
            asscion1.items.add(ConvertToArray(OldSubData.get(i).items)[0]);
            asscion1.items.add(ConvertToArray(OldSubData.get(i).items)[1]);
            asscion2.items.add(ConvertToArray(OldSubData.get(i).items)[1]);
            asscion2.items.add(ConvertToArray(OldSubData.get(i).items)[0]);
            asscions.add(asscion1);
            asscions.add(asscion2);
        }
        for(int j =0;j<asscions.size();j++)
        {
            System.out.println(asscions.get(j).items+"  ");
        }
        CalcFreqAssision();
        FilterAssicion();
        StrongAssicion();
    }
    public static void CalcFreqAssision()
    {
        System.out.println("After calc freq and value of assicion");
        for(int i=0;i<asscions.size();i++)
        {
            asscions.get(i).firstFreq=getFreqFirst(asscions.get(i).items.iterator().next());
            asscions.get(i).AllFrq=getFreqOld(asscions.get(i).items);
            asscions.get(i).value=(double) asscions.get(i).AllFrq/(double) asscions.get(i).firstFreq*100;
            System.out.println(asscions.get(i).items+"  "+asscions.get(i).firstFreq+"  "+asscions.get(i).AllFrq+"  "+asscions.get(i).value);
        }
    }
    public static void FilterAssicion()
    {
        for(int i=0;i<asscions.size();i++)
        {
            if(asscions.get(i).value < minCoff)
            {
                asscions.remove(i);
                i--;
            }
        }
    }
    public static void StrongAssicion()
    {
        System.out.println("After filtration assicion");
        for(int i=0;i<asscions.size();i++)
        {
            System.out.println(asscions.get(i).items+" "+asscions.get(i).value);
        }
    }
    public static int getFreqFirst(double temp)
    {
            for(int i=0;i<FirstData.size();i++)
            {
               /* Set<Double> intersection = new HashSet<>(NewSubData.get(i).items);
               intersection.retainAll(OrgnalData.get(j).items);
                 //System.out.println(intersection);*/
                if(FirstData.get(i).items.iterator().next().equals(temp))
                {
                    return FirstData.get(i).Freq;
                }
            }
            return -1;
    }
    public static int getFreqOld(Set<Double> temp)
    {
        for(int i=0;i<OldSubData.size();i++)
        {
               /* Set<Double> intersection = new HashSet<>(NewSubData.get(i).items);
               intersection.retainAll(OrgnalData.get(j).items);
                 //System.out.println(intersection);*/
            if(OldSubData.get(i).items.equals(temp))
            {
                return OldSubData.get(i).Freq;
            }
        }
        return -1;
    }

    public static void CalFreq()
    {
        for (int i=0;i<NewSubData.size();i++)
        {
            for(int j=1;j<OrgnalData.size();j++)
            {
               /* Set<Double> intersection = new HashSet<>(NewSubData.get(i).items);
               intersection.retainAll(OrgnalData.get(j).items);
                 //System.out.println(intersection);*/
                if(OrgnalData.get(j).items.containsAll(NewSubData.get(i).items))
                {
                    NewSubData.get(i).Freq++;
                }
            }
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
    public static double [] ConvertToArray(Set<Double> temp)
    {
        double[] array = new double[temp.size()];
        int i = 0;
        for (double s: temp) {
            array[i++] = s;
        }
        return array;
    }
    public static void combinationUtil(double arr[], double data[], int start,
                                       int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            Data d = new Data();
            for (int j=0; j<r; j++) {
                //System.out.print(data[j] + " ");
                d.items.add(data[j]);
            }
            NewSubData.add(d);
            //System.out.println("");
            return  ;
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
   /* public static void combinationUtil(int bits)
    {
        for(int i=0;i<OldSubData.size();i++)
        {
            Data data = new Data();
            int counter = 1;
            data.items.addAll(OldSubData.get(i).items);
            for(int j=i+1;j<(OldSubData.size());j++)
            {
                data.items.addAll(OldSubData.get(j).items);
                counter++;
                if(j==OldSubData.size()-1&&counter<bits)
                {
                    data.items.addAll(OldSubData.get(j-bits).items);
                }
                if(counter==bits)
                {
                    NewSubData.add(data);
                    data = new Data();
                    data.items.addAll(OldSubData.get(i).items);
                    counter=1;
                    for(int L = j-1;L<bits-1;L++)
                    {
                         boolean check = data.items.addAll(OldSubData.get(L).items);
                        if(check){counter++;}
                    }
                }
            }
            data.items.addAll(OldSubData.get(OldSubData.size()-1).items);
        }
    }*/

    public static boolean Check(Set<Double> item)
    {
        for (int i=0;i<OldSubData.size();i++)
        {
            if(OldSubData.get(i).items.equals(item))
            {
                return true;
            }
        }
        return false;
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
                Data data = new Data();
                data.id=i++;
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch(cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            //System.out.print(cell.getNumericCellValue() + "\t\t");
                            if(cell.getRowIndex()==0||cell.getColumnIndex()==0)
                            {
                                break;
                            }
                            data.items.add(cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            //System.out.print(cell.getStringCellValue() + "\t\t");
                            if(cell.getRowIndex()==0||cell.getColumnIndex()==0)
                            {
                                break;
                            }
                            //data.items.add(cell.getStringCellValue());
                            break;
                    }
                    if(!cellIterator.hasNext())
                    {
                        OrgnalData.add(data);
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
