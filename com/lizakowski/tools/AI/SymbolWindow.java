package com.lizakowski.tools.AI;


import java.util.LinkedList;
import java.util.ListIterator;

public class SymbolWindow {
	//a form of linked list/queue for Symbols

	//LinkedList<Symbol>  queue;
	LinkedList queue;
	double count;	

	SymbolWindow () {
		//queue=new LinkedList<Symbol>();
		queue=new LinkedList();
		count=0;
	}

	public void add(Symbol s) {
		queue.addFirst(s);
		count++;
	}

	public void release(int n) {
		for (int i=0;i<n;i++) {
			queue.removeLast();
			count--;
		}
	}

	ListIterator listIterator() {
		return queue.listIterator();
	}

	public String toString() {
		String ret="";
		
		ListIterator iter=queue.listIterator();
		while (iter.hasNext()) {
			ret+=iter.next()+", ";
		}

		ret+="\n";
		return ret;
	}

}
