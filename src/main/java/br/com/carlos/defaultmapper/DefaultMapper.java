package br.com.carlos.defaultmapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import br.com.carlos.defaultmapper.anottations.EmbeddedMapped;
import br.com.carlos.defaultmapper.anottations.Mapped;

public final class DefaultMapper{
	
	public final <FROM,TO> TO map(FROM from, Class<TO> toClass) {
		TO to = null;
		try {
			to = toClass.newInstance();
		} catch (InstantiationException e) {
			//TODO tratar erros
		} catch (IllegalAccessException e) {
			//TODO tratar erros
		}
		
		for (Field field : FieldUtils.getFieldsWithAnnotation(from.getClass(), Mapped.class)) {
			try {
				Mapped mapped = field.getAnnotation(Mapped.class);
				Object fieldValue = FieldUtils.readDeclaredField(from, field.getName(), true);
				FieldUtils.writeDeclaredField(to, mapped.value(), fieldValue , true);
			} catch (IllegalAccessException e) {
				//TODO tratar erros
			}
		}
		for (Field field : FieldUtils.getFieldsWithAnnotation(from.getClass(), EmbeddedMapped.class)) {
			try {
				EmbeddedMapped embeddedMapped = field.getAnnotation(EmbeddedMapped.class);
				if(embeddedMapped.isSameClass()){
					for (Field embeddedField : FieldUtils.getFieldsWithAnnotation(field.getType(), Mapped.class)) {
						Mapped mapped = embeddedField.getAnnotation(Mapped.class);
						Object fieldObject = FieldUtils.readDeclaredField(from, field.getName(), true);
						Object embeddedfieldValue = FieldUtils.readDeclaredField(fieldObject, embeddedField.getName(), true);
						FieldUtils.writeDeclaredField(to, mapped.value(), embeddedfieldValue , true);
					}
				}else{
					Object embeddedfieldValue = FieldUtils.readDeclaredField(from, field.getName(), true);
					FieldUtils.writeDeclaredField(to, embeddedMapped.value(), embeddedfieldValue , true);
				}
			} catch (IllegalAccessException e) {
				//TODO tratar erros
			}
		}
		return to;
	}

	public <FROM,TO> List<TO> listMap(List<FROM> fromList, Class<TO> toClass) {
		List<TO> result = new ArrayList<TO>();
		for (FROM from : fromList) {
			result.add(map(from, toClass));
		}
		return result;
	}
}