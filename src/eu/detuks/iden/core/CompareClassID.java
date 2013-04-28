package eu.detuks.iden.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import eu.detuks.iden.storage.ClassIdent;
import eu.detuks.iden.storage.IdenResult;

public class CompareClassID {
	
	public ArrayList<IdenResult> getResults(HashMap<String, ClassIdent> cMap1, HashMap<String, ClassIdent> cMap2){
		ArrayList<IdenResult> result = new ArrayList<IdenResult>();
		System.out.println("cMap: "+cMap1.size()+ " cMap2: "+cMap2.size());
		ClassIdent[] ciA = cMap2.values().toArray(new ClassIdent[]{});
		int temp=0;
		for(ClassIdent ci1 : cMap1.values()){
			String name = ci1.name;
			//if(!Identify.classesToFind.contains(name))
			//	continue;
			String bestS = "";
			int bestI = -9999;
			temp =0;
			for(int i =0;i<ciA.length;i++){				
				temp = compare(ci1,ciA[i]);
				if(bestI<temp){
					bestI = temp;
					bestS = ciA[i].name;
					//System.out.println(name+" "+bestS+" "+bestI);
				}
			}
			//cMap2.remove(bestS);
			IdenResult id = new IdenResult(name, bestS, bestI);
			result.add(id);
		}
		return result;
	}
	
	public int compare ( ClassIdent ci1, ClassIdent ci2){
		//int perc =0;
		//int max = ci1.fieldDesc.size() +ci1.methodDesc.size()+ci1.methodReturn.size()+10;
		//System.out.println("fd1: "+Arrays.toString(ci1.methodReturn.toArray()));
		//System.out.println("fd2: "+Arrays.toString(ci2.methodReturn.toArray()));
		//perc+= fieldComp(ci1.fieldDesc, ci2.fieldDesc);
		//perc+= fieldComp(ci1.methodDesc, ci2.methodDesc);
		//perc+= fieldComp(ci1.methodReturn, ci2.methodReturn);
		//if(ci1.haveSupeClass.equals(ci2.haveSupeClass))
		//	perc+=10;

		
		
		
		//return (perc*100)/max;
		return 0;
	}
	
	int fieldComp(ArrayList<String> fd1, ArrayList<String> fd2){
		int resl = 0;
		ArrayList<String> fd2t = new ArrayList<String>();
		for(String s : fd2){
			fd2t.add(s);
		}
		String[] s1 = fd1.toArray(new String[]{});
		for(int i=0; i<s1.length;i++){
			if(fd2t.contains(s1[i])){
				//System.out.println("removed: "+s1);
				resl++;
				fd2t.remove(s1[i]);
			}
		}
		resl -= fd2t.size();
		return resl;
	}
	
	
}
