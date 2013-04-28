package eu.detuks.iden.analyzers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.util.Printer;

import eu.detuks.iden.core.Identify;
import eu.detuks.iden.storage.ClassIdent;
import eu.detuks.iden.storage.FieldIdent;
import eu.detuks.iden.storage.MethodIdent;

public class ClassAnalyzer {

	public HashMap<String, ClassIdent> getClientIdMap(
			HashMap<String, ClassNode> classes,String test) throws AnalyzerException {
		HashMap<String, ClassIdent> classIdMap = new HashMap<String, ClassIdent>();

		for (ClassNode cn : classes.values()) {
			// System.out.println(cn.sourceFile);
			if (!cn.name.equals(test))
				continue;
			System.out.println("Class: " + cn.name);
			String superName = "";
			if (cn.superName.contains("java")) {
				superName = cn.superName;
			}
			ArrayList<FieldIdent> fields = getFields(cn);
			//ArrayList<MethodIdent> methods = getMethods(cn);
			

			// System.out.println(cn.name+" "+Arrays.toString(getFieldDesc(cn).toArray()));
			ClassIdent ci = new ClassIdent(cn.name, superName, fields);
			classIdMap.put(cn.name, ci);
		}

		return classIdMap;
	}

	private ArrayList<MethodIdent> getMethods(ClassNode cn) throws AnalyzerException {
		ArrayList<MethodIdent> mDescs = new ArrayList<MethodIdent>();
		MethodNode next = null;
		for (Iterator<MethodNode> fi = cn.methods.iterator(); fi.hasNext();) {
			next = fi.next();
			//if ((next.access & Opcodes.ACC_STATIC) == 0) {
				ArrayList<String> desc = new ArrayList<String>();
				String name = next.name;
				//ClassIdent ci = null;
				Type[] descRaw = Type.getArgumentTypes(next.desc);
				for (Type ty : descRaw) {
					desc.add(ty.toString().replaceAll("(\\w)+;", "object"));
				}

				// Arrays.toString().replaceAll("(\\w)+;", "object");
				String returnT = Type.getReturnType(next.desc).toString().replaceAll("L(\\w)+;", "object");
				int complexity = (new Complexity()).getCyclomaticComplexity(
						cn.name, next);
				MethodIdent mi = new MethodIdent(name, cn.name, returnT, complexity,
						desc);
				// mDescs.add(Arrays.toString(Type.getArgumentTypes(next.desc)).replaceAll("(\\w)+;",
				// "object"));
				mDescs.add(mi);
			//}
		}

		return mDescs;
	}

	public ArrayList<FieldIdent> getFields(ClassNode cn) throws AnalyzerException {
		ArrayList<FieldIdent> fields = new ArrayList<FieldIdent>();
		ArrayList<FieldNode> afn = (ArrayList<FieldNode>) cn.fields;
		//ArrayList<MethodIdent> methods = new ArrayList<MethodIdent>();
		for (FieldNode fn : afn) {
			//if ((fn.access & Opcodes.ACC_STATIC) == 0) {
			ArrayList<MethodIdent> methods = AnalyzerUtils.getMethodsthatUse(cn,fn.name, cn.name);
				
				ArrayList<Integer[]> patterns = new ArrayList<Integer[]>();
				String desc = Type.getReturnType(fn.desc).toString();
				MethodNode next = null;
				for (Iterator<MethodNode> fi = cn.methods.iterator(); fi
						.hasNext();) {
					next = fi.next();
					for (Integer[] s : AnalyzerUtils.getMethodInsnFlow(next,cn.name + "." + fn.name)) {
						if (!patterns.contains(s))
							patterns.add(s);
					}
				}
				FieldIdent fid = new FieldIdent(fn.name, desc, methods,patterns);
				fields.add(fid);
			//}
		}

		return fields;
	}

	private ArrayList<String> getFieldDesc(ClassNode cn) {
		ArrayList<String> fDescs = new ArrayList<String>();
		FieldNode next = null;
		for (Iterator<FieldNode> fi = cn.fields.iterator(); fi.hasNext();) {
			next = fi.next();
			//if ((next.access & Opcodes.ACC_STATIC) == 0) {
				// System.out.println(""+Type.getReturnType(next.desc).toString());
				fDescs.add(Type.getReturnType(next.desc).toString());
			//}
		}
		return fDescs;
	}

}
