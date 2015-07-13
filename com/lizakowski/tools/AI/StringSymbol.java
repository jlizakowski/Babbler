package com.lizakowski.tools.AI;

import com.lizakowski.tools.AI.*;

import java.lang.*;
import java.util.*;
import java.io.*;

public class StringSymbol extends Symbol implements Serializable{

	String value;

	StringSymbol (String val) {
		value=val;
	}
	StringSymbol (char val) {
		char [] arg=new char[1];
		arg[0]=val;
		value=new String(arg);
	}
	public String toString() {
		return value;
	}
	public void print() {
		System.out.print(value);
	}

}
