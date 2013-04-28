package eu.detuks.iden.setup;

import java.util.HashMap;

import org.objectweb.asm.tree.ClassNode;

import eu.detuks.iden.storage.ClassIdent;

public class SetupIdent {
	public HashMap<String, ClassNode> classes = new HashMap<String, ClassNode>();
	public HashMap<String, ClassIdent> classIdMap = new HashMap<String, ClassIdent>();
	
	public SetupIdent(HashMap<String, ClassNode> classes) {
		this.classes = classes;
	}
	
	
	
	
	
}
