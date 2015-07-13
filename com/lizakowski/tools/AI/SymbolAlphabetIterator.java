package com.lizakowski.tools.AI;

import com.lizakowski.tools.AI.*;

import java.lang.*;
import java.util.*;
import java.io.*;

public class SymbolAlphabetIterator implements Serializable {
	//Keeps track of unique symbols, overall rate of occurrence, and most importantly can assign stable integer indices to a symbol

	SymbolTreeNode top;
	SymbolTreeNode cur;
	private SymbolTreeNode curInternal;	//an iterator for use within the class


	SymbolAlphabetIterator (SymbolTreeNode top) {
		//pass us the top of the tree, and we will access the first level children, which are the alphabet
		//note that adding a symbol can reblance the tree, invalidating the order (and count) of the alphabet

		this.top=top;
		reset();
	}

	public void reset() {
		cur=this.top;
	}
	public void resetInternal() {
		curInternal=this.top;
	}

	public int size() {
		resetInternal();
		int i=0;
		while(nextInternal()!=null) i++;
	
		return i;
	}
	
	public boolean hasNext() {
		if (cur==top) {		//special case of first element
			if (cur.firstChild!=null) return true;
			else return false;
		}
		else {
			if (cur.lilBro!=null) return true;
			else return false;
		}
	}

	public Symbol next() {
		if (cur==top) {		//special case of first element
			if (cur.firstChild!=null) {
				cur=cur.firstChild;
				return cur.symbol;
			}
			else {
				return null;
			}
		}
		else {
			if (cur.lilBro!=null)  {
				cur=cur.lilBro;
				return cur.symbol;
			}	
			else  {
				return null;
			}
		}
	}

	private boolean hasNextInternal() {
		if (curInternal==top) {		//special case of first element
			if (curInternal.firstChild!=null) return true;
			else return false;
		}
		else {
			if (curInternal.lilBro!=null) return true;
			else return false;
		}
	}

	private Symbol nextInternal() {
		if (curInternal==top) {		//special case of first element
			if (curInternal.firstChild!=null) {
				curInternal=curInternal.firstChild;
				return curInternal.symbol;
			}
			else {
				return null;
			}
		}
		else {
			if (curInternal.lilBro!=null)  {
				curInternal=curInternal.lilBro;
				return curInternal.symbol;
			}	
			else  {
				return null;
			}
		}
	}


	public String toString() {
		return top.toString(0,1);
	}

/*
	public void addSymbol(Symbol s) {
		//add symbol if new, and increment count
	}

	public void symbolCount(Symbol s) {
		//this is really used to test if the symbol exists, and the count is "free" (and already obtainable from the tree, but might as well provide it)
		//maintain counts for how often each is called, and move them towards the front of the list, or use a hashmap
		//returns null if not found
		
	}

	public SymbolAlphabetIterator getIterator() {
		//iterator supports next(), hasNext(), reset()
		return new SymbolAlphabetIterator(this);
	}
*/	



}
