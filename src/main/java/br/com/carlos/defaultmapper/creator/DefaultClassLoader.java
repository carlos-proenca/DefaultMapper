package br.com.carlos.defaultmapper.creator;

import java.security.SecureClassLoader;

/**
 * The Default Class Loader Type
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
@SuppressWarnings("rawtypes")
public class DefaultClassLoader extends SecureClassLoader {
	
	protected DefaultClassLoader(ClassLoader classLoader) {
		super(classLoader);
	}

	protected Class load(String name, byte[] data) {
		return super.defineClass(name, data, 0, data.length);
	}
	
	protected Class find(String name) {
		return super.findLoadedClass(name);
	}
	
}