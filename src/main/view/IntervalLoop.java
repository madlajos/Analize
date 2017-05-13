package main.view;

public class IntervalLoop {
	final double dmin;
	final double dmax;
	final double q;
	int matrix [] = new int[100];
	
	
	
	public IntervalLoop(double dmin, double dmax){
		this.dmin = dmin;
		this.dmax = dmax;
		this.q = Math.pow(dmax/dmin, 1.0/100);
	}
	
	public static void main(String[] Args){
		IntervalLoop il = new IntervalLoop(0.01, 10000);
		System.out.println(il.getBottomBound(99));
		System.out.println(il.getTopBound(99));
	}
	
	public void addItem(double val){
		int index = getIndex(val);
		matrix [index]++;
	} 
	
	public double getTopBound(int index){
		return dmin*Math.pow(q, index+1);
	}
	
	public double getBottomBound(int index){
		return dmin*Math.pow(q, index);
	}
	
	private int getIndex(double val){
		int x = (int) (Math.log(val/dmin)/Math.log(q));
		return x;
	}
	private double getintervalAvg(int index) {
		return (getTopBound(index)+getTopBound(index))/2;
	}
	private double getInteralVolume(int index){
		return getintervalAvg(index)*matrix[index];
	}
}
