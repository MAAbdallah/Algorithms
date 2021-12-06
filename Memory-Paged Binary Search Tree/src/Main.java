import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Functions functions = new Functions();
		String name = "index5.bin";
		functions.CreateRecordsFile(name,11);
		//functions.DisplayIndexFileContent(name);
        functions.InsertNewRecordAtIndex(name,5,12);
        //functions.DisplayIndexFileContent("index.bin");
        functions.InsertNewRecordAtIndex(name,3,36);
        functions.InsertNewRecordAtIndex(name,12,24);
        functions.InsertNewRecordAtIndex(name,9,48);
        functions.InsertNewRecordAtIndex(name,8,44);
        functions.InsertNewRecordAtIndex(name,2,32);
        functions.InsertNewRecordAtIndex(name,4,111);
        functions.DisplayIndexFileContent(name);
        System.out.println(functions.SearchRecordInIndex(name,12));

    }

}
