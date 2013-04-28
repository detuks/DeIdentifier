package eu.detuks.iden.util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class JarUtils {
	
	public static HashMap<String, ClassNode> getClasses(JarFile jarFile) throws Exception{
		HashMap<String, ClassNode> classes = new HashMap<String, ClassNode>();
		 Enumeration<JarEntry> enumeration = jarFile.entries();
		while(enumeration.hasMoreElements()){
			JarEntry entry = enumeration.nextElement();
			if(entry.getName().endsWith(".class")){
				ClassReader classReader = new ClassReader(jarFile.getInputStream(entry));
				ClassNode classNode = new ClassNode();
				classReader.accept(classNode, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
				classes.put(classNode.name, classNode);
			}
		}
		return classes;	
	}
	
	public static void dumpClasses(File file, HashMap<String, ClassNode> classes) {
		  try {
		   ClassWriter cw;
		   JarOutputStream out = new JarOutputStream(
		         new FileOutputStream(file));
		   for (ClassNode cn : classes.values()) {
		        JarEntry entry = new JarEntry(cn.name + ".class");
		        out.putNextEntry(entry);
		        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		        cn.accept(writer); // cn being the classnode.
		        writer.toByteArray();
		        byte[] by = writer.toByteArray();
		        out.write(by);
		   }
		   out.close();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		}
}
