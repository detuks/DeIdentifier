package eu.detuks.iden.storage;

import java.util.ArrayList;

public class ClassIdent {
	public String name;
	public String haveSupeClass;
	public ArrayList<FieldIdent> fields = new ArrayList<FieldIdent>();
	
	public ClassIdent(String name, String haveSuperClass, ArrayList<FieldIdent> fields) {
		this.name = name;
		this.haveSupeClass = haveSuperClass;
		this.fields = fields;
	}
	
	
}
