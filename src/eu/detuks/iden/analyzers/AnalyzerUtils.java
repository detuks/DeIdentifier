package eu.detuks.iden.analyzers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.util.Printer;

import eu.detuks.iden.storage.MethodIdent;

public class AnalyzerUtils {
	
	public static ArrayList<MethodIdent> getMethodsthatUse(ClassNode cn, String field, String owner) throws AnalyzerException {
		ArrayList<MethodNode> amn = (ArrayList<MethodNode>) cn.methods;
		ArrayList<MethodIdent> mets = new ArrayList<MethodIdent>();
		for(MethodNode mn : amn){
			AbstractInsnNode instr[] = mn.instructions.toArray();
			for(int i=0;i<instr.length;i++){
				if(instr[i].getType() == AbstractInsnNode.FIELD_INSN){
					if(field.equals(((FieldInsnNode)instr[i]).name) && owner.equals(((FieldInsnNode)instr[i]).owner)){
						Type[] descRaw = Type.getArgumentTypes(mn.desc);
						ArrayList<String> desc = new ArrayList<String>();
						for (Type ty : descRaw) {
							desc.add(ty.toString().replaceAll("(\\w)+;", "object"));
						}

						// Arrays.toString().replaceAll("(\\w)+;", "object");
						String returnT = Type.getReturnType(mn.desc).toString().replaceAll("L(\\w)+;", "object");
						int complexity = (new Complexity()).getCyclomaticComplexity(cn.name, mn);
						MethodIdent mi = new MethodIdent(mn.name, cn.name, returnT, complexity,desc);
						mets.add(mi);
						i=instr.length+1;
					}
				}
			}
		}
		return mets;
	}
	
	public static boolean containField(ArrayList<FieldNode> fields, String fName){
		for(FieldNode fn : fields){
			if(fn.name.equals(fName))
				return true;
		}
		return false;
	}
	
	public static ArrayList<String> getMethodsthatUse(MethodNode mn, String field,String owner){
		ArrayList<String> patters = new ArrayList<String>();
		Stack<Integer> mustVisit = new Stack<Integer>();
		return patters;
	}
	
	public static ArrayList<Integer[]> getMethodInsnFlow(MethodNode mn, String field) {
		AbstractInsnNode instr[] = mn.instructions.toArray();
		ArrayList<Integer> visitedLabels = new ArrayList<Integer>();
		Stack<Integer> mustVisit = new Stack<Integer>();
		ArrayList<Integer[]> patters = new ArrayList<Integer[]>();
		HashMap<Integer, Integer> records = new HashMap<Integer, Integer>();
		
		int count =0;
		
		for (int i = 0; i < instr.length; i++) {
			boolean ignore = false;
			final int opcode = instr[i].getOpcode();
			final String instruct = opcode == -1 ? ""
					: Printer.OPCODES[instr[i].getOpcode()];
			
			
			switch (instr[i].getType()) {
			case AbstractInsnNode.LABEL:
				ignore = true;
				break;
			case AbstractInsnNode.FRAME:
				ignore = true;
				break;
			case AbstractInsnNode.LINE:
				ignore = true;
			case AbstractInsnNode.INSN:
				break;
			case AbstractInsnNode.JUMP_INSN: {
				final LabelNode targetInstruction = ((JumpInsnNode) instr[i]).label;
				final int targetId = mn.instructions.indexOf(targetInstruction);

				if (opcode == Opcodes.GOTO && !visitedLabels.contains(targetId)) {
					visitedLabels.add(targetId);
					i = targetId;
				} else if (!visitedLabels.contains(targetId)) {

					visitedLabels.add(targetId);
					mustVisit.push(targetId);
				}
				ignore = true;
				break;
			}
			case AbstractInsnNode.FIELD_INSN:
				String actFields = ((FieldInsnNode)instr[i]).owner+"."+((FieldInsnNode)instr[i]).name;
				if(actFields.equals(field)){
					if(records.size()>5){
						Integer addMe[] = new Integer[7];
						for(int j = 6;j>=0;j--){
							addMe[j]=records.get(count-j);
						}
						patters.add(addMe);
						//patters.add(records.get(count-4)+" "+records.get(count-3)+" "+records.get(count-2)+" "+records.get(count-1)+" "+records.get(count));
					}
				}
				break;
			}
			if (!ignore) {
				count ++;
				records.put(count, opcode);
			}
			if (i == instr.length - 1 && !mustVisit.empty()) {
				i = mustVisit.pop();
			}

		}
		
		return patters;
	}
}
