package com.lizakowski.tools.AI;

import java.lang.*;
import java.util.*;
import java.io.*;
import com.lizakowski.tools.math.*;


public class SymbolTree implements Serializable {
	//a symboltree

	//tip-of-the-tounge babbling

	SymbolTreeNode top;
	int deepestDepth=0;
	SymbolTreeStats stats;
	boolean allowNewBranches=true;		//can we add new branches, or is it frozen?

	SymbolTree () {
		top=new SymbolTreeNode();  //the top node has null payload
		stats=new SymbolTreeStats();
	}

	public class SymbolTreeStats {
		int maxTreeDepth=0;
		int symbolsBabbled=0;
		int symbolsLearned=0;
		int alphabetSize=0;
		double timeLearning=0;				
		double timeBabbling=0;				


		public String toString() {
			String ret="TreeStats:\n";
			ret+="\tMax tree depth="+maxTreeDepth+"\n";
			ret+="\tAlphabet size="+alphabetSize+"\n";
			ret+="\tSymbols learned="+symbolsLearned+"\n";
			ret+="\tSymbols babbled="+symbolsBabbled+"\n";
			ret+="\tTimer: learn="+timeLearning+"\n";
			ret+="\tTimer: babble="+timeBabbling+"\n";
			return ret;
		}	

	}

	void addFromWindow(SymbolWindow window) {
		ListIterator iter=window.listIterator();  //the iterator will show is the most recent addition first
		
		Symbol symbol;
		SymbolTreeNode par=top;
		SymbolTreeNode cur=top;
		int depth=0;
		boolean done=false;
		cur.count++;
		while (iter.hasNext() && !done) {
			symbol=(Symbol)iter.next();

			//String [] hex= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};	
			//int x=(int)(symbol.toString().charAt(0));
			//System.out.print(" "+ hex[x/16]+hex[x%16] );

			cur=findChild(symbol,par);	//finds the node mathching the symbol amongst the children
			if (cur==null) {		//if child not found
				if (allowNewBranches) {
					cur=new SymbolTreeNode();
					cur.count=1;
					cur.symbol=symbol;
					addChild(cur,par);	//scroll along the list and add it where it fits
				}
				done=true;
			}
			else {
				cur.count++;
				sortSiblings(cur,par);	//sort siblings, and update the parent if the oldest child changes	
			}
			par=cur;
			depth++;		//this is the deepest we went.  
		}

		window.release((int)window.count-depth-1);	//release the window up to depth, since we will never ask for more than that
		//System.out.println("Window:"+window.count);
		if (depth>deepestDepth) {
			deepestDepth=depth;
			stats.maxTreeDepth=depth;
		}
	}

	WeightedOddsArray nextSymbol(SymbolWindow window) {
		ListIterator iter=window.listIterator();  //the iterator will show is the most recent addition first

//how exactly do these synchronize between different trees?????   Doh!
//maybe the weightedoddsarray needs some intelligence to match them up when mixing

		SymbolAlphabetIterator alphabetIter=new SymbolAlphabetIterator(this.top);

		Symbol symbol;
		Symbol testSymbol;

		int alphabetSize= alphabetIter.size();

		WeightedOddsArray odds=new WeightedOddsArray(alphabetSize);

		//Load the array with descriptive messages for rare errors: can be commented out
		/*
		Iterator alphabetIter2=alphabet.iterator();
		int k=0;
		while(alphabetIter2.hasNext()) {
			testSymbol=(Symbol) uniqueSymbols.get(alphabetIter2.next());
			odds.setDescription(k,testSymbol.toString());
			k++;
		}
		*/

		int depth=0;
		double weight=1;
		double ratio=1;
		int i=0;
		int maxdepth=0;

		SymbolTreeNode par=top;
		SymbolTreeNode cur=top;

		while(alphabetIter.hasNext()) {
			testSymbol=alphabetIter.next();

			cur=findChild(testSymbol,par);
//System.out.print("\t("+testSymbol+")"+cur.count+" ");

			//odds.increment(i,1);
			odds.set(i,1.0,testSymbol);	//index=i, odds=1.0, payload=testSymbol
		
			iter=window.listIterator();  //try to reset the iterator
			depth=1;
			weight=alphabetSize;
			while(iter.hasNext() && cur!=null) {	//now descend the tree
				depth++;	//not really needed anymore, except for stats
				symbol=(Symbol)iter.next();

				cur=findChild(symbol,cur);
				if (cur!=null) {
					//ratio=par.count/cur.count;
					weight=weight*alphabetSize*.4;
						//System.out.print("["+symbol+"]+="+cur.count+"*"+weight+" ");
					odds.increment(i,cur.count*weight);
					//odds.increment(i,cur.count*weight*ratio);
					//odds.increment(i,ratio);
				}
			}

			i++;
			if (depth>maxdepth) maxdepth=depth;
		}			

		return odds;

	}
	
