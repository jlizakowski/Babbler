package com.lizakowski.tools.AI;

import java.io.Serializable;

import com.lizakowski.tools.math.RandomTools;

public class WeightedOddsArray implements Serializable {
	//does probabilities for arrays

	private double [] elements;	//each hold the odds/occurencerate for one type of event

	//doubel sum;		//this saves having to recalculate it during pick()
	//however, right now, weghted odds arrays have pick() called once per instance, so it's no net gain at this point

	int size;
	String [] descriptions;	//for pretty-printing if wanted
	Object [] payload;	//optional, you can store objects (references), so that pick() gets to the point with less hassle

	WeightedOddsArray(int size) {	//need to delcare the number of eventTypes in advance
		elements=new double[size];	
		descriptions=new String[size];	
		payload=new Object[size];	
		this.size=size;
		clear();
	}

	public void setDescription(int index,String desc) {
		descriptions[index]=desc;
	}
	public void setPayload(int index, Object p) {
		payload[index]=p;
	}

	public void clear() {
		for (int i=0;i<size;i++) {
			elements[i]=0;
			descriptions[i]="";
			payload[i]=null;
		}
	}

	public Object getPayload(int index) {
		return payload[index];
	}

	public void set(int index, double val) {
		elements[index]=val;
		check(index);
	}
	public void set(int index, double val, Object p) {
		elements[index]=val;
		payload[index]=p;
		check(index);
	}
	public void increment(int index, double val) {
		elements[index]+=val;
		check(index);
	}
	public void scale(int index, double val) {
		elements[index]*=val;
		check(index);
	} 
	public void scale(double val) {
		for (int i=0;i<size;i++) {
			elements[i]*=val;
		}
	}
	private double sum() {
		double sum=0;

		for (int i=0;i<size;i++) {
			sum+=elements[i];
		}
		return sum;
	}

	public int pickRandomElement() {
		double tgt=RandomTools.straightRandom(sum());
		double sum=0;
		
		int i;
		for (i=0;i<size;i++) {
			sum+=elements[i];
			if (sum>tgt) return i;
		}
		return (size-1);
	}
	public Object pickRandomPayload() {
		//return the payload, rather than the index

		double tgt=RandomTools.straightRandom(sum());
		double sum=0;
		
		int i;
		for (i=0;i<size;i++) {
			sum+=elements[i];
			if (sum>tgt) return payload[i];
		}
		return payload[size-1];
	}

	private void check(int index) throws RuntimeException{
		if (elements[index]<0) {
			throw new RuntimeException("Negative element:"+index+"\n"+this);
		}
	}

	public void combine(WeightedOddsArray otherOdds) {

		for (int i=0;i<size;i++) {
			elements[i]+=otherOdds.elements[i];
		}

	}
	public String toString() {
		String ret="[";

		for (int i=0;i<size-1;i++) {
			if (descriptions[i].length()>0) {
				ret+=descriptions[i]+":";
			}
			ret+=elements[i]+", ";
		}
		ret+=elements[size-1]+"]";

		return ret;
	}
}
