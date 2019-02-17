package br.com.carlos.mapper;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import br.com.carlos.util.AnnotationUtil;

/**
 * Test of Create Mapper Test class.
 * 
 * Tests business logic of annotation processor class creation
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
public class MapMethod {
	
	private static final String MAP_TO_ANNOTATION = "br.com.carlos.annotations.MapTo";
	
	private String toClassName;
	private String toClassWithoutPackage;
	
	private String fromClassName;
	private String fromClassWithoutPackage;
	
	private Map<String, String> mappedFields = new HashMap<>();
	
	private Elements elementUtils;
	
	public MapMethod(final Element element, final Elements elementUtils ) {
		this.elementUtils = elementUtils;
		TypeElement annotatedClass = AnnotationUtil.findEnclosingTypeElement(element);
		
		this.fromClassName = AnnotationUtil.getCreateMapperValue(element);
		this.fromClassWithoutPackage = getPathWithoutPackage(this.fromClassName);
		
		this.toClassName = annotatedClass.getQualifiedName().toString();
		this.toClassWithoutPackage = getPathWithoutPackage(this.toClassName);
		
		getMappedFields(annotatedClass, element);
	}

	@Override
	public String toString() {
		StringBuilder methodContent = new StringBuilder();
		
		String methodDeclaration = "    public %s map(final %s %s) {\n\n";
		methodContent.append(String.format(methodDeclaration, this.fromClassName, this.toClassName, this.toClassWithoutPackage));
		
		String pojoInstance = "    %s %s = new %s();\n\n";
		methodContent.append(String.format(pojoInstance, this.fromClassName, this.fromClassWithoutPackage, this.fromClassName));

		methodContent.append(getTransferValues());
		methodContent.append("    }\n");
		
		return methodContent.toString();
	}
	
	private String getTransferValues() {
		StringBuilder content = new StringBuilder();

		createGetSetLines(content);

		content.append("\n\n");
		content.append(String.format("        return %s;", this.fromClassWithoutPackage));
		content.append("\n\n");

		return content.toString();
	}

	private void createGetSetLines(final StringBuilder content) {
		for (String key : mappedFields.keySet()) {
			String getSetLine = "        %s.set%s( %s.get%s());\n";
			content.append(String.format(getSetLine, this.fromClassWithoutPackage, captalize(mappedFields.get(key)), this.toClassWithoutPackage, captalize(key)));
		}
	}

	private void getMappedFields(final TypeElement annotatedClass, final Element element) {
		for (Element elemento : elementUtils.getAllMembers(annotatedClass)) {
			for (AnnotationMirror mirror : elementUtils.getAllAnnotationMirrors(elemento)) {
				if(mirror.getAnnotationType().toString().equalsIgnoreCase(MAP_TO_ANNOTATION)) {
					String value = (String) mirror.getElementValues().values().iterator().next().getValue();
					mappedFields.put(elemento.toString(), value);
				}
			}
	    }
	}
	
	private String getPathWithoutPackage(final String path) {
		return uncaptalize(path.substring(path.lastIndexOf('.') + 1));
	}
	
	private String uncaptalize(String input) {
		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}
	
	private String captalize(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
}