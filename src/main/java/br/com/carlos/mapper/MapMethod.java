package br.com.carlos.mapper;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import br.com.carlos.log.DevLog;

public class MapMethod {
	private String toClass;
	private String fromClass;
	private PrintWriter out;
	private String fromClassWithoutPackage;
	private String fromClassNameWithoutPackage;
	private Element element;
	private Elements elementUtils;
	

//	public MapMethod(PrintWriter out, Element element, String toClass, String fromClass, Elements elementUtils) {
//		this.toClass = toClass;
//		this.fromClass = fromClass;
//		this.out = out;
//		this.element = element;
//		this.
//		this.elementUtils = elementUtils;
//		buildMapMethod();
//	}

	
}