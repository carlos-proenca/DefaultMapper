package br.com.carlos.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test of Create Mapper Test class.
 * 
 * Tests business logic of annotation processor class creation
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface CreateMapper { 
	Class<?> value() default CreateMapper.class;
}