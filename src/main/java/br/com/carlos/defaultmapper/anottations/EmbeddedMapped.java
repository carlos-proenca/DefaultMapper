package br.com.carlos.defaultmapper.anottations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The Embedded Mapped Annotation Type
 * 
 * @author Carlos Proen�a (carlos_proenca@live.com)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EmbeddedMapped {

	String value() default "";

	boolean isSameClass() default false;
}
