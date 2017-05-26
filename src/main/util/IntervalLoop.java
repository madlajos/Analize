package main.util;

public class IntervalLoop {
	final double dmin;
	final double dmax;
	final double q;
	int[] cardinalityVector = new int[100];
	int[] volumeVector = new int[100];
	
	public IntervalLoop(double dmin, double dmax){
		this.dmin = dmin;
		this.dmax = dmax;
		this.q = Math.pow(dmax/dmin, 1.0/100);
	}
	
	public void addItem(double val){
		int index = getIndex(val);
		cardinalityVector[index]++;
		volumeVector[index] += val; 
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
		return getintervalAvg(index) * cardinalityVector[index];
	}
}
