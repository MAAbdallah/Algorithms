import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Functions {

    public void CreateRecordsFile (String filename, int numberOfRecords) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filename, "rw") ;
        Node n = new Node (1 ,0 , 0, 0,0) ;
        n.WriteNodeInFile(file);
        for (int i = 1 ; i <numberOfRecords ; i++ ) {
            n = new Node (-1,0,0,0,i) ;
            n.WriteNodeInFile(file);
        }
    }


    int InsertNewRecordAtIndex (String filename, int Key, int ByteOffset) throws FileNotFoundException, IOException
    {
        RandomAccessFile in = new RandomAccessFile(filename, "rw");
        long len = in.length();
        in.seek(0);
        int EmPage = in.readInt();
        System.out.println(EmPage);
        if(EmPage==1)
        {
            in.seek(0);
            in.seek(20);
            in.writeInt(Key);
            in.writeInt(ByteOffset);
            in.writeInt(-1);
            in.writeInt(-1);
            int index = in.readInt();
            in.seek(0);
            in.writeInt(EmPage+1);
            return index ;
        }
        //for(int i = 0;i<len;i++)
        int count = 0;
        in.seek(0);
        in.seek(20);
        int value = in.readInt();
        int offset = in.readInt();
        int left = in.readInt();
        int right = in.readInt();
        int index  = in.readInt();
        while (true)
        {
            if(Key<value)
            {
                if(left==-1)
                {
                    if(count==0)
                    {
                        in.writeInt(Key);
                        in.writeInt(ByteOffset);
                        in.writeInt(-1);
                        in.writeInt(-1);
                        in.seek((index*20)+8);
                        in.writeInt(index+1);
                        return index;
                    }
                    else
                    {
                        in.seek(0);
                        in.seek((index*20)+8);
                        in.writeInt(EmPage);
                        in.seek(0);
                        in.writeInt(EmPage+1);
                        in.seek(((EmPage-1)*20*3)+20);
                        in.writeInt(Key);
                        in.writeInt(ByteOffset);
                        in.writeInt(-1);
                        in.writeInt(-1);
                        index = in.readInt();
                        return index ;
                    }
                }
                else
                {
                    if(count ==0 ){
                        in.seek(0);
                        in.seek(left*20);
                        value = in.readInt();
                        offset = in.readInt();
                        left = in.readInt();
                        right = in.readInt();
                        index  = in.readInt();
                        count=1;
                    }
                    else{
                        in.seek(0);
                        in.seek(((left-1)*20*3)+20);
                        value = in.readInt();
                        offset = in.readInt();
                        left = in.readInt();
                        right = in.readInt();
                        index  = in.readInt();
                        count=0;
                    }

                }
            }
            if(Key>value)
            {
                if(right==-1)
                {
                    if(count==0)
                    {
                        in.seek(0);
                        in.seek(index*3*20);
                        in.writeInt(Key);
                        in.writeInt(ByteOffset);
                        in.writeInt(-1);
                        in.writeInt(-1);
                        in.seek(0);
                        in.seek((index*20)+12);
                        in.writeInt(index+2);
                        return index;
                    }
                    else
                    {
                        in.seek(0);
                        in.seek((index*20)+12);
                        in.writeInt(EmPage);
                        in.seek(0);
                        in.writeInt(EmPage+1);
                        in.seek(((EmPage-1)*20*3)+20);
                        in.writeInt(Key);
                        in.writeInt(ByteOffset);
                        in.writeInt(-1);
                        in.writeInt(-1);
                        index = in.readInt();
                        return index ;
                    }
                }
                else
                {
                    if(count==0)
                    {
                        in.seek(0);
                        in.seek(right*20);
                        value = in.readInt();
                        offset = in.readInt();
                        left = in.readInt();
                        right = in.readInt();
                        index  = in.readInt();
                        count=1;
                    }
                    else
                    {
                        in.seek(0);
                        in.seek(((right-1)*20*3)+20);
                        value = in.readInt();
                        offset = in.readInt();
                        left = in.readInt();
                        right = in.readInt();
                        index  = in.readInt();
                        count=0;
                    }
                }
            }
        }
    }

    public int SearchRecordInIndex (String filename, int Key) throws IOException {
        RandomAccessFile in = new RandomAccessFile(filename, "rw");
        long len = in.length();
        in.seek(0);
        int EmPage = in.readInt();
        System.out.println(EmPage);

        //for(int i = 0;i<len;i++)
        int count = 0;
        in.seek(0);
        in.seek(20);
        int value = in.readInt();
        int offset = in.readInt();
        int left = in.readInt();
        int right = in.readInt();
        int index  = in.readInt();
        while (true)
        {
            if(Key==value)
            {
                System.out.println("founded");
                return offset ;
            }
            if(Key<value)
            {
                if(left==-1)
                {
                    System.out.println("not founded");
                    return -1 ;
                }
                else
                {
                    if(count ==0 ){
                        in.seek(0);
                        in.seek(left*20);
                        value = in.readInt();
                        offset = in.readInt();
                        left = in.readInt();
                        right = in.readInt();
                        index  = in.readInt();
                        count=1;
                    }
                    else{
                        in.seek(0);
                        in.seek(((left-1)*20*3)+20);
                        value = in.readInt();
                        offset = in.readInt();
                        left = in.readInt();
                        right = in.readInt();
                        index  = in.readInt();
                        count=0;
                    }

                }
            }
            if(Key>value)
            {
                if(right==-1)
                {
                    System.out.println("not founded");
                    return -1;
                }
                else
                {
                    if(count==0)
                    {
                        in.seek(0);
                        in.seek(right*20);
                        value = in.readInt();
                        offset = in.readInt();
                        left = in.readInt();
                        right = in.readInt();
                        index  = in.readInt();
                        count=1;
                    }
                    else
                    {
                        in.seek(0);
                        in.seek(((right-1)*20*3)+20);
                        value = in.readInt();
                        offset = in.readInt();
                        left = in.readInt();
                        right = in.readInt();
                        index  = in.readInt();
                        count=0;
                    }
                }
            }
        }
    }
    public void DisplayIndexFileContent(String filename) throws FileNotFoundException, IOException {
        RandomAccessFile file = new RandomAccessFile(filename,"r");
        int value = 0;
        int offset = 0;
        int left = 0;
        int right = 0;
        int index = 0;
        for (int i = 0; i < file.length() /20; i++) {
            value = file.readInt();
            offset = file.readInt();
            left = file.readInt();
            right = file.readInt();
            index = file.readInt();
            System.out.println("value : "+ value +  " offset : " + offset +" left : "+ left + " right : "+ right+ " index : "+ index);
        }
    }

}
