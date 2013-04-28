package eu.detuks.iden.analyzers;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.Value;

public class Node<V extends Value> extends Frame {
	Set< Node<V> > successors = new HashSet< Node<V> >();
	public Node(int nLocals, int nStack) {
		super(nLocals, nStack);
	}
	
	public Node( Frame src){
		super(src);
	}
}
