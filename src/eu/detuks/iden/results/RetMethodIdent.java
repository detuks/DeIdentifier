package eu.detuks.iden.results;

import java.util.ArrayList;

public class RetMethodIdent {
	public String name;
	public String equals;
	public int proc;
	public ArrayList<RetFieldIdent> fields = new ArrayList<RetFieldIdent>();
	
	public RetMethodIdent(String name,
	String equals,
	int proc,
	ArrayList<RetFieldIdent> fields) {
		this.name = name;
		this.equals = equals;
		this.proc = proc;
		this.fields = fields;
	}
}
