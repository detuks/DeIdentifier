package eu.detuks.iden.analyzers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;

public class test {
	
	public static boolean containsField(String field, AbstractInsnNode instr[]){
		for (int i = 0; i < instr.length; i++) {
			switch (instr[i].getType()) {
			case AbstractInsnNode.FIELD_INSN:
				String fieldt = ((FieldInsnNode)instr[i]).owner+"."+((FieldInsnNode)instr[i]).name;
				if(field.equals(fieldt))
					return true;
				break;
			}
		}
		
		return false;
	}
	/*
	 * Method to search
	 * known field name like "client.hg"
	 */
	public static String getMethodInsnFlow(MethodNode mn, String field) {
		//puts instructions in array
		AbstractInsnNode instr[] = mn.instructions.toArray();
		if(!containsField(field, instr))
			return null;
		
		String current = " ";
		String superString = "";
		String fields = "";

		ArrayList<Integer> visitedLabels = new ArrayList<Integer>();
		HashMap<Integer, String> records = new HashMap<Integer, String>();
		
		Stack<Integer> mustVisit = new Stack<Integer>();
		int returnType;
		//get what kind of return we need
		String[] splity = mn.desc.split("\\)");
		if (splity[1].equals("V")) {
			returnType = Opcodes.RETURN;

		} else if (splity[1].equals("I") || splity[1].equals("C")
				|| splity[1].equals("Z") || splity[1].equals("B")
				|| splity[1].equals("S")) {
			returnType = Opcodes.IRETURN;
		} else if (splity[1].equals("D") || splity[1].equals("J")) {
			returnType = Opcodes.LRETURN;
		} else {
			returnType = Opcodes.ARETURN;
		}
		//check so don't get in loop
		int tooMuch = 0;
		boolean biFound = false;
		//go through all instructions
		for (int i = 0; i < instr.length; i++) {
			
			tooMuch++;
			//get so string would be between BIPUSH and SIPUSH
			final int opcode = instr[i].getOpcode();
			if(opcode == Opcodes.BIPUSH){
				current = "";
				biFound = true;
			}else if(opcode == Opcodes.SIPUSH && biFound){
				if(current.contains(field)){
					current = "";
					biFound=false;
				}
			}
			final String instruct = opcode == -1 ? ""
					: Printer.OPCODES[instr[i].getOpcode()];
			boolean ignore = false;
			if ((opcode != -1 && tooMuch > instr.length + 1)
					|| (opcode != -1 && Printer.OPCODES[instr[i].getOpcode()]
							.contains("RETURN"))) {

				current += " " + Printer.OPCODES[instr[i].getOpcode()];
				if (!mustVisit.empty()) {
					int nextLine = mustVisit.pop();
					i = nextLine;
					if (opcode == returnType) {
						biFound = false;
						if(current.contains(field)){
							superString+= " % "+current;
						}
					}
					current = records.get(nextLine);
				} else {
					biFound = false;
					if(current.contains(field)){
						superString+= " % "+current;
					}
					break;
				}
			}
			//what kind of insn
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
					records.put(targetId, current);
				}
				ignore = true;
				break;
			}
			case AbstractInsnNode.FIELD_INSN:
				fields = " "+((FieldInsnNode)instr[i]).owner+"."+((FieldInsnNode)instr[i]).name;
				if(!fields.equals(" "+field)){
					fields = "";
				}
				break;
			}
			if (!ignore) {
				current += " " + instruct + fields;
				fields = "";
			}
			if (i == instr.length - 1 && !mustVisit.empty()) {
				i = mustVisit.pop();
			}

		}
		
		if(superString.contains(field)){
			return superString.replace(field, "&&&&");
		}else{
			return null;
		}
	}
}
