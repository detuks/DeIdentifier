package eu.detuks.iden.storage;

import java.util.ArrayList;

public class MethodIdent {
	public String name;
	public String classOwning;
	public String returnType;
	public int complexity;
	public ArrayList<String> desc = new ArrayList<String>();

	public MethodIdent(String name, String classOwning, String returnType,int complexity, ArrayList<String> desc) {
		this.name = name;
		this.classOwning = classOwning;
		this.returnType = returnType;
		this.complexity = complexity;
		this.desc = desc;
	}

}
