import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;



public class VectorQuantizer {
	private ArrayList<ArrayList<Double>> imageBlocks;
	private int vecWidth , vecHeight , height, width, codeBookSize;
	ArrayList<Node> nodes;
	ArrayList<Integer> bits;
	ArrayList<ArrayList<Double>> avg;

	public VectorQuantizer() {
		imageBlocks = new ArrayList<ArrayList<Double>>();
		nodes = new ArrayList<Node>();
		bits = new ArrayList<Integer>();
		avg = new ArrayList<ArrayList<Double>>();
		vecHeight = 0;
		vecWidth = 0;
		height = 0;
		width =0; 
		codeBookSize =0;
	}
	private void reset() {
		avg= new ArrayList<ArrayList<Double>>();
		nodes= new ArrayList<Node>();
		bits = new ArrayList<Integer>();
		imageBlocks = new ArrayList<ArrayList<Double>>();
		width=0;
		height=0;
		vecHeight=0;
		vecWidth=0;
		codeBookSize=0;
	}
	public ArrayList<ArrayList<Double>> getImageBlocks() {
		return imageBlocks;
	}
	public void setImageBlocks(ArrayList<ArrayList<Double>> imageBlocks) {
		this.imageBlocks = imageBlocks;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getCodeBookSize() {
		return codeBookSize;
	}
	public void setCodeBookSize(int codeBookSize) {
		this.codeBookSize = (int)Math.pow(2D,(double)codeBookSize);
	}
	public ArrayList<ArrayList<Double>> getIamgepixles() {
		return imageBlocks;
	}

	public void setIamgepixles(ArrayList<ArrayList<Double>> iamgepixles) {
		this.imageBlocks = iamgepixles;
	}

	public int getVecWidth() {
		return vecWidth;
	}

	public void setVecWidth(int vecWidth) {
		this.vecWidth = vecWidth;
	}

	public int getVecHeight() {
		return vecHeight;
	}

	public void setVecHeight(int vecHeight) {
		this.vecHeight = vecHeight;
	}

	public void compress(File file, int codeBookSize,int vectorWidth,int vectorHeight) throws IOException {
		reset();
		setVecHeight(vectorHeight);
		setCodeBookSize(codeBookSize);
		setVecWidth(vectorWidth);
		//int [][] x= {{1,2,7,9,4,11},{3,4,6,6,12,12},{4,9,15,14,9,9},{10,10,20,18,8,8},{4,3,17,16,1,4},{4,5,18,18,5,6}};
		init(readImage(file.getPath()));
		System.out.println(imageBlocks.size());
		setAvrages();
//		for(int i=0;i<indexes.size();i++)
//			System.out.print(bits.get(i)+" ");
		writeCompressedFile(file.getParentFile().getPath());
		System.out.println("//////////////////////////////////////////////////////////////////");
	}
	public void decompress(File file) throws IOException{
		reset();
		Scanner scanner=new Scanner(file);
		height=scanner.nextInt();
		width=scanner.nextInt();
		vecHeight=scanner.nextInt();
		vecWidth=scanner.nextInt();
		codeBookSize=scanner.nextInt();
		System.out.println(height+" "+width+" "+vecHeight+" "+vecWidth);
		ArrayList<Double> holder;
		for(int i=0;i<codeBookSize;i++) {
			holder=new ArrayList<Double>();
			for(int j=0;j<vecHeight*vecWidth;j++) {
				holder.add(scanner.nextDouble());
			}
			avg.add(holder);
		}
//		for(int i=0;i<fileAverages.size();i++) {
//			for(int j=0;j<fileAverages.get(0).size();j++) {
//				System.out.print(fileAverages.get(i).get(j)+" ");
//			}
//			System.out.println("^^^^^");
//		}
		while(scanner.hasNextInt()) {
			bits.add(scanner.nextInt());
		}
		writeImage(file.getParentFile().getPath(),constructImage());
		scanner.close();
	}
	public void init( int [][] temp)
	{
		width = temp.length;
		height = temp[0].length;
		for(int i = 0 ; i< width ; i+=vecWidth)
		{
			for(int  j = 0 ;j<height ; j+=vecHeight)
			{
				ArrayList<Double> blocks = new ArrayList<>();
				for(int k = i ; k <i+vecWidth ; k++)
				{
					for(int q = j; q<j+vecHeight ; q++)
					{
						if(k < width && q < height)
						{
							blocks.add((double) temp[k][q]);
						}
						else {
							blocks.add((double) -1);
						}
						
					}
				}
				//System.out.println(i + " :" + j + " : " + width + " : " + height + " : " + vecHeight + " : " + vecWidth);
				imageBlocks.add(blocks);
			}
		}	
	}
	private void printPixles()
	{
		for(int i = 0 ; i< imageBlocks.size(); i++)
		{
			System.out.println(imageBlocks.get(i));
		}
	}
	
	public void setAvrages()
	{
		Node node = new Node();
		node.setNumbers(imageBlocks);
		nodes.add(node);
		for(int i = 1 ;i<codeBookSize;i*=2)
		{
			//System.out.println("->"+i);
			for(int j = 0 ; j<i; j++)
			{
				System.out.println(nodes.get(0).getNumbers());
				nodes.get(0).setMean(getAvrage(nodes.get(0).getNumbers()));
				split();
			}
			allocate();
			
		}
//		for(int i=0;i<nodes.size();i++) {
//			System.out.println(" -> "+nodes.get(i).getNumbers().size());
//		}
		for(int i =0; i<10 && !updateAvgrage() ; i++)
		{
			allocate();
		}
		
		ArrayList<Double> differences = new ArrayList<Double>();
		Double smallestDistance = 100000000D;
		
		int index = -1;
		for(int i =0; i<imageBlocks.size();i++)
		{
			for(int j =0; j<nodes.size();j++)
			{
				differences.add(vectorDiffrence(imageBlocks.get(i), nodes.get(j).getMean()));
				if(differences.get(differences.size() - 1)<smallestDistance)
				{
					smallestDistance = differences.get(differences.size()-1);
					index = differences.size()-1;	
				}
				
			}
			bits.add(index);
			smallestDistance = 10000000D;
			differences.clear();

		}
		
		for(int i =0; i<bits.size();i++)
		{
			System.out.println(bits.get(i));
		}
	}
	public boolean updateAvgrage()
	{
		ArrayList<ArrayList<Double>> avg = new ArrayList<ArrayList<Double>>();
		boolean flag = true;
		for (int i = 0; i < nodes.size(); i++) {
			avg.add(nodes.get(i).getMean());
			nodes.get(i).setMean(getAvrage(nodes.get(i).getNumbers()));
		}
		for(int i = 0; i<avg.size(); i++)
		{
			if(!sameBlock(avg.get(i) , nodes.get(i).getMean()))
			{
				flag = false;
				break;
			}
		}
		return flag;
	}
	private boolean sameBlock(ArrayList<Double> v1 , ArrayList<Double> v2)
	{
		boolean flag = true;
		for(int i = 0; i <v1.size(); i++)
		{
			if (!v1.get(i).equals(v2.get(i)))
			{
				flag = false;
			}
		}
		return flag;
	}
	private void split()
	{
		Node newNode1 = new Node();
		Node newNode2 = new Node();
		newNode1.setMean_1(nodes.get(0).getMean());
		newNode2.setMean_2(nodes.get(0).getMean());
		nodes.add(newNode1);
		nodes.add(newNode2);
		nodes.remove(0);
	}
	//Assigne vectors to the nearest node
	private void allocate()
	{
		for(int i = 0; i<nodes.size();i++)
		{
			nodes.get(i).getNumbers().clear();
		}
		ArrayList<Double> differences = new ArrayList<Double>();
		Double smallestDistance = 100000000D;
		int index = -1;
		for(int i = 0; i<imageBlocks.size(); i++)
		{
			for(int j = 0 ; j<nodes.size();j++)
			{
				//To calculate the Difference between Block and avrages 
				differences.add(vectorDiffrence(imageBlocks.get(i), nodes.get(j).getMean()));
				//System.out.println(vectorDiffrence(imageBlocks.get(i), nodes.get(j).getMean()));
				if(differences.get(differences.size() - 1)<smallestDistance)
				{
					smallestDistance = differences.get(differences.size()-1);
					index = differences.size()-1;	
				}
			}
			nodes.get(index).getNumbers().add(imageBlocks.get(i));
			smallestDistance = 10000000D;
			differences.clear();
		}
	}
	private Double vectorDiffrence (ArrayList<Double> v1 , ArrayList <Double> v2 )
	{
		double diffvalue = 0 ;
		for(int i = 0 ; i<v1.size(); i++)
		{
			//System.out.println("------------------------> "+ diffvalue);
			diffvalue += Math.abs(v1.get(i) - v2.get(i));
		}
		//System.out.println(v1.get(0) + " " + v2.get(0) + " " + "---> " + diffvalue);
		return diffvalue;
 	}
	private ArrayList<Double> getAvrage( ArrayList<ArrayList<Double>> vectors) {
		ArrayList<Double> average=new ArrayList<Double>();
		double avg;
		for(int i=0;i< vectors.get(0).size();i++) {
			avg=0D;
			for(int j=0;j<vectors.size();j++) {
				avg+=vectors.get(j).get(i);
			}
			if(vectors.size()!=0)
				average.add(avg/vectors.size());
			else
				average.add(0D);
		}
		return average;
	}
	
	public static int[][] readImage(String path){
		
		
		BufferedImage img;
		try {
		img = ImageIO.read(new File(path));
		int hieght=img.getHeight();
		int width=img.getWidth();
		
		int[][] imagePixels=new int[hieght][width];
		
		for(int x=0;x<width;x++){
			for(int y=0;y<hieght;y++){
				
				int pixel=img.getRGB(x, y);
				
				int red=(pixel  & 0x00ff0000) >> 16;
				int grean=(pixel  & 0x0000ff00) >> 8;
				int blue=pixel  & 0x000000ff;
				int alpha=(pixel & 0xff000000) >> 24;
				imagePixels[x][y]=red;
			}
		}
		
		return imagePixels;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}

	private void writeImage(String parentFilePath,int [] [] image)
    {
        File fileout=new File(parentFilePath+"\\"+"vDecompressed.jpg");
        BufferedImage image2=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );
        int temp=0;
        for(int x=0;x<width ;x++)
        {
            for(int y=0;y<height;y++)
            {
            	temp=image[x][y];
                image2.setRGB(x,y,(temp<<16)|(temp<<8)|(temp));
            }
        }
        try
        {
            ImageIO.write(image2, "jpg", fileout);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
	private void writeCompressedFile(String parentPath) throws IOException {
		PrintWriter outputStream=new PrintWriter(new FileWriter(parentPath+"\\"+"compressed.txt"));
		outputStream.println(height+" "+width);
		outputStream.println(vecHeight+" "+vecWidth+" "+codeBookSize);
		for(int i=0;i<codeBookSize;i++) {
			for(int j=0;j<nodes.get(i).getMean().size();j++) {
				outputStream.print(nodes.get(i).getMean().get(j)+" ");
			}
		}
		for(int i=0;i<bits.size();i++) {
			outputStream.print(bits.get(i)+" ");
		}
		outputStream.close();
	}
	  private void read(File file) throws IOException
	  {
		  bits = new ArrayList<Integer>();
		  ArrayList<Double> holder;
		  int width ,height, vecWidth, vecHeight, codeBook,bitsSize;
		  Scanner scanner = new Scanner(file);
		  width = scanner.nextInt();
		  height = scanner.nextInt();
		  vecWidth = scanner.nextInt();
		  vecHeight = scanner.nextInt();
		  codeBook = scanner.nextInt();
		  
		  for(int i = 0 ; i < codeBook ; i++)
		  {
			  holder = new ArrayList<Double>();
			  for(int  j = 0; j<vecHeight * vecWidth ; j++)
			  {
				  holder.add(scanner.nextDouble());
			  }
			  avg.add(holder);
		  }
		  bitsSize = scanner.nextInt();
		  for(int i = 0; i<bitsSize; i++)
		  {
			  bits.add(scanner.nextInt());
		  }
		  writeImage(file.getParentFile().getPath(),constructImage());
		  scanner.close();
	  }
	  
	  private int[][] constructImage(){
		int [][] image=new int[width][height];
		int blockIndex=0;
		System.out.println(avg.get(0).size());
		for(int i=0;i<width;i+=vecWidth) {
			for(int j=0;j<height;j+=vecHeight) {
				for(int _i=0,index=0;_i<vecWidth;_i++) {
					for(int _j=0;_j<vecHeight;_j++,index++) {
						System.out.println(blockIndex+" "+i+" "+j+" "+_i+" "+_j+" ");
						if(i+_i<width&&j+_j<height)
							image[i+_i][j+_j]=avg.get(bits.get(blockIndex)).get(index).intValue();
					}
				}
				blockIndex++;
			}
		}
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				System.out.print(image[i][j]+" ");
			}
			System.out.println();
		}
		return image;
	}
}