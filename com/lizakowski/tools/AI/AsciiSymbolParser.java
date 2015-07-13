package com.lizakowski.tools.AI;

import java.lang.*;
import java.util.*;
import java.io.*;
import com.lizakowski.tools.AI.*;



public class AsciiSymbolParser extends SymbolParser {
	
	LinkedList doc;

	AsciiSymbolParser () {
		doc=new LinkedList();
	}

	static public LinkedList parseString(String s) {
		LinkedList ret=new LinkedList();

		for (int n=0;n<s.length();n++) {
			//ret.add( new StringSymbol( new String(s.charAt(n)) ) );
			ret.add( new StringSymbol( s.substring(n,n+1)) );
		}
		return ret;
	}

	public void load(String filename) throws java.io.FileNotFoundException, java.io.IOException{
		String letter;
		int tmp1;
		char [] arg=new char[1];		

		doc.clear();
		//BufferedReader in = new BufferedReader( new FileReader(filename) );
		//lets read bytes instead of 16bits characters, which messes us up if you hit a byte over 128
		BufferedInputStream in = new BufferedInputStream( new FileInputStream(filename) );

		StringSymbol [] alphabet=new StringSymbol[256];

//java converts al bytes> 0x7f into unicode multibyte codes for printing purposes, which makes Strings ill suited 
//for binary work.  Sure, things match up properly, but when strings are output, they would have to be un-unicoded
//SO, convert to integer format

		for (int i=0;i<127;i++) {
			arg[0]=(char)i;
			letter=new String(arg);
			alphabet[i]=new StringSymbol(letter);
		}
		for (int i=127;i<256;i++) {  //use the actual integer representation at this point, and decode when printing below
			//arg[0]=(char)i;
			//letter=new String(arg);
			letter=""+i;
			alphabet[i]=new StringSymbol(letter);
		}


		tmp1=1;
		while(tmp1>-1) {
			tmp1=in.read();
//System.out.println(tmp1+" ");
			//if (tmp1!=-1) {
			if (tmp1>-1) {
				/*
				arg[0]=(char)tmp1;
				letter=new String(arg);
				doc.add(new StringSymbol(letter));		
				*/
				doc.add(alphabet[tmp1]); //uses copies of the same symbol, rather than new objects
			}
		}
//System.out.println("size="+doc.size());
		in.close();
	}

	public void save(String filename) throws java.io.IOException {
		ListIterator iter=doc.listIterator(0);
		StringSymbol s;	
		int asciiCode;
		char [] arg=new char[1];		
		byte b;

		FileOutputStream out =  new FileOutputStream(filename) ;	//no unicode parsing, just binary data output!
		//BufferedWriter out = new BufferedWriter( new FileWriter(filename) );

		while(iter.hasNext()) {
			s=(StringSymbol)iter.next();
			if (s.toString().length()>1) {	//it is a decimal code for a high-ascii (7f-ff) character
				asciiCode=Integer.parseInt(s.toString());
//System.out.println(asciiCode+"=");
//System.out.write(asciiCode);
				//b=(byte)asciiCode;
				out.write(asciiCode);
			}
			else {
				out.write(s.toString().getBytes());
			}
		}
		out.close();
	}

	public LinkedList getSymbols() {
		return doc;
	}

	public void setSymbols(LinkedList list) {
		doc=list;
	}


}
