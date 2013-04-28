package eu.detuks.iden.results;

import java.util.ArrayList;
import java.util.HashMap;

public class RetClientIdent {
	public String name;
	public String equals;
	public int proc;
	public ArrayList<RetMethodIdent> methods = new ArrayList<RetMethodIdent>();
	public HashMap<String, RetFieldIdent> fieldies = new HashMap<String, RetFieldIdent>();
	
	public RetClientIdent(String name,
	String equals,
	int proc,
	ArrayList<RetMethodIdent> methods, HashMap<String, RetFieldIdent> fieldies) {
		this.name = name;
		this.equals = equals;
		this.proc = proc;
		this.methods = methods;
		this.fieldies = fieldies;
	}
}
