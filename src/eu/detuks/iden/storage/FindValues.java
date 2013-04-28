package eu.detuks.iden.storage;

import java.util.ArrayList;
import java.util.HashMap;

public class FindValues {
	public HashMap<String, ArrayList<String> > updateMe = new HashMap<String, ArrayList<String> >();
	
	public FindValues() {
		ArrayList<String> dt = new ArrayList<String>();
		dt.add("d");
		dt.add("y");
		dt.add("e");
		dt.add("x");
		dt.add("i");
		dt.add("z");
		dt.add("t");
		dt.add("o");
		dt.add("u");
		dt.add("f");
		dt.add("k");
		updateMe.put("dt model",dt);
		
		ArrayList<String> v = new ArrayList<String>();
		v.add("j");
		v.add("r");
		updateMe.put("v player",v);
		
		ArrayList<String> am = new ArrayList<String>();
		am.add("bq");
		am.add("ah");
		am.add("ag");
		am.add("ak");
		am.add("ay");
		am.add("c");
		am.add("aa");
		am.add("bv");
		am.add("bw");
		am.add("av");
		am.add("h");
		updateMe.put("am Actor",am);
	}
}
