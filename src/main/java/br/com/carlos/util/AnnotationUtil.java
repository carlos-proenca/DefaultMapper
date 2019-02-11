package br.com.carlos.util;

import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

import br.com.carlos.annotations.CreateMapper;

public class AnnotationUtil {

	public static TypeMirror getAnnotationValue(CreateMapper annotation) {
		try {
			annotation.value();
		} catch (MirroredTypeException mte) {
			return mte.getTypeMirror();
		}
		return null;
	}
}