	private void sortSiblings(SymbolTreeNode changedNode, SymbolTreeNode parent) {
		//resort cur, as it's count has changed

		SymbolTreeNode cur=changedNode;
		SymbolTreeNode tmp=null;
		SymbolTreeNode swapee=null;
	
		boolean done=false;

		while(!done) {
			if (cur.bigBro!=null && cur.bigBro.count<cur.count) {	//then swap with bigBro
				swapee=cur.bigBro;

				tmp=cur.lilBro;
				cur.lilBro=swapee;
				swapee.lilBro=tmp;
				if (tmp!=null) {
					tmp.bigBro=swapee;
				}

				tmp=swapee.bigBro;   
				swapee.bigBro=cur;
				cur.bigBro=tmp;
				if (tmp!=null) { 	//don't go past the head
					tmp.lilBro=cur;
				}
				else {	//fix the parent
					parent.firstChild=cur;
				}
//System.out.println("done swap with bigbro");
//System.out.println(this);
			}
			else if (cur.lilBro!=null && cur.lilBro.count>cur.count) {	//then swap with lilBro
				swapee=cur.lilBro;

				tmp=cur.bigBro;
				cur.bigBro=swapee;
				swapee.bigBro=tmp;
				tmp.lilBro=swapee;
				
				tmp=swapee.lilBro;
				swapee.lilBro=cur;
				cur.lilBro=tmp;
				if (tmp!=null) {	//don't under flow
					tmp.bigBro=cur;
				}
		
			}
			else {
				done=true;
			}

		}
	}

	private SymbolTreeNode findChild(Symbol symbol, SymbolTreeNode parent) {
		//find a child of the parent

		SymbolTreeNode cur=parent.firstChild;
		SymbolTreeNode old=null;

		if (cur==null) {	
			return null;
		}	
		if (symbol.equals(cur.symbol)) {	
			return cur;
		}	
		while(cur.lilBro!=null) {
			if (symbol.equals(cur.symbol)) {	
				return cur;
			}	
			cur=cur.lilBro;
		}
		if (symbol.equals(cur.symbol)) {	
			return cur;
		}	
		return null;
	}

	private void addChild(SymbolTreeNode newChild, SymbolTreeNode parent) {
		//add symcol as a child of parent

		SymbolTreeNode cur=parent.firstChild;
		SymbolTreeNode old=null;

		if (cur==null)  {	//then we will be the first "firstChild"
			parent.firstChild=newChild;
			newChild.bigBro=null;
			newChild.lilBro=null;
		}
		else if (cur.count<newChild.count) {	//insert before head: this should never be true, unless someone adds already populated subtrees, since the count for a new child should be 1 and that will never be the largest (at best equal to)
			parent.firstChild=newChild;
			newChild.bigBro=null;
			newChild.lilBro=cur;
		}
		else {
			while(cur.lilBro!=null && cur.lilBro.count>=newChild.count) {
				cur=cur.lilBro;
			}
			newChild.bigBro=cur;
			newChild.lilBro=cur.lilBro;
			cur.lilBro=newChild;
			if (newChild.lilBro!=null) {  	//in case we are the last element, our lilBro is null
				newChild.lilBro.bigBro=newChild;
			}
		}
	}

