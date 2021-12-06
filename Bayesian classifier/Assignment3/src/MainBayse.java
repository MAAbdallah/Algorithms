
import java.io.*;
import java.util.Vector;

public class MainBayse {
    //public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\assignment3\\test1.csv";
    public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\assignment3\\car.data.csv";
    public static Objects objects = new Objects();
    public static Objects test = new Objects();
    public static Vector<Data> traning = new Vector<>();
    public static Vector<ClassData> ClassV = new Vector<>();
    public static double accurcy =0.0;
    public static long duration = 0;

    public MainBayse() {
        long startTime = System.nanoTime();
        ExcelReader();
        beforeProcess();
        Process();
        AfterProcess();
        long endTime = System.nanoTime();
         duration = (endTime - startTime);

    }

    public static void beforeProcess()
    {
        int counter =0;
        int sum = objects.CarAcceptability.size();
        for(int i=0;i<objects.CarAcceptability.size();i++)
        {
            String ClassType = objects.CarAcceptability.get(i);
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
        for(int i=0;i<objects.buyingPrice.size();i++)
        {
            String type = objects.buyingPrice.get(i);
            String ClassType = objects.CarAcceptability.get(i);
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
        for(int i=0;i<objects.MaintenancePrice.size();i++)
        {
            String type = objects.MaintenancePrice.get(i);
            String ClassType = objects.CarAcceptability.get(i);
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
        for(int i=0;i<objects.NumOfDoors.size();i++)
        {
            String type = objects.NumOfDoors.get(i);
            String ClassType = objects.CarAcceptability.get(i);
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
        for(int i=0;i<objects.CapacityInTerms.size();i++)
        {
            String type = objects.CapacityInTerms.get(i);
            String ClassType = objects.CarAcceptability.get(i);
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
        for(int i=0;i<objects.SizeOfLuggage.size();i++)
        {
            String type = objects.SizeOfLuggage.get(i);
            String ClassType = objects.CarAcceptability.get(i);
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
        for(int i=0;i<objects.EstimatedSafety.size();i++)
        {
            String type = objects.EstimatedSafety.get(i);
            String ClassType = objects.CarAcceptability.get(i);
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
    public static void AfterProcess()
    {
        for (int index = 0;index<test.CarAcceptability.size();index++)
        {
            String s1 = test.buyingPrice.get(index);
            String s2=test.MaintenancePrice.get(index);
            String s3=test.NumOfDoors.get(index);
            String s4=test.CapacityInTerms.get(index);
            String s5=test.SizeOfLuggage.get(index);
            String s6=test.EstimatedSafety.get(index);
            String c = test.CarAcceptability.get(index);
            for(int i=0;i<ClassV.size();i++)
            {
                for(int j=0;j<traning.size();j++)
                {
                    String dataType = traning.get(j).dataType;
                    if(traning.get(j).Nclass.equals(ClassV.get(i).ClassName)&&(dataType.equals(s1)||dataType.equals(s2)|| dataType.equals(s3)||
                            dataType.equals(s4)|| dataType.equals(s5)|| dataType.equals(s6)))
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
            for(int i =0;i<ClassV.size();i++)
            {
                ClassV.get(i).value=ClassV.get(i).prob;
            }
            if(ClassN.equals(c))
            {
                accurcy++;
            }
            System.out.println("index : "+index+" class name : "+ClassN+" value : "+Max);
        }
        accurcy = (accurcy/(double) test.CarAcceptability.size())*100 ;
        System.out.println("accurcy : "+accurcy);
    }

    public static void ExcelReader()
    {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(FN));
            int counter = (int) (1728*0.75);
            int Line = 0;
            while ((line = br.readLine()) != null) {
                if(Line<counter) {
                    // use comma as separator
                    String[] country = line.split(cvsSplitBy);
                    //System.out.println(line);
                    objects.buyingPrice.add(country[0]);
                    objects.MaintenancePrice.add(country[1]);
                    objects.NumOfDoors.add(country[2]);
                    objects.CapacityInTerms.add(country[3]);
                    objects.SizeOfLuggage.add(country[4]);
                    objects.EstimatedSafety.add(country[5]);
                    objects.CarAcceptability.add(country[6]);
                    Line++;
                }
                else
                {
                    String[] country = line.split(cvsSplitBy);
                    //System.out.println(line);
                    test.buyingPrice.add(country[0]);
                    test.MaintenancePrice.add(country[1]);
                    test.NumOfDoors.add(country[2]);
                    test.CapacityInTerms.add(country[3]);
                    test.SizeOfLuggage.add(country[4]);
                    test.EstimatedSafety.add(country[5]);
                    test.CarAcceptability.add(country[6]);
                }
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
