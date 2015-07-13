package com.lizakowski.tools.AI;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;





public abstract class Symbol implements Serializable {
	//any class which has a toString can be a Symbol

	public Symbol () {
	}

	public abstract String toString();

	public boolean equals(Symbol s) {
		//this works because we use toSTring as the hash keys, so it is the definitive test of equality.
		//it would be nice to have somethign else used for equality, but it would have to work nicely with the 
		//hash keys, and as of yet, this is the best solution, so we might as well implement .equals as sim[ply a string check.  
		//note: this can be overridden to be more efficient , if there is a more elegant soltuion for a particular type of symbol

		return s.toString().equals(this.toString());
	} 


	public abstract void print();

	static void printList(LinkedList doc) {
		ListIterator iter=doc.listIterator(0);
		while(iter.hasNext()) {
			System.out.print(((StringSymbol)iter.next()).toString() );
		}
	}
	static void printMarkedList(LinkedList doc) {
		ListIterator iter=doc.listIterator(0);
		while(iter.hasNext()) {
			System.out.print("{"+((StringSymbol)iter.next()).toString() + "}");
		}
	}

}
