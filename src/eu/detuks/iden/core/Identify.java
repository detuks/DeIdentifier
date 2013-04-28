package eu.detuks.iden.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.jar.JarFile;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;

import eu.detuks.iden.analyzers.ClassAnalyzer;
import eu.detuks.iden.analyzers.CompareClasses;
import eu.detuks.iden.results.RetClientIdent;
import eu.detuks.iden.results.RetFieldIdent;
import eu.detuks.iden.results.RetMethodIdent;
import eu.detuks.iden.storage.ClassIdent;
import eu.detuks.iden.storage.FindValues;
import eu.detuks.iden.storage.IdenResult;
import eu.detuks.iden.util.JarUtils;

public class Identify {

	static HashMap<String, ClassNode> classes1 = new HashMap<String, ClassNode>();
	static HashMap<String, ClassNode> classes2 = new HashMap<String, ClassNode>();
	static HashMap<String, ClassIdent> classIdMap1 = new HashMap<String, ClassIdent>();
	static HashMap<String, ClassIdent> classIdMap2 = new HashMap<String, ClassIdent>();
	public static ArrayList<String> classesToFind1 = new ArrayList<String>();
	//public static ArrayList<String> classesToFind2 = new ArrayList<String>();
	
	

	public static void main(String[] args) {
		//classesToFind.add("client");
		classesToFind1.add("v");
		classesToFind1.add("c");
		//classesToFind1.add("ar");
		//classesToFind1.add("am");
		
		//classesToFind1.add("s");
		
		//String test = "[Ldco;, I], [I], [], [Ldo;], [B], [Ldo;], [], [], [], [], [Ldo;], [Ldo;], [Ldo;]";
		//System.out.println(test);
		//System.out.println(""+test.replaceAll("[\\[](\\w\\w|\\w\\w\\w)[o]", "[object"));
		getClasses();
		CompareClassID ccID = new CompareClassID();
		ClassAnalyzer ca = new ClassAnalyzer();
		
		try {
			classIdMap1 = ca.getClientIdMap(classes1,"ar");
			classIdMap2 = ca.getClientIdMap(classes2,"am");//am 13
			FindValues fv = new FindValues();
			for(ClassIdent t1 : classIdMap1.values()){
				for(ClassIdent t2 : classIdMap2.values()){
					ArrayList<RetFieldIdent> test2 = CompareClasses.testCompare(t1, t2);
					for(RetFieldIdent rfi : test2){
						//if(fv.updateMe.get("am Actor").contains(rfi.name))
							System.out.println("	Field: " +rfi.name+" equals: "+rfi.equals + " "+rfi.proc + "%");
					}
				}
			}
			
			
			
			/*//System.out.println(""+classIdMap2.get("c").methods.get(3).fieldsContain.get(0).name);
			ArrayList<RetClientIdent> cIdent = CompareClasses.superCompare(classIdMap1, classIdMap2);
			//for(int i =0;i<classIdMap1.get("v").methods.size();i++)
				//System.out.println(""+classIdMap1.get("v").methods.get(i).returnType);
			for(RetClientIdent rci : cIdent){
				System.out.println("class: " +rci.name+" equals: "+rci.equals + " "+rci.proc + "%");
				for(RetFieldIdent rfi : rci.fieldies.values()){
					System.out.println("	Field: " +rfi.name+" equals: "+rfi.equals + " "+rfi.proc + "%");
				}
				//for(RetMethodIdent rmi : rci.methods){
				//	System.out.println("	Method: " +rmi.name+" equals: "+rmi.equals + " "+rmi.proc + "%");
				//	for(RetFieldIdent rfi : rmi.fields){
				//		System.out.println("		Field: " +rfi.name+" equals: "+rfi.equals + " "+rfi.proc + "%");
				//	}
				//}
			}*/
			
			
		} catch (AnalyzerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done!");
	}

	public static void getClasses() {
		try {
			classes1 = JarUtils.getClasses(new JarFile(
					"C:/packs/gamepack14.jar"));
			System.out.println("classes 1 size: " + classes1.size());
			classes2 = JarUtils.getClasses(new JarFile(
					"C:/packs/gamepack13.jar"));
			System.out.println("classes 2 size: " + classes2.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
