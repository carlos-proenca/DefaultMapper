package br.com.carlos.defaultmapper.anottations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The Embedded Mapped From Annotation Type
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EmbeddedMappedFrom {

	String fieldName() default "";

	String fieldTargetName() default "";
	
	boolean isSameClass() default false;
	
}
