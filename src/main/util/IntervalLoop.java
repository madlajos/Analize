package main.util;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class IntervalLoop {
	final double dmin;
	final double dmax;
	final double q;
	int[] cardinalityVector = new int[50];
	double[] volumeVector = new double[50];
	ArrayList<Double> items = new ArrayList<>();
	double totalV = 0.0;
	int size;
	
	public IntervalLoop(double dmin, double dmax){
		this.dmin = dmin;
		this.dmax = dmax;
		this.q = Math.pow(dmax/dmin, 1.0/50);
	}
	
	public void clearItems(){
		items.clear();
	}
	
	
	public void addItem(double val){
		int index = getIndex(val);
		totalV += d2Volume(val);
		try {
			cardinalityVector[index]++;
			volumeVector[index] += d2Volume(val);
			items.add(val);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public int getItemsSize(){
		int itemsSize = items.size();
		
		return itemsSize;
	}
	
	public double getTopBound(int index){
		return dmax*Math.pow(q, index+1);
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
		return volumeVector[index];
	}
	
	public double getIntervalVpercent(int index){
		double intervalV = getInteralVolume(index);
		if(totalV == 0.0){
			return 0;
		}
		return intervalV / totalV * 50;
	}
	
	public double getPercentile(double p){
		Percentile perc = new Percentile(p);
		double[] temp = new double[items.size()];
		for(int i = 0; i < items.size(); i++){
			temp[i] = items.get(i);
		}
		perc.setData(temp);
		return perc.evaluate();
	}
	
	private double d2Volume(double d){
		return 4.0 * Math.PI * Math.pow(d/2, 3)/3;
	}
	
	public double getSum(){
		double sum = 0;
		for(int i = 0; i < 50; i++){
			sum += volumeVector[i];
		}
		return sum;
	}
}
