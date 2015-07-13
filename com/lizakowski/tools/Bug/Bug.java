
package com.lizakowski.tools.Bug;

public class Bug extends RuntimeException {
	public int mode;  //try to phase this out
	public String msg;
	public String type;
	
	public Bug() {
		super();

		this.msg=new String("No inforamtion was supplied");

		System.out.println("Bug!");
	}

	public Bug(String msg) {
		super(msg);

		this.msg=msg;

		//System.out.println("Bug!");

	}
/*
	public Bug(String msg,String type) {  /slightly better than int.  Currently unused, hence commented.  Need to clean this file up a bit
		super(msg);

		this.type=type;
		this.msg=msg;

	}
*/
	public Bug(String msg,int mode) {  //integers are confusing, try phase this out
		super(msg);

		this.mode=mode;
		this.msg=msg;

		if (mode==1) {
			this.printStackTrace();
			System.out.println("Bug!");
		}
		if (mode==2) {
			//filenotfound ewrror;
		}
	}
}


