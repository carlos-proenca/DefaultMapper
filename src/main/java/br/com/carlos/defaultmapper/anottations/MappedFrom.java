package br.com.carlos.defaultmapper.anottations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The Mapped From Annotation Type
 * 
 * @author Carlos Proen�a (carlos_proenca@live.com)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MappedFrom {

	String value() default "";
}
