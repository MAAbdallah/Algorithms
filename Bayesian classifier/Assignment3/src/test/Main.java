package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Main {
    public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\assignment3\\test1.csv";
    //public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\assignment3\\car.data.csv";
    public static Objects objects = new Objects();
    public static Vector<Data> traning = new Vector<>();
    //public static Vector<Data> test = new Vector<>();
    public static Vector<ClassData> ClassV = new Vector<>();
    public static void main(String[] args) {
        // write your code here
        ExcelReader();
        beforeProcess();
        Process();
        AfterProcess("sunny","cool","high","strong");
    }
    public static void beforeProcess()
    {
        int counter =0;
        int sum = objects.PlayTennis.size();
        for(int i=0;i<objects.PlayTennis.size();i++)
        {
            String ClassType = objects.PlayTennis.get(i);
            int id = CheckClass(ClassType) ;
            if(id!=-1)
            {
                ClassV.get(id).count++;
            }
            else
            {
                ClassData classData = new ClassData();
                classData.id=counter;
                classData.ClassName=ClassType;
                classData.count++;
                ClassV.add(classData);
                counter++;
            }
        }
        for (int i=0;i<ClassV.size();i++)
        {
            ClassV.get(i).prob=(double) ClassV.get(i).count/(double) sum;
            ClassV.get(i).value=ClassV.get(i).prob;
            ClassData data = ClassV.get(i);
            System.out.println("id : "+data.id+"  "+" "+"class : "+data.ClassName+" "+"count : "+data.count+" prob : "+data.prob);
        }
        System.out.println();
    }
    public static int CheckClass(String ClassName)
    {
        for(int i=0;i<ClassV.size();i++)
        {
            if(ClassV.get(i).ClassName.equals(ClassName))
            {
                return ClassV.get(i).id;
            }
        }
        return -1 ;
    }
    public static void Process()
    {
        int counter =0;
        for(int i=0;i<objects.OutLook.size();i++)
        {
            String type = objects.OutLook.get(i);
            String ClassType = objects.PlayTennis.get(i);
            //System.out.println("type "+type+" class "+ClassType);
            int id = CheckRecord(type,ClassType);
            if(id!=-1)
            {
                //System.out.println("id : "+id);
                traning.get(id).count++;
            }
            else
            {
                Data data = new Data();
                data.id=counter;
                //System.out.println("id new : "+data.id);
                data.dataType=type;
                data.Nclass= ClassType;
                data.count++;
                traning.add(data);
                counter++;
            }
        }
        for(int i=0;i<objects.Temperature.size();i++)
        {
            String type = objects.Temperature.get(i);
            String ClassType = objects.PlayTennis.get(i);
            //System.out.println("type "+type+" class "+ClassType);
            int id = CheckRecord(type,ClassType);
            if(id!=-1)
            {
                //System.out.println("id : "+id);
                traning.get(id).count++;
            }
            else
            {
                Data data = new Data();
                data.id=counter;
                //System.out.println("id new : "+data.id);
                data.dataType=type;
                data.Nclass= ClassType;
                data.count++;
                traning.add(data);
                counter++;
            }
        }
        for(int i=0;i<objects.Humiditiy.size();i++)
        {
            String type = objects.Humiditiy.get(i);
            String ClassType = objects.PlayTennis.get(i);
            //System.out.println("type "+type+" class "+ClassType);
            int id = CheckRecord(type,ClassType);
            if(id!=-1)
            {
                //System.out.println("id : "+id);
                traning.get(id).count++;
            }
            else
            {
                Data data = new Data();
                data.id=counter;
                //System.out.println("id new : "+data.id);
                data.dataType=type;
                data.Nclass= ClassType;
                data.count++;
                traning.add(data);
                counter++;
            }
        }
        for(int i=0;i<objects.Wind.size();i++)
        {
            String type = objects.Wind.get(i);
            String ClassType = objects.PlayTennis.get(i);
            //System.out.println("type "+type+" class "+ClassType);
            int id = CheckRecord(type,ClassType);
            if(id!=-1)
            {
                //System.out.println("id : "+id);
                traning.get(id).count++;
            }
            else
            {
                Data data = new Data();
                data.id=counter;
                //System.out.println("id new : "+data.id);
                data.dataType=type;
                data.Nclass= ClassType;
                data.count++;
                traning.add(data);
                counter++;
            }
        }
        for (int i=0;i<traning.size();i++)
        {
            Data data = traning.get(i);
            int count = getcount(data.Nclass);
            traning.get(i).prob=(double) data.count/(double)count;
            data=traning.get(i);
            System.out.println("id : "+data.id+"  "+"type : "+data.dataType+" "+"class : "+data.Nclass+" "+"count : "+data.count+" prob : "+data.prob);
        }
        System.out.println();
    }
    public static int CheckRecord (String type , String Class)
    {
        for (int i=0;i<traning.size();i++)
        {
            Data data = traning.get(i);
            if(data.dataType.equals(type)&&data.Nclass.equals(Class))
            {
                return data.id;
            }
        }
        return -1;
    }
    public static int getcount(String ClassN)
    {
        for(int i =0;i<ClassV.size();i++)
        {
            if(ClassV.get(i).ClassName.equals(ClassN))
            {
                return ClassV.get(i).count ;
            }
        }
        return 0;
    }
    public static void AfterProcess(String s1,String s2,String s3,String s4)
    {
        for(int i=0;i<ClassV.size();i++)
        {
            for(int j=0;j<traning.size();j++)
            {
                if(traning.get(j).Nclass.equals(ClassV.get(i).ClassName)&&(traning.get(j).dataType.equals(s1)||traning.get(j).dataType.equals(s2)||
                traning.get(j).dataType.equals(s3)||traning.get(j).dataType.equals(s4)))
                {
                    ClassV.get(i).value*=traning.get(j).prob;
                }
            }
            //System.out.println("class name : "+ClassV.get(i).ClassName+" value : "+ClassV.get(i).value);
        }
        double Max=ClassV.get(0).value;
        String ClassN =ClassV.get(0).ClassName;
        for(int i=1;i<ClassV.size();i++)
        {
            if(ClassV.get(i).value>Max)
            {
                Max=ClassV.get(i).value;
                ClassN=ClassV.get(i).ClassName;
            }
        }
        System.out.println("class name : "+ClassN+" value : "+Max);
    }

    public static void ExcelReader()
    {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(FN));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                //System.out.println(line);
                objects.OutLook.add(country[0]);
                objects.Temperature.add(country[1]);
                objects.Humiditiy.add(country[2]);
                objects.Wind.add(country[3]);
                objects.PlayTennis.add(country[4]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
