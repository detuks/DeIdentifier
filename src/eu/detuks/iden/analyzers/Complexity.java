package eu.detuks.iden.analyzers;

import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.Frame;


public class Complexity {
	public int getCyclomaticComplexity(String owner, MethodNode mn)
			throws AnalyzerException{
				Analyzer a = new Analyzer(new BasicInterpreter()){
					protected Frame newFrame(int nLocals, int nStack){
						return new Node(nLocals, nStack);
					}
					protected Frame newFrame(Frame src){
						return new Node(src);
					}
					protected void newControlFlowEdge(int src, int dst){
						Node s = (Node) getFrames()[src];
						s.successors.add((Node) getFrames()[dst]);
					}
				};
				a.analyze(owner, mn);
				Frame[] frames = a.getFrames();
				int edges = 0;
				int nodes = 0;
				for(int i=0; i<frames.length; ++i){
					if(frames.length>1 && frames[1] != null){
						//System.out.println("stack size: "+frames[i].getStackSize());
						edges+= ((Node) frames[i]).successors.size();
						nodes+= 1;
					}
				}
				
				return edges-nodes +2;
			}
}
