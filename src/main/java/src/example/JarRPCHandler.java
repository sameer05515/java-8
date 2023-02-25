package example;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class JarRPCHandler {

//	static String destDir = PropertyUtil.getInstance().getValueForKey(
			//"serverLibPath");
	static String destDir = "test-libs/";

	public static void main(String[] args) throws ClassNotFoundException {

        JarRPCHandler.getAllClassName("test-jar.jar").forEach(out::println);
		JarDTO jarDTO=new JarDTO();
		jarDTO.setJar_id(1);
		jarDTO.setJar_name("test-jar.jar");
		List<ClassDTO> allClassInJar = JarRPCHandler.getAllClassInJar(jarDTO);
		List<String> list = allClassInJar.stream().map(ClassDTO::getClassName).collect(Collectors.toList());
		list.stream().limit(1).forEach(System.out::println);
		String oneClass="example.JavaDuplicated1";
		Class<?> c = Class.forName(oneClass);
		MethodParameterSpy.printClassMethods(c);
	}
	//static Logger log = Logger.getLogger(JarRPCHandler.class);

	/**
	 * Gives names of all classes in a given jar file
	 *
	 * @param jarName
	 *            name of jar file in which we check for classes
	 * @return List of all required classes' name
	 */

	public static List<String> getAllClassName(String jarName) {
		ArrayList<String> classes = new ArrayList<>();
		jarName = destDir + jarName;
		System.out.println("Jar " + jarName + " looking for all classes");

		File file = new File(jarName);
		JarInputStream jarFile = null;
		try {
			if (file.exists()) {
				jarFile = new JarInputStream(Files.newInputStream(file.toPath()));
				JarEntry jarEntry;
				boolean flag = true;
				while (flag) {
					jarEntry = jarFile.getNextJarEntry();
					if (jarEntry == null) {
						flag = false;
						break;
					}
					String classname = jarEntry.getName()
							.replaceAll("/", "\\.");
					if (classname.endsWith(".class")) {
						Class<?> c = Class.forName(classname.substring(0,
								classname.indexOf(".class")));
						classes.add(classname.substring(0,
								classname.indexOf(".class")));
					}
				}
			}
		} catch (Exception e) {
			System.out.println();
			System.out.println("Exception occured while getting class names form jar : "
					+ jarName);
			e.printStackTrace();
		} finally {
			try {
				if (jarFile != null) {
					jarFile.close();
				}
			} catch (Exception e) {
			}
		}
		System.out.println("classes found : " + classes);
		return classes;
	}

	/**
	 * Gives names of all classes in a given jar file which are implementing a
	 * given interface
	 *
	 * @param objJarDTO
	 *            DTO of jar file in which we check for classes
	 * @return List of all required classes' name
	 */
	public static List<ClassDTO> getAllClassInJar(JarDTO objJarDTO) {
		ArrayList<ClassDTO> classes = new ArrayList<>();
		String jarName = destDir + objJarDTO.getJar_name();
		System.out.println("Jar " + jarName + " looking for classes");
		File file = new File(jarName);
		JarInputStream jarFile = null;

		try {
			if (file.exists()) {
				jarFile = new JarInputStream(Files.newInputStream(file.toPath()));
				JarEntry jarEntry;
				while (true) {
					jarEntry = jarFile.getNextJarEntry();
					if (jarEntry == null) {
						break;
					}
					String classname = jarEntry.getName()
							.replaceAll("/", "\\.");
					if (classname.endsWith(".class")) {
						Class<?> c = Class.forName(classname.substring(0,
								classname.indexOf(".class")));
						if (!c.isInterface()) {
							// Class<?>[] interfaces = c.getInterfaces();

							// for (int z = 0; z < interfaces.length; z++) {
							// if
							// (interfaces[z].getName().equals(interfaceName)) {
							ClassDTO objClassDTO = new ClassDTO();
							objClassDTO.setLinked_jar_id(objJarDTO.getJar_id());
							objClassDTO.setLinkedJarName(objJarDTO
									.getJar_name());
							objClassDTO.setClassName(classname.substring(0,
									classname.indexOf(".class")));
							classes.add(objClassDTO);
							// }
							// }
						}

					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occured while getting class names form jar : "
					+ jarName);
		} finally {
			try {
				if (jarFile != null) {
					jarFile.close();
				}
			} catch (Exception e) {
			}
		}
		System.out.println("All classes found in jar :- " + classes);
		return classes;
	}

	/**
	 * Gives names of all public methods in a given class file name
	 *
	 * @param className
	 *            name of class file in which we check for methods
	 * @return List of all required methods
	 */
//	public static List<PublicMethodDTO> getPublicMethodsInClass(
//			ClassDTO objClassDTO) {
//		ArrayList<PublicMethodDTO> publicMethods = new ArrayList<PublicMethodDTO>();
//		try {
//			String className = objClassDTO.getClassName();
//			Class<?> c = Class.forName(className);
//			Method[] methods = c.getDeclaredMethods();
//			if (methods.length > 0) {
//				for (int z = 0; z < methods.length; z++) {
//					int mod = methods[z].getModifiers();
//					if (Modifier.isPublic(mod)) {
//						PublicMethodDTO objPublicMethodDTO = new PublicMethodDTO();
//						Class<?> pvec[] = methods[z].getParameterTypes();
//						List<ParameterDescDTO> inputType = new ArrayList<ParameterDescDTO>();
//						for (int j = 0; j < pvec.length; j++) {
//							ParameterDescDTO objParamDesc = new ParameterDescDTO();
//							objParamDesc.setParameterType(pvec[j].getName());
//							inputType.add(objParamDesc);
//						}
//
//						try {
//							String[] names = Snippet
//									.getParameterNames(methods[z]);
//							for (int index = 0; index < names.length; index++) {
//								ParameterDescDTO objParamDesc = inputType
//										.get(index);
//								objParamDesc.setParameterName(names[index]);
//								inputType.remove(index);
//								inputType.add(index, objParamDesc);
//							}
//						} catch (IOException e) {
//							e.printStackTrace();
//							System.out.println("IOException from here");
//						} catch (Exception e) {
//							e.printStackTrace();
//							System.out.println("IOException from here");
//						} catch (Throwable e) {
//							e.printStackTrace();
//							System.out.println("IOException from here");
//						}
//						objPublicMethodDTO.setLinked_jar_id(objClassDTO
//								.getLinked_jar_id());
//						objPublicMethodDTO.setClassName(objClassDTO
//								.getClassName());
//						objPublicMethodDTO.setMethodName(methods[z].getName());
//						objPublicMethodDTO.setInputType(inputType);
//						publicMethods.add(objPublicMethodDTO);
//					}
//				}
//			}
//		} catch (ClassNotFoundException e) {
//			System.out.println("Class not found : ");
//		} catch (Exception e) {
//			System.out.println("Error occured : ");
//		}
//		System.out.println("public methods found in " + objClassDTO.getClassName()
//				+ " are : " + publicMethods);
//		return publicMethods;
//	}
}

class PublicMethodDTO {

	private int linked_jar_id;
	private String className;
	private String methodName;
	private List<ParameterDescDTO> inputType=new ArrayList<ParameterDescDTO>();
	private List<ParameterDescDTO> returnType=new ArrayList<ParameterDescDTO>();
	public int getLinked_jar_id() {
		return linked_jar_id;
	}
	public void setLinked_jar_id(int linked_jar_id) {
		this.linked_jar_id = linked_jar_id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	///////////////
	public List<ParameterDescDTO> getInputType() {
		return inputType;
	}
	public void setInputType(List<ParameterDescDTO> inputType) {
		this.inputType = inputType;
	}
	public List<ParameterDescDTO> getReturnType() {
		return returnType;
	}
	public void setReturnType(List<ParameterDescDTO> returnType) {
		this.returnType = returnType;
	}
}

class ParameterDescDTO extends IAgentServiceParameter{

	private int paramId;
	private int serviceId;
	private String parameterName;
	private String parameterType;
	private String parameterDescr;
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getParameterDescr() {
		return parameterDescr;
	}
	public void setParameterDescr(String parameterDescr) {
		this.parameterDescr = parameterDescr;
	}
	public int getParamId() {
		return paramId;
	}
	public void setParamId(int paramId) {
		this.paramId = paramId;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

}


class JarDTO {

	int jar_id;
	String jar_name;
	boolean jarExistsOnServer;

	public int getJar_id() {
		return jar_id;
	}

	public void setJar_id(int jar_id) {
		this.jar_id = jar_id;
	}

	public String getJar_name() {
		return jar_name;
	}

	public void setJar_name(String jar_name) {
		this.jar_name = jar_name;
	}

	public boolean isJarExistsOnServer() {
		return jarExistsOnServer;
	}

	public void setJarExistsOnServer(boolean jarExistsOnServer) {
		this.jarExistsOnServer = jarExistsOnServer;
	}
}

abstract class IAgentServiceParameter {
	public abstract int getParamId();
	public abstract int getServiceId();
	public abstract String getParameterName();
	public abstract String getParameterType();
	public abstract String getParameterDescr();
}

class ClassDTO {

	private int linked_jar_id;
	private String linkedJarName;
	private String className;
	public int getLinked_jar_id() {
		return linked_jar_id;
	}
	public void setLinked_jar_id(int linked_jar_id) {
		this.linked_jar_id = linked_jar_id;
	}
	public String getLinkedJarName() {
		return linkedJarName;
	}
	public void setLinkedJarName(String linkedJarName) {
		this.linkedJarName = linkedJarName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}


}

//class Snippet {
//
//	public static String[] getParameterNames(Method m) throws IOException {
//		Class<?> declaringClass = m.getDeclaringClass();
//		String resourceName = "/" + declaringClass.getName().replace('.', '/')
//				+ ".class";
//		InputStream classData = declaringClass
//				.getResourceAsStream(resourceName);
//
//		VariableReader variableDiscoverer = new VariableReader();
//
//		ClassReader r = new ClassReader(classData);
//		r.accept(variableDiscoverer, ClassReader.EXPAND_FRAMES);
//
//		Map<Integer, String> variableNames = variableDiscoverer
//				.getVariableNames(m);
//		String[] parameterNames = new String[m.getParameterTypes().length];
//		for (int i = 0,j=0; i < parameterNames.length; i++,j++) {
//			if(variableNames.get(j).toString().equals("this")){
//				j++;
//			}
//			parameterNames[i] = variableNames.get(j);
//		}
//		return parameterNames;
//	}
//}
//
//public class VariableReader extends EmptyVisitor {
//	private Map<String, Map<Integer, String>> methodParameters = new HashMap<String, Map<Integer, String>>();
//	private String currentMethod;
//
//	public MethodVisitor visitMethod(int access, String name, String desc,
//									 String signature, String[] exceptions) {
//
//		currentMethod = name + desc;
//		return this;
//
//	}
//
//	public void visitLocalVariable(String name, String desc, String signature,
//								   Label start, Label end, int index) {
//		Map<Integer, String> parameters = methodParameters.get(currentMethod);
//		if (parameters == null) {
//			parameters = new HashMap<Integer, String>();
//			methodParameters.put(currentMethod, parameters);
//		}
//		parameters.put(index, name);
//	}
//
//	public Map<Integer, String> getVariableNames(Method m) {
//		Map<Integer, String> ret = methodParameters.get(m.getName()
//				+ Type.getMethodDescriptor(m));
//		return methodParameters.get(m.getName() + Type.getMethodDescriptor(m));
//	}
//
//}



class MethodParameterSpy {

	private static final String  fmt = "%24s: %s%n";

	// for the morbidly curious
	<E extends RuntimeException> void genericThrow() throws E {}

	public static void printClassConstructors(Class c) {
		Constructor[] allConstructors = c.getConstructors();
		out.format(fmt, "Number of constructors", allConstructors.length);
		for (Constructor currentConstructor : allConstructors) {
			printConstructor(currentConstructor);
		}
		Constructor[] allDeclConst = c.getDeclaredConstructors();
		out.format(fmt, "Number of declared constructors",
				allDeclConst.length);
		for (Constructor currentDeclConst : allDeclConst) {
			printConstructor(currentDeclConst);
		}
	}

	public static void printClassMethods(Class c) {
		Method[] allMethods = c.getDeclaredMethods();
		out.format(fmt, "Number of methods", allMethods.length);
		for (Method m : allMethods) {
			printMethod(m);
		}
	}

	public static void printConstructor(Constructor c) {
		out.format("%s%n", c.toGenericString());
		Parameter[] params = c.getParameters();
		out.format(fmt, "Number of parameters", params.length);
		for (int i = 0; i < params.length; i++) {
			printParameter(params[i]);
		}
	}

	public static void printMethod(Method m) {
		out.format("%s%n", m.toGenericString());
		out.format(fmt, "Return type", m.getReturnType());
		out.format(fmt, "Generic return type", m.getGenericReturnType());

		Parameter[] params = m.getParameters();
		for (int i = 0; i < params.length; i++) {
			printParameter(params[i]);
		}
	}

	public static void printParameter(Parameter p) {
		out.format(fmt, "Parameter class", p.getType());
		out.format(fmt, "Parameter name", p.getName());
		out.format(fmt, "Modifiers", p.getModifiers());
		out.format(fmt, "Is implicit?", p.isImplicit());
		out.format(fmt, "Is name present?", p.isNamePresent());
		out.format(fmt, "Is synthetic?", p.isSynthetic());
	}

	public static void main(String... args) {

		try {
			printClassConstructors(Class.forName(args[0]));
			printClassMethods(Class.forName(args[0]));
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		}
	}
}