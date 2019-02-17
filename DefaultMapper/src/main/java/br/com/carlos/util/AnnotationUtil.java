package br.com.carlos.util;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

import br.com.carlos.annotations.CreateMapper;

/**
 * Test of Create Mapper Test class.
 * 
 * Tests business logic of annotation processor class creation
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
public final class AnnotationUtil {
	private AnnotationUtil() {}
	
	public static String getCreateMapperValue(final Element element) {
		try {
			((CreateMapper) element.getAnnotation(CreateMapper.class)).value();
		} catch (MirroredTypeException mte) {
			return mte.getTypeMirror().toString();
		}
		return null;
	}

	public static TypeElement findEnclosingTypeElement(Element e){
		while (e != null && !(e instanceof TypeElement)){
			e = e.getEnclosingElement();
		}
		return TypeElement.class.cast(e);
	}
}
