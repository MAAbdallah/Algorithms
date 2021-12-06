import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class Main
{
    public static Vector<Integer> arr = new Vector<Integer>();
    public static void readFromfile()
    {
        try {
            FileReader in = new FileReader ("input.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                arr.add(Integer.parseInt(line));
            }
            buffer.close();

        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static int w =0;
    public static int h=0;

    public static int[][] readImage(String path)
    {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
            int hieght=img.getHeight();
            h = hieght;
            int width=img.getWidth();
            w= width;
            System.out.println(hieght +"  "+h+"  "+width+"  "+w);
            int[][] imagePixels=new int[hieght][width];
            for(int x=0;x<width;x++){
                for(int y=0;y<hieght;y++){
                    int pixel=img.getRGB(x, y);
                    int red=(pixel  & 0x00ff0000) >> 16;
                    int grean=(pixel  & 0x0000ff00) >> 8;
                    int blue=pixel  & 0x000000ff;
                    int alpha=(pixel & 0xff000000) >> 24;
                    imagePixels[y][x]=red;
                }
            }
            return imagePixels;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
    public static void writeImage(int[][] imagePixels,String outPath){
        // width .. hight
        BufferedImage image = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB);
        for (int y= 0; y < w; y++) {
            for (int x = 0; x < h; x++) {
                int value = -1<<24 ;
                value= 0xff000000 | (imagePixels[x][y]<<16) | (imagePixels[x][y]<<8) | (imagePixels[x][y]);
                //image.setRGB(x, y, (value<<16)|(value<<8)|(value));
                image.setRGB(x, y, value);
            }
        }
        File ImageFile = new File(outPath);
        try {
            ImageIO.write(image, "jpg", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static Vector<Integer> deComCode = new Vector<Integer>();
    public static void readDeComCode()
    {
        try {
            FileReader in = new FileReader ("DeComCode.txt");
            BufferedReader buffer = new BufferedReader(in);
            String line="";
            while ((line=buffer.readLine())!=null)
            {
                deComCode.add(Integer.parseInt(line));
            }
            buffer.close();
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        /*readFromfile();
        for(int i=0;i<arr.size();i++)
        {
            for(int j=0;j<arr.size();j++)
            {
                if(arr.get(i)<arr.get(j))
                {
                    Collections.swap(arr,i,j);
                }
            }
        }
        FeedForward f = new FeedForward(arr,2);*/

        /*FeedForward f = new FeedForward();
        f.Decom();
        System.out.println("MSE : "+f.getMSE());*/

        int[][] pixels= readImage("G:\\FCI\\third year\\IT433 - Multimedia\\MMAssignment7\\cat.jpg");
        Vector<Integer> arr_int = new Vector<Integer>();
        System.out.println(h+"  "+w);
        for (int i = 0; i <w; i++) {
            for (int j = 0; j < h; j++) {
                arr_int.add(pixels[j][i]);
            }
        }
        for(int i=0;i<arr_int.size();i++)
        {
            for(int j=0;j<arr_int.size();j++)
            {
                if(arr_int.get(i)<arr_int.get(j))
                {
                    Collections.swap(arr_int,i,j);
                }
            }
        }
        FeedForward f = new FeedForward(arr_int,2);
        f.Decom();
        readDeComCode();
        int x=0;
        int [][] decompressed_pixels=new int[w][h];
        for (int i = 0; i <w; i++) {
            for (int j = 0; j <h; j++) {
                decompressed_pixels[j][i]= deComCode.get(x++);
            }
        }
        writeImage(decompressed_pixels, "G:\\FCI\\third year\\IT433 - Multimedia\\MMAssignment7\\cat_out.jpg");
    }
}