	public void trimOnes() {	//remove ones.  Scaling by 1/2 and trimming fracitonal counts is more accurate, but subject to temporal effects, if you add more elements later.
		trimOnes(top, top);	//recursion
	}
	public void trimOnes(SymbolTreeNode cur, SymbolTreeNode parent) {
		//don't remove top level items, as they are part of the alphabet, regardless of ho infrequent they appear we need them
	
		if (cur.firstChild!=null) {	//should never happen, or we would have been trimmed by now
			trimOnes(cur.firstChild,cur);
		}
		if (cur.lilBro!=null) {	
			trimOnes(cur.lilBro,parent);
		}
		if (cur.count<=1) {	//trim
			if (parent!=top) {	//leave at least the top row intact, else we get DB misses later
				if (cur.lilBro!=null) {
					cur.lilBro.bigBro=cur.bigBro;
				}
				if (cur.bigBro!=null) {
					cur.bigBro.lilBro=cur.lilBro;
				}
				if (parent.firstChild==cur) {
					parent.firstChild=cur.lilBro;
				}
				cur.bigBro=null;  //this will make it an orphan, and set to be GC on the next run
				cur.lilBro=null;
				cur.symbol=null;
				cur.firstChild=null;	//if not already
			}			
		}
	}

	public void zeroElements () {
		//set the counts for all elements to zero

		zeroElements(top);
	}
	public void zeroElements (SymbolTreeNode cur) {
		//set the counts for all elements to zero
	
		if (cur.firstChild!=null) {	//should never happen, or we would have been trimmed by now
			zeroElements(cur.firstChild);
		}
		if (cur.lilBro!=null) {	
			zeroElements(cur.lilBro);
		}
		cur.count=0;
	}

	public String toHTML() {
		String ret="";

		ret+="SymbolTree:<br>";
		ret+=top.toString(1);
		
		return ret;
	}

	public String toString() {	//warning: memory intensive for large trees, try toStream instead, which doesn't cache
		String ret="";
		
		ret+="SymbolTree:\n";
		ret+=top.toString(1);
		
		return ret;
	}

	public void toStream(PrintStream stream) {
		String ret="";

		ret+="SymbolTree:\n";
		stream.print(ret);
		top.toStream(1,stream);
	}

	public void learn(LinkedList symbols) {

		ListIterator iter=symbols.listIterator();

		SymbolWindow window=new SymbolWindow();		

		int i=0;
		int count=symbols.size();

		System.out.print("Learning");

		while(iter.hasNext()) {
			if (i++%(Math.ceil(count/50))==0) System.out.print(".");		//progress bar
			window.add((Symbol)iter.next());
			addFromWindow(window);
		}
		System.out.println("");

	}

	public LinkedList babble(double count) {
		return babble(count, new SymbolWindow());
	}
	public LinkedList babble(double count, SymbolWindow startingWindow) {
		WeightedOddsArray odds;
		Symbol cursymbol;

		System.out.print("Babbling");
		for (int i=0;i<count;i++) {
			if (i%(Math.ceil(count/50))==0) System.out.print(".");
			//babbleFromWindow(startingWindow);

			odds=nextSymbol(startingWindow);
			cursymbol=(Symbol)odds.pickRandomPayload();
			startingWindow.add(cursymbol);
		}
		System.out.println("");

		//we need to reverse the window to get the final text
		LinkedList ret=new LinkedList();
		ListIterator iter=startingWindow.queue.listIterator();
		while (startingWindow.queue.size()>0) {
			ret.addLast( (Symbol)(startingWindow.queue.removeLast()) );
		}

		return ret;
	}

	public static void main(String [] argv) {
		SymbolTree s=new SymbolTree();

		AsciiSymbolParser parser;
	        LinkedList insymbols;
	        LinkedList outsymbols;
		
                parser=new AsciiSymbolParser();
		try {
			if (argv.length!=1) throw new RuntimeException("bad parameters");

	     		parser.load("data/"+argv[0]);
	                insymbols=parser.getSymbols();
System.out.println(insymbols.size());
			s.learn(insymbols);
			//s.trimOnes();
			//s.learn(insymbols);
			//s.learn(insymbols);
			//s.learn(insymbols);
			//s.toStream(System.out);

			outsymbols=s.babble(80000);
	
			parser.setSymbols(outsymbols);
			parser.save("output/"+argv[0]+".tree");

			SymbolAlphabetIterator iter=new SymbolAlphabetIterator(s.top);			
			s.stats.alphabetSize=iter.size();
			System.out.println(s.stats);
			
                }
                catch (java.io.FileNotFoundException fnfe) {
                        System.out.println("File not found");
                }
                catch (java.io.IOException ioe) {
                        System.out.println("File not found");
                }


		//s.toStream(System.out);

	}


}
