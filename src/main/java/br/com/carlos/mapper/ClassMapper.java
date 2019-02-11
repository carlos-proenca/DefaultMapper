package br.com.carlos.mapper;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import br.com.carlos.annotations.CreateMapper;
import br.com.carlos.log.DevLog;

public class ClassMapper {

	private Element element;
	private ProcessingEnvironment processingEnvironment;
	private Elements elementUtils;
	
	private String fromClass = "";
	private String toClass = "";
	
	public ClassMapper(final Element element, final ProcessingEnvironment processingEnvironment, final Elements elementUtils) {
		this.element = element;
		this.elementUtils = elementUtils;
		this.processingEnvironment = processingEnvironment;
	}

	public String getFromClassName() {
		return this.fromClass;
	}

	public String getClassContent() {
		
		DevLog.log(" ======================================================== ");
		Map<String, String> mappedFields = new HashMap<>();
		CreateMapper mapperAnnotation = element.getAnnotation(CreateMapper.class);
		
		TypeElement annotatedClass = findEnclosingTypeElement(element);
		DevLog.log("\n Annotated class "+annotatedClass.getQualifiedName());
		this.toClass = annotatedClass.getQualifiedName().toString();
		
		DevLog.log("\n\n");
		for (Element elemento : elementUtils.getAllMembers(annotatedClass)) {
			DevLog.log("\n ==> The elements is = "+elemento);
			for (AnnotationMirror mirror : elementUtils.getAllAnnotationMirrors(elemento)) {
				if(mirror.getAnnotationType().toString().equalsIgnoreCase("br.com.carlos.annotations.MapTo")) {
					DevLog.log("\n ====> Is Map To = true");
					String value = (String) mirror.getElementValues().values().iterator().next().getValue();
					mappedFields.put(elemento.toString(), value);
				}else {
					DevLog.log("\n ====> Is Map To = false");
				}
				DevLog.log("\n\n");
			}
	    }
		
		this.fromClass = getAnnotationValue(mapperAnnotation).toString();
//		writeMapperClass(fromClass, toClass, mappedFields);
		DevLog.log("\n\n======================================================\n\n");
		
		return "";
	}
	
	private TypeElement findEnclosingTypeElement(Element e){
		while (e != null && !(e instanceof TypeElement)){
			e = e.getEnclosingElement();
		}
		return TypeElement.class.cast(e);
	}
	
	private TypeMirror getAnnotationValue(final CreateMapper annotation) {
		try {
			annotation.value(); // this should throw
		} catch (MirroredTypeException mte) {
			return mte.getTypeMirror();
		}
		return null;
	}
	
//	@Override
//	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//
//		
//		DevLog.log("\n\n");
//		DevLog.log(" ======================================================== ");
//		DevLog.log("#process(...) in " + this.getClass().getSimpleName());
//		DevLog.log(" ======================================================== ");
//
//		for (TypeElement annotation : annotations) {
//			try {
//				for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
//					DevLog.log(" ======================================================== ");
//					String toClass = "";
//					String fromClass = "";
//					Map<String, String> mappedFields = new HashMap<>();
//					CreateMapper mapperAnnotation = element.getAnnotation(CreateMapper.class);
//					
//					TypeElement annotatedClass = findEnclosingTypeElement(element);
//					DevLog.log("\n Annotated class "+annotatedClass.getQualifiedName());
//					toClass = annotatedClass.getQualifiedName().toString();
//					
//					DevLog.log("\n\n");
//					for (Element elemento : elementUtils.getAllMembers(annotatedClass)) {
//						DevLog.log("\n ==> The elements is = "+elemento);
//						for (AnnotationMirror mirror : elementUtils.getAllAnnotationMirrors(elemento)) {
//							if(mirror.getAnnotationType().toString().equalsIgnoreCase("br.com.carlos.annotations.MapTo")) {
//								DevLog.log("\n ====> Is Map To = true");
//								String value = (String) mirror.getElementValues().values().iterator().next().getValue();
//								mappedFields.put(elemento.toString(), value);
//							}else {
//								DevLog.log("\n ====> Is Map To = false");
//							}
//							DevLog.log("\n\n");
//						}
//				    }
//					
//					TypeMirror classValue = getMyValue1(mapperAnnotation);
//					fromClass = classValue.toString();
//					writeMapperClass(fromClass, toClass, mappedFields);
//					DevLog.log("\n\n======================================================\n\n");
//				}
//			} catch (Exception e) {
//				DevLog.log(" Error ====== " + e.getLocalizedMessage());
//			}
//		}
//
//		return true;
//	}
//

//
//
//	private void writeMapperClass(final String fromClass, final String toClass, Map<String, String> mappedFields) throws IOException {
//		String packageName = null;
//		int lastDot = fromClass.lastIndexOf('.');
//		if (lastDot > 0) {
//			packageName = fromClass.substring(0, lastDot);
//		}
//
//		String fromClassWithoutPackage = uncaptalize(toClass.substring(toClass.lastIndexOf('.') + 1));
//		String builderSimpleClassName = fromClass.substring(lastDot + 1);
//		JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(fromClass+"Mapper");
//		
//		cleanFile(builderFile);
//		
//		try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
//			out.write("");
//			if (packageName != null) {
//				out.print("package ");
//				out.print(packageName);
//				out.println(";");
//				out.println();
//				DevLog.log("package " + packageName + ";");
//				DevLog.log("\n");
//			}
//
//			String classDeclaration = "public class "+builderSimpleClassName+"Mapper {\n\n";
//			out.println(classDeclaration);
//			DevLog.log(classDeclaration);
//			
//			String classMapMethod = "    public "+fromClass+" map("+toClass+" "+fromClassWithoutPackage+"){\n\n";
//			out.println(classMapMethod);
//			DevLog.log(classMapMethod);
//			
//			
//			StringBuilder content = new StringBuilder();
//			String fromClassNameWithoutPackage = uncaptalize(fromClass.substring(fromClass.lastIndexOf('.') + 1));
//			content.append( "        "+fromClass+" "+fromClassNameWithoutPackage+" = new "+fromClass+"();\n\n");
//			
//			for (String key : mappedFields.keySet()) {
//				content.append("        "+fromClassNameWithoutPackage+".set"+captalize(mappedFields.get(key))+"( "+fromClassWithoutPackage+".get"+captalize(key)+"()"+");\n");
//			}
//
//			out.println("\n\n");
//			DevLog.log("\n\n");
//			
//			content.append("        return "+fromClassNameWithoutPackage+";");
//			out.println(content);
//			DevLog.log(content);
//			
//			out.println("    }");
//			DevLog.log("    }\n");
//
//			out.println("}");
//			DevLog.log("}\n");
//		}
//
//	}
//	
//	private String captalize(String input) {
//		return input.substring(0, 1).toUpperCase() + input.substring(1);
//	}
//	
//	private String uncaptalize(String input) {
//		return input.substring(0, 1).toLowerCase() + input.substring(1);
//	}
//
}