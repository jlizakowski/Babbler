package com.lizakowski.tools.AI;

import java.lang.*;
import java.util.*;
import java.io.*;

public class SymbolTreeNode implements Serializable {
	//node for a symboltree

	SymbolTreeNode firstChild;
	SymbolTreeNode bigBro;
	SymbolTreeNode lilBro;
	Symbol symbol;
	double count;	//8 bytes?? even if an int

	SymbolTreeNode () {
		firstChild=null;
		bigBro=null;
		lilBro=null;
		count=0;
	}

	public int countChildren() {
		SymbolTreeNode cur=firstChild;
		int count=0;
		if (cur==null) return count;
		count++;
		while(cur.lilBro!=null) {
			cur=cur.lilBro;
			count++;
		}
		return count;

	}

	public String toHTML(int level) {
		String ret="";

		for (int i=0;i<level;i++) {
			ret+="&nbsp;";
			ret+="&nbsp;";
		}

		if (symbol==null) {
			ret+="{NULL}";
		}
		else {
			ret+="{"+symbol+"}";
		}
		ret+="&nbsp;";
		ret+="&nbsp;";
		ret+="count="+count;
		ret+="&nbsp;";
		ret+="children="+countChildren();
		ret+="<br>";

		if (firstChild!=null) {
			ret+=firstChild.toHTML(level+1);
		}		
		if (lilBro!=null) {
			ret+=lilBro.toHTML(level);
		}		
		return ret;
	}

	public String toString() {
		String ret="";
		
		if (symbol==null) {
			ret+="symbol={NULL}";
		}
		else {
			ret+="symbol={"+symbol+"}";
		}
		ret+=" ";
		ret+=" ";
		ret+="count="+count;
		ret+=" ";
		ret+=" ";
		ret+="children="+countChildren();

		return ret;
	}

	public String toString(int level) {
		return this.toString(level,0);		//no maximum depth if not specified
	}
	public String toString(int level, int maxdepth) {
		String ret="";
		
		for (int i=0;i<level;i++) {
			ret+=" ";
			ret+=" ";
		}

		if (symbol==null) {
			ret+="{NULL}";
		}
		else {
			ret+="{"+symbol.toString().replaceAll("\n","\\\\n")+"}";
		}
		ret+=" ";
		ret+=" ";
		ret+="count="+count;
		ret+=" ";
		ret+=" ";
		ret+="children="+countChildren();
		ret+="\n";

		if (firstChild!=null && level<=maxdepth) {
			ret+=firstChild.toString(level+1,maxdepth);
		}		
		if (lilBro!=null  && level<=maxdepth ) {
			ret+=lilBro.toString(level,maxdepth);
		}		
		
		return ret;
	}

	public void toStream(int level,PrintStream stream) {
		String ret="";
		
		for (int i=0;i<level;i++) {
			ret+=" ";
			ret+=" ";
		}

		if (symbol==null) {
			ret+="{NULL}";
		}
		else {
			ret+="{"+symbol.toString().replaceAll("\n","\\\\n")+"}";
		}
		ret+=" ";
		ret+=" ";
		ret+="count="+count;
		ret+=" ";
		ret+=" ";
		ret+="children="+countChildren();
		ret+="\n";

		stream.print(ret);

		if (firstChild!=null) {
			firstChild.toStream(level+1,stream);
		}		
		if (lilBro!=null) {
			lilBro.toStream(level,stream);
		}		
	
	}


}
