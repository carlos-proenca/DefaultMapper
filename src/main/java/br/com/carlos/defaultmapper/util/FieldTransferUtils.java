package br.com.carlos.defaultmapper.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import br.com.carlos.defaultmapper.anottations.EmbeddedMapped;
import br.com.carlos.defaultmapper.anottations.EmbeddedMappedFrom;
import br.com.carlos.defaultmapper.anottations.Mapped;
import br.com.carlos.defaultmapper.anottations.MappedFrom;

/**
 * The Field Transfer Utils Type
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
public class FieldTransferUtils {
	
	public static <TO, FROM> void transferEmbeddedMappedFields(FROM from, TO to) {
		for (Field field : FieldUtils.getFieldsWithAnnotation(from.getClass(), EmbeddedMapped.class)) {
			try {
				EmbeddedMapped embeddedMapped = field.getAnnotation(EmbeddedMapped.class);
				if(embeddedMapped.isSameClass()){
					transferEmbeddedFieldsForSameClass(from, to, field);
				}else{
					Object embeddedfieldValue = FieldUtils.readDeclaredField(from, field.getName(), true);
					if(embeddedfieldValue != null){
						String fieldName = StringUtils.isNotBlank(embeddedMapped.value()) ? embeddedMapped.value() : field.getName();
						FieldUtils.writeDeclaredField(to, fieldName, embeddedfieldValue , true);
					}
				}
			} catch (Exception e) {
				//nothing to do here
			}
		}
	}

	public static <TO, FROM> void transferEmbeddedFieldsForSameClass(FROM from, TO to, Field field) throws IllegalAccessException {
		for (Field embeddedField : FieldUtils.getFieldsWithAnnotation(field.getType(), Mapped.class)) {
			Mapped mapped = embeddedField.getAnnotation(Mapped.class);
			Object fieldObject = FieldUtils.readDeclaredField(from, field.getName(), true);
			if(fieldObject != null){
				Object embeddedfieldValue = FieldUtils.readDeclaredField(fieldObject, embeddedField.getName(), true);
				if(embeddedfieldValue != null){
					String fieldName = StringUtils.isNotBlank(mapped.value()) ? mapped.value() : embeddedField.getName();
					FieldUtils.writeDeclaredField(to, fieldName, embeddedfieldValue , true);
				}
			}
		}
	}

	public static <FROM, TO> void transferMappedFields(FROM from, TO to) {
		for (Field field : FieldUtils.getFieldsWithAnnotation(from.getClass(), Mapped.class)) {
			try {
				Mapped mapped = field.getAnnotation(Mapped.class);
				Object fieldValue = FieldUtils.readDeclaredField(from, field.getName(), true);

				if(fieldValue != null){
					String fieldName = StringUtils.isNotBlank(mapped.value()) ? mapped.value() : field.getName();
					FieldUtils.writeDeclaredField(to, fieldName, fieldValue , true);
				}
			} catch (Exception e) {
				//nothing to do here
			}
		}
	}
	
	public static <FROM,TO> void transferMappedFromFields(FROM from, TO to) {
		for (Field field : FieldUtils.getFieldsWithAnnotation(to.getClass(), MappedFrom.class)) {
			try {
				MappedFrom mapped = field.getAnnotation(MappedFrom.class);
				String fieldName = StringUtils.isNotBlank(mapped.value()) ? mapped.value() : field.getName();
				Object fieldValue = FieldUtils.readDeclaredField(from, fieldName, true);
				if(fieldValue != null){
					FieldUtils.writeDeclaredField(to, field.getName(), fieldValue , true);
				}
			} catch (Exception e) {
				//nothing to do here
			}
		}
	}
	
	public static <TO, FROM> void transferEmbeddedMappedFromFields(FROM from, TO to) {
		for (Field field : FieldUtils.getFieldsWithAnnotation(to.getClass(), EmbeddedMappedFrom.class)) {
			try {
				EmbeddedMappedFrom embeddedMappedFrom = field.getAnnotation(EmbeddedMappedFrom.class);
				if(embeddedMappedFrom.isSameClass()){
					transferEmbeddedFromFieldsFromSameClass(from, to, field, embeddedMappedFrom);
				}else{
					String fieldName = StringUtils.isNotBlank(embeddedMappedFrom.fieldName()) ? embeddedMappedFrom.fieldName() : field.getName();
					Object fieldValue = FieldUtils.readDeclaredField(from, fieldName, true);
					if(fieldValue != null){
						FieldUtils.writeDeclaredField(to, field.getName(), fieldValue , true);
					}
				}
			} catch (Exception e) {
				//nothing to do here
			}
		}
	}


	public static <FROM, TO> void transferEmbeddedFromFieldsFromSameClass(FROM from, TO to, Field field, EmbeddedMappedFrom embeddedMappedFrom) throws Exception {
		String fieldName = StringUtils.isNotBlank(embeddedMappedFrom.fieldTargetName()) ?embeddedMappedFrom.fieldTargetName() : field.getName();
		Object embeddedFromField = FieldUtils.readDeclaredField(from, fieldName, true);
		Object embeddedFromFieldValue = FieldUtils.readDeclaredField(embeddedFromField, embeddedMappedFrom.fieldName(), true);
		FieldUtils.writeDeclaredField(to, field.getName(), embeddedFromFieldValue , true);
	}
}