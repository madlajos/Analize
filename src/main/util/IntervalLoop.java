package main.util;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class IntervalLoop {
	final double dmin;
	final double dmax;
	final double q;
	int[] cardinalityVector = new int[100];
	double[] volumeVector = new double[100];
	ArrayList<Double> items = new ArrayList<>();
	double totalV;
	
	public IntervalLoop(double dmin, double dmax){
		this.dmin = dmin;
		this.dmax = dmax;
		this.q = Math.pow(dmax/dmin, 1.0/100);
	}
	
	public void addItem(double val){
		int index = getIndex(val);
		totalV += val;
		try {
			cardinalityVector[index]++;
			volumeVector[index] += val;
			items.add(val);
		} catch (Exception e) {
			
		}
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
		double result;
		try {
			result = intervalV / totalV * 100;
		} catch(Exception e){
			result = 0;
		}
		return result;
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
}
