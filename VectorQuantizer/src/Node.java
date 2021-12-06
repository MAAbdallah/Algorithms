import java.util.ArrayList;

public class Node {
	private ArrayList<ArrayList<Double>> numbers;
	private ArrayList<Double> mean;

	public ArrayList<Double> getMean() {
		return mean;
	}

	public void setMean(ArrayList<Double> mean) {
		this.mean = mean;
	}
	public ArrayList<ArrayList<Double>> getNumbers() {
		return numbers;
	}

	public void setNumbers(ArrayList<ArrayList<Double>> numbers) {
		this.numbers = numbers;
	}
	public void setMean_1(ArrayList<Double> mean)
	{
		this.mean = new ArrayList<Double>();
		for (int i = 0; i < mean.size(); i++) {
			this.mean.add(mean.get(i) - 1 );
		}
	}
	public void setMean_2(ArrayList<Double> mean)
	{
		this.mean = new ArrayList<Double>();
		for (int i = 0; i < mean.size(); i++) {
			this.mean.add(mean.get(i)  + 1 );
		}
	}
	public Node() {
		numbers = new ArrayList<ArrayList<Double>>();
		mean = new ArrayList<Double>();
	}
}
