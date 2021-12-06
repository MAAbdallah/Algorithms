import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class MainKnear {

    //public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\assignment3\\test1.csv";
    public static String FN = "G:\\FCI\\forth year\\IS421 - Data Mining\\Assignments\\assignment3\\car.data.csv";
    public static Objects objects = new Objects();
    public static ObjectsKn objectsK = new ObjectsKn();
    public static Objects test = new Objects();
    public static ObjectsKn testK = new ObjectsKn();
    public static Vector<ObjectsKnRows> objectsRow = new Vector<>();
    public static Vector<ObjectsKnRows> testRow = new Vector<>();
    public static Vector<ObjectsKnRows> selected = new Vector<>();
    public static Vector<KnearNode> distanceNode = new Vector<>();
    public static Vector<ClassData> ClassV = new Vector<>();

    public static int k = 0;
    public static double accurcy =0.0;
    public static long duration = 0;


    public MainKnear() {
        long startTime = System.nanoTime();

        ExcelReader();
        Convert();
        beforeProcess();
        System.out.println("enter K : ");
        Scanner scanner = new Scanner(System.in);
        k  = scanner.nextInt();
        process();

        long endTime = System.nanoTime();
        duration = (endTime - startTime);

    }

    public static void beforeProcess()
    {
        int counter =0;
        for(int i=0;i<objectsK.CarAcceptability.size();i++)
        {
            ObjectsKnRows obj = new ObjectsKnRows();
            obj.id = counter++; ;
            obj.buyingPrice = objectsK.buyingPrice.get(i);
            obj.MaintenancePrice = objectsK.MaintenancePrice.get(i);
            obj.NumOfDoors = objectsK.NumOfDoors.get(i);
            obj.CapacityInTerms = objectsK.CapacityInTerms.get(i);
            obj.SizeOfLuggage = objectsK.SizeOfLuggage.get(i);
            obj.EstimatedSafety = objectsK.EstimatedSafety.get(i);
            obj.v.add(obj.buyingPrice); obj.v.add(obj.MaintenancePrice); obj.v.add(obj.NumOfDoors);
            obj.v.add(obj.CapacityInTerms); obj.v.add(obj.SizeOfLuggage); obj.v.add(obj.EstimatedSafety);
            obj.CarAcceptability = objectsK.CarAcceptability.get(i);
            objectsRow.add(obj);
        }
        System.out.println("rows traning : " +objectsRow.size());

        for(int i=0;i<testK.CarAcceptability.size();i++)
        {
            ObjectsKnRows obj = new ObjectsKnRows();
            obj.id=counter++;
            obj.buyingPrice = testK.buyingPrice.get(i);
            obj.MaintenancePrice = testK.MaintenancePrice.get(i);
            obj.NumOfDoors = testK.NumOfDoors.get(i);
            obj.CapacityInTerms = testK.CapacityInTerms.get(i);
            obj.SizeOfLuggage = testK.SizeOfLuggage.get(i);
            obj.EstimatedSafety = testK.EstimatedSafety.get(i);
            obj.v.add(obj.buyingPrice); obj.v.add(obj.MaintenancePrice); obj.v.add(obj.NumOfDoors);
            obj.v.add(obj.CapacityInTerms); obj.v.add(obj.SizeOfLuggage); obj.v.add(obj.EstimatedSafety);
            obj.CarAcceptability = testK.CarAcceptability.get(i);
            testRow.add(obj);
        }
        System.out.println("rows test : " +testRow.size());
    }
    // Euclidean distance
    public static double CalcDistance(Vector<Integer> v1,Vector<Integer> v2)
    {
        double sum =0;
        for(int i=0;i<v1.size();i++)
        {
            double x = v1.get(i);
            double y = v2.get(i);
            double subtraction = y - x ;
            double square = Math.pow((subtraction),2);
            sum+=square;
        }
        double distance = Math.sqrt(sum);
        return distance;
    }
    public static void process()
    {
        for (int i=0;i<testRow.size();i++)
        {
            ObjectsKnRows obj1 = testRow.get(i);
            for(int j = 0;j<objectsRow.size();j++)
            {
                KnearNode node = new KnearNode();
                ObjectsKnRows obj2 = objectsRow.get(j);
                node.id=obj2.id;
                node.distance = CalcDistance(obj1.v,obj2.v);
                //System.out.println("id "+node.id+"  dis : "+node.distance);
                distanceNode.add(node);
            }
            sort();
            for(int q = 0;q<k;q++)
            {
               ObjectsKnRows objselected =  search(distanceNode.get(q).id);
               if (objselected!=null)
               {
                   selected.add(objselected);
               }
            }
            for(int q = 0;q<selected.size();q++)
            {
                System.out.println("index : "+i+"  "+selected.get(q).id+" "+selected.get(q).CarAcceptability+" dis : "+distanceNode.get(q).distance);
            }
            String name = getClassName();
            ClassV.clear();
            if(name.equals(testRow.get(i).CarAcceptability))
            {
                accurcy++;
            }
            selected.clear();
            distanceNode.clear();
        }
        accurcy = (accurcy/(double) testRow.size())*100 ;
        System.out.println("accurcy : "+accurcy);
    }
    public static void sort()
    {
        int i, j;
        for (i = 0; i < distanceNode.size()-1; i++)
            // Last i elements are already in place
            for (j = 0; j < distanceNode.size()-i-1; j++) {
                if (distanceNode.get(j).distance > distanceNode.get(j + 1).distance)
                {
                    /*KnearNode temp = distanceNode.get(j);
                    distanceNode.set(j,distanceNode.get(j+1));
                    distanceNode.set(j+1 ,temp);*/
                    Collections.swap(distanceNode, j, j+1);
                }
            }
    }
    public static ObjectsKnRows search (int id )
    {
        for (int i=0;i<objectsRow.size();i++)
        {
            if(objectsRow.get(i).id==id)
            {
                return objectsRow.get(i);
            }
        }
        return null ;
    }
    public static String getClassName()
    {
        for (int i=0;i<selected.size();i++)
        {
            int counter =0;
            String ClassN =selected.get(i).CarAcceptability;
            int id = CheckClass(ClassN) ;
            if(id!=-1)
            {
                ClassV.get(id).count++;
            }
            else
            {
                ClassData classData = new ClassData();
                classData.id=counter;
                classData.ClassName=ClassN;
                classData.count++;
                ClassV.add(classData);
                counter++;
            }
        }
        int Max = ClassV.get(0).count;
        String NameClass = ClassV.get(0).ClassName;
        for(int i=1;i<ClassV.size();i++)
        {
            if(ClassV.get(i).count>Max)
            {
                Max = ClassV.get(i).count;
                NameClass = ClassV.get(i).ClassName;
            }
        }
        return NameClass;
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
    public static void Convert()
    {
        for(int i=0;i<objects.buyingPrice.size();i++)
        {
            String temp = objects.buyingPrice.get(i);
            if(temp.equals("vhigh")){objectsK.buyingPrice.add(4);}
            if(temp.equals("high")){objectsK.buyingPrice.add(3);}
            if(temp.equals("med")){objectsK.buyingPrice.add(2);}
            if(temp.equals("low")){objectsK.buyingPrice.add(1);}
        }
        for(int i=0;i<objects.MaintenancePrice.size();i++)
        {
            String temp = objects.MaintenancePrice.get(i);
            if(temp.equals("vhigh")){objectsK.MaintenancePrice.add(4);}
            if(temp.equals("high")){objectsK.MaintenancePrice.add(3);}
            if(temp.equals("med")){objectsK.MaintenancePrice.add(2);}
            if(temp.equals("low")){objectsK.MaintenancePrice.add(1);}
        }
        for(int i=0;i<objects.NumOfDoors.size();i++)
        {
            String temp = objects.NumOfDoors.get(i);
            if(temp.equals("2")){objectsK.NumOfDoors.add(2);}
            if(temp.equals("3")){objectsK.NumOfDoors.add(3);}
            if(temp.equals("4")){objectsK.NumOfDoors.add(4);}
            if(temp.equals("5more")){objectsK.NumOfDoors.add(5);}
        }
        for(int i=0;i<objects.CapacityInTerms.size();i++)
        {
            String temp = objects.CapacityInTerms.get(i);
            if(temp.equals("2")){objectsK.CapacityInTerms.add(2);}
            if(temp.equals("4")){objectsK.CapacityInTerms.add(4);}
            if(temp.equals("more")){objectsK.CapacityInTerms.add(5);}
        }
        for(int i=0;i<objects.SizeOfLuggage.size();i++)
        {
            String temp = objects.SizeOfLuggage.get(i);
            if(temp.equals("small")){objectsK.SizeOfLuggage.add(1);}
            if(temp.equals("med")){objectsK.SizeOfLuggage.add(2);}
            if(temp.equals("big")){objectsK.SizeOfLuggage.add(3);}
        }
        for(int i=0;i<objects.EstimatedSafety.size();i++)
        {
            String temp = objects.EstimatedSafety.get(i);
            if(temp.equals("low")){objectsK.EstimatedSafety.add(1);}
            if(temp.equals("med")){objectsK.EstimatedSafety.add(2);}
            if(temp.equals("high")){objectsK.EstimatedSafety.add(3);}
        }

        for(int i=0;i<test.buyingPrice.size();i++)
        {
            String temp = test.buyingPrice.get(i);
            if(temp.equals("vhigh")){testK.buyingPrice.add(4);}
            if(temp.equals("high")){testK.buyingPrice.add(3);}
            if(temp.equals("med")){testK.buyingPrice.add(2);}
            if(temp.equals("low")){testK.buyingPrice.add(1);}
        }
        for(int i=0;i<test.MaintenancePrice.size();i++)
        {
            String temp = test.MaintenancePrice.get(i);
            if(temp.equals("vhigh")){testK.MaintenancePrice.add(4);}
            if(temp.equals("high")){testK.MaintenancePrice.add(3);}
            if(temp.equals("med")){testK.MaintenancePrice.add(2);}
            if(temp.equals("low")){testK.MaintenancePrice.add(1);}
        }
        for(int i=0;i<test.NumOfDoors.size();i++)
        {
            String temp = test.NumOfDoors.get(i);
            if(temp.equals("2")){testK.NumOfDoors.add(2);}
            if(temp.equals("3")){testK.NumOfDoors.add(3);}
            if(temp.equals("4")){testK.NumOfDoors.add(4);}
            if(temp.equals("5more")){testK.NumOfDoors.add(5);}
        }
        for(int i=0;i<test.CapacityInTerms.size();i++)
        {
            String temp = test.CapacityInTerms.get(i);
            if(temp.equals("2")){testK.CapacityInTerms.add(2);}
            if(temp.equals("4")){testK.CapacityInTerms.add(4);}
            if(temp.equals("more")){testK.CapacityInTerms.add(5);}
        }
        for(int i=0;i<test.SizeOfLuggage.size();i++)
        {
            String temp = test.SizeOfLuggage.get(i);
            if(temp.equals("small")){testK.SizeOfLuggage.add(1);}
            if(temp.equals("med")){testK.SizeOfLuggage.add(2);}
            if(temp.equals("big")){testK.SizeOfLuggage.add(3);}
        }
        for(int i=0;i<test.EstimatedSafety.size();i++)
        {
            String temp = test.EstimatedSafety.get(i);
            if(temp.equals("low")){testK.EstimatedSafety.add(1);}
            if(temp.equals("med")){testK.EstimatedSafety.add(2);}
            if(temp.equals("high")){testK.EstimatedSafety.add(3);}
        }


        /*System.out.println(objects.CarAcceptability.size());
        System.out.println(objectsK.CarAcceptability.size());*/
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
                    objectsK.CarAcceptability.add(country[6]);
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
                    testK.CarAcceptability.add(country[6]);

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
