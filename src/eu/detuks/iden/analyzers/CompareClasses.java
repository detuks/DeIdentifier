package eu.detuks.iden.analyzers;

import java.util.ArrayList;
import java.util.HashMap;

import eu.detuks.iden.results.RetClientIdent;
import eu.detuks.iden.results.RetFieldIdent;
import eu.detuks.iden.results.RetMethodIdent;
import eu.detuks.iden.storage.ClassIdent;
import eu.detuks.iden.storage.FieldIdent;
import eu.detuks.iden.storage.MethodIdent;

public class CompareClasses {
	
	public static ArrayList<RetClientIdent> superCompare(HashMap<String, ClassIdent> map1,HashMap<String, ClassIdent> map2){
		ArrayList<RetClientIdent> cIdent = new ArrayList<RetClientIdent>();
		//System.out.println("map1: "+map1.size()+ " map2: "+map2.size());
		for(ClassIdent ci1 : map1.values()){
			final int cMax = ci1.fields.size()*10+20;
			int cProc =0;
			String cBestS = "";
			int cBestI = -999;
			ArrayList<RetMethodIdent> retMethods = new ArrayList<RetMethodIdent>();
			HashMap<String, RetFieldIdent> fieldies = new HashMap<String, RetFieldIdent>();
			for(ClassIdent ci2 : map2.values()){
				if(ci1.haveSupeClass.equals(ci2.haveSupeClass)){
					cProc += 20;
				}
				for(FieldIdent fi1 : ci1.fields){
					
				}
				
				if(cProc>cBestI){
					cBestI = cProc;
					cBestS = ci2.name;
				}
			}
			RetClientIdent rci = new RetClientIdent(ci1.name, cBestS, (cBestI*100)/cMax, retMethods, fieldies);
			cIdent.add(rci);
		}
		
		return cIdent;
	}
	
	public static ArrayList<RetFieldIdent> testCompare(ClassIdent cn1, ClassIdent cn2){
		ArrayList<RetFieldIdent> arfi = new ArrayList<RetFieldIdent>();
		for(FieldIdent fi1 : cn1.fields){
			int bestM = 0;
			String best ="yolo";
			for(FieldIdent fi2 : cn2.fields){
				int temp = compareFields(fi1, fi2);
				//if(fi1.name.equals("ay") && fi2.name.equals("ad"))
				//	System.out.println("yolooo "+temp +"%");
				if(temp>bestM){
					bestM=temp;
					best = fi2.name;
				}
			}
			RetFieldIdent rfi = new RetFieldIdent(fi1.name, best, bestM);
			arfi.add(rfi);
		}
		return arfi;
	}
	
	public static int compareFields(FieldIdent f1, FieldIdent f2){
		int equality = 0;
		int max = 270;
		ArrayList<String> compared = new ArrayList<String>();
		if(!f1.desc.equals(f2.desc))
		{
			return 0;
		}else{
			equality+=20;
		}
		int totaly =0;
		for(Integer[] i1 : f1.patterns){
			int best =0;
		
			for(Integer[] i2 : f2.patterns){
				int tempy =0;
				for(int i =0;i<5;i++){
					if(i1[i]==i2[i])
						tempy+=20;
				}
				if(tempy>best){
					best = tempy;
				}
			}
			totaly+=best;
		}
		if(f1.patterns.size()>0)
			equality+=(totaly/f1.patterns.size());
		else if(f2.patterns.size()<10)
				equality+=100-f2.patterns.size()*10;
		
		
		ArrayList<MethodIdent> tempA = new ArrayList<MethodIdent>();
		for(MethodIdent miT : f2.methodOwning){
			tempA.add(miT);
		}
		int mTotal = 0;
		for(MethodIdent mi1 : f1.methodOwning){
			int bestMI = 0;
			MethodIdent best = null;
			for(MethodIdent mi2 : tempA){
				int temp = compareMethods(mi1, mi2);
				if(temp>bestMI){
					
					bestMI=temp;
					best = mi2;
				}
			}
			
			mTotal+=bestMI;
			tempA.remove(best);
		}
		
		float koif = 1;
		if(f1.methodOwning.size() !=0 &&  f2.methodOwning.size()!=0){
			if(f1.methodOwning.size()> f2.methodOwning.size()){
				koif = f2.methodOwning.size()/f1.methodOwning.size();
			}else{
				koif = f1.methodOwning.size()/f2.methodOwning.size();
			}
		}

		
		int metAprox =0;
		if(f1.methodOwning.size()!=0){
			metAprox =(int) (((mTotal*koif)/f1.methodOwning.size()));
		}else{
			metAprox = 100-(f2.methodOwning.size()*10);
		}
		
		equality+= metAprox;
		
		
		return (equality*100)/max;
	}
	
	public static int compareMethods(MethodIdent mi1, MethodIdent mi2){
		String[] desc2 = mi2.desc.toArray(new String[] {});
		int startDesc2Lenght = desc2.length;
		int eqv = 0;
		int del =0;
		int max = mi1.desc.size()*10 + 10;
		if(!mi1.returnType.equals(mi2.returnType))
			return 0;
		else
			eqv+=10;
		int equality =0;
		for (String desc1 : mi1.desc) {
			for (int i = 0; i < desc2.length; i++) {
				if(desc2[i].equals(desc1)){
					desc2[i]="yomom";
					del++;
					eqv+=10;
					break;
				}
			}
		}
		if(del==startDesc2Lenght){
			return (eqv*100)/max;
		}else{
			return ((eqv*100)/max)*(startDesc2Lenght-del)/startDesc2Lenght;
		}
	}
}
