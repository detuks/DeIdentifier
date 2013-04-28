package eu.detuks.iden.storage;

import java.util.ArrayList;

public class FieldIdent {
	public String name;
	public String desc;
	public ArrayList<MethodIdent> methodOwning;
	public ArrayList<Integer[]> patterns;
	public ArrayList<String> ownMethsName;

	public FieldIdent(String name, String desc, ArrayList<MethodIdent> methodOwning, ArrayList<Integer[]> patterns) {
		this.name = name;
		this.desc = desc;
		this.methodOwning = methodOwning;
		this.patterns = patterns;
	}
	
	public boolean isOwnedBy(String methName){
		if(ownMethsName.contains(methName))
			return true;
		return false;
	}
}
