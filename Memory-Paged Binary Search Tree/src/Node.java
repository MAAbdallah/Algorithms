import java.io.IOException;
import java.io.RandomAccessFile;

public class Node {
    public int value = 0;
    public int offest =0;
    public int indexLeft =0 ;
    public int indexRight =0 ;
    public int index = 0 ;

    public Node() {
    }

    public Node(int value, int offest, int indexLeft, int indexRight, int index) {
        this.value = value;
        this.offest = offest;
        this.indexLeft = indexLeft;
        this.indexRight = indexRight;
        this.index = index ;
    }

    public void WriteNodeInFile(RandomAccessFile file) throws IOException {
        file.writeInt(value);
        file.writeInt(offest);
        file.writeInt(indexLeft);
        file.writeInt(indexRight);
        file.writeInt(index);
    }
    public void ReadNodeInFile(RandomAccessFile file) throws IOException {
        value = file.readInt();
        offest = file.readInt();
        indexLeft= file.readInt();
        indexRight=file.readInt();
        index = file.readInt();
    }
}
