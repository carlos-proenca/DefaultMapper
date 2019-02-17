package br.com.carlos.mapper;

import javax.lang.model.element.Element;

import br.com.carlos.util.AnnotationUtil;

/**
 * Test of Create Mapper Test class.
 * 
 * Tests business logic of annotation processor class creation
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
public class ClassMapperHeader {
	
	private Integer classMapperNameLastDot;
	private String classMapperName;
	
	public ClassMapperHeader(final Element element) {
		super();
		this.classMapperName = AnnotationUtil.getCreateMapperValue(element);
		this.classMapperNameLastDot = this.classMapperName.lastIndexOf('.');
	}

	@Override
	public String toString() {
		StringBuilder headerClass = new StringBuilder();
		headerClass.append(getPackage());
		headerClass.append(getClassDeclaration());
		return headerClass.toString();
	}

	private String getClassDeclaration() {
		String builderSimpleClassName = classMapperName.substring(classMapperNameLastDot + 1);
		return "public class ".concat(builderSimpleClassName).concat("Mapper {\n\n");
	}

	private String getPackage() {
		return "\npackage ".concat(classMapperName.substring(0, classMapperNameLastDot)).concat(";\n\n");		
	}
}