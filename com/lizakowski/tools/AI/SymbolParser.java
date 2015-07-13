package com.lizakowski.tools.AI;


import java.lang.*;
import java.util.*;
import java.io.*;


public abstract class SymbolParser {
	//any class which has a toString can be a Symbol

	SymbolParser () {
	}

	public abstract void load(String filename) throws java.io.FileNotFoundException, java.io.IOException;
	public abstract void save(String filename) throws java.io.IOException;
	public abstract LinkedList getSymbols();
	public abstract void setSymbols(LinkedList list);


}
