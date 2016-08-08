package br.com.carlos.defaultmapper.creator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import br.com.carlos.defaultmapper.DefaultMapper;
import br.com.carlos.defaultmapper.anottations.MappedFrom;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

/**
 * The Default Mapper Java Creator Type
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class DefaultMapperJavaCreator implements DefaultMapper {

	private final CtClass[] NO_ARGS = {};

	private DefaultClassLoader defaultClassLoader= new DefaultClassLoader(this.getClass().getClassLoader());
	
	public <FROM, TO> TO map(FROM from, Class<TO> toClass) {
		try {
			
			String className = getMapperClassName(from, toClass);
			Class loadedClass = defaultClassLoader.find(className);
			DefaultMapper defaultMapper = null;
			
			if(loadedClass == null){

				CtClass newMapperClass = createNewClass(className);
				addDefaultConstructor(newMapperClass);
				createMethodMapGenerated(newMapperClass, from, toClass);
				newMapperClass.addInterface( ClassPool.getDefault().get(DefaultMapper.class.getCanonicalName()));
				byte[] bytes = newMapperClass.toBytecode();
				defaultMapper = loadClassByInterface(className, bytes, DefaultMapper.class);
				
			}else{
				defaultMapper = (DefaultMapper) loadedClass.newInstance();
			}

			return (TO) MethodUtils.invokeExactMethod(defaultMapper, "mapGenerated",from);
		} catch (Exception e) {
			e.printStackTrace();
		}catch (AbstractMethodError e) {
			e.printStackTrace();
		}
		return null;
	}


	public <FROM, TO> List<TO> listMap(List<FROM> fromList, Class<TO> toClass) {
		List<TO> result = new ArrayList<TO>();
		for (FROM from : fromList) {
			result.add(map(from, toClass));
		}
		return result;
	}

	private <FROM, TO> void createMethodMapGenerated(CtClass newMapperClass, FROM from, Class<TO> toClass) throws Exception {
		
		StringBuilder methodContent = new StringBuilder();
		Class fromClass = from.getClass();
		
		methodContent.append("public ").append(toClass.getName()).append(" mapGenerated(")
						.append(fromClass.getName()).append(" ")
						.append(StringUtils.uncapitalize(fromClass.getSimpleName()))
						.append(") {")
						.append(toClass.getName()).append(" ")
						.append(StringUtils.uncapitalize(toClass.getSimpleName()))
						.append(" = new ").append(toClass.getName()).append("();")
						.append(getMethodContentBody(toClass, fromClass))
						.append("return ").append(StringUtils.uncapitalize(toClass.getSimpleName())).append("; ")
						.append("}");

		CtMethod generatedMethod = CtMethod.make(methodContent.toString(), newMapperClass);
		newMapperClass.addMethod(generatedMethod);
	}


	private <TO> String getMethodContentBody(Class<TO> toClass,  Class fromClass) {
		StringBuilder methodContentBody = new StringBuilder();
		for (Field field : FieldUtils.getFieldsWithAnnotation(toClass, MappedFrom.class)) {
			try {
				MappedFrom mapped = field.getAnnotation(MappedFrom.class);
				String fieldName = StringUtils.isNotBlank(mapped.value()) ? mapped.value() : field.getName();
				
				fieldName = StringUtils.capitalize(fieldName);
				Method fromGetMethod = fromClass.getMethod("get".concat(fieldName));
				
				fieldName = StringUtils.capitalize(field.getName());
				
				Method methodSetToClass = toClass.getMethod("set".concat(fieldName), field.getType());
				
			
				StringBuilder getSetGenerated = new StringBuilder();
				getSetGenerated.append(StringUtils.uncapitalize(toClass.getSimpleName()))
								.append(".")
								.append(methodSetToClass.getName())
								.append("(")
								.append(generateGetCheckingWrappers(fromClass, fromGetMethod, methodSetToClass))
								.append(");");
				
				methodContentBody.append(getSetGenerated.toString());
			} catch (Exception e) {
				//nothing to do here
			}
		}
		return methodContentBody.toString();
	}


	private String generateGetCheckingWrappers(Class fromClass, Method fromGetMethod, Method methodSetToClass) {
		StringBuilder getSetGenerated = new StringBuilder();
		Class<?>[] setParameterTypes = methodSetToClass.getParameterTypes();
		Class<?> setParameterType = setParameterTypes[0];
		
		if((setParameterType.isPrimitive() && fromGetMethod.getReturnType().isPrimitive()) ||
			setParameterType.equals(String.class)){
			
			getSetGenerated.append(StringUtils.uncapitalize(fromClass.getSimpleName()))
			.append(".")
			.append(fromGetMethod.getName())
			.append("()");
			
		
		}else if(setParameterType.isPrimitive()){
			String castType = setParameterType.getSimpleName();
			
			getSetGenerated					
			.append(StringUtils.uncapitalize(fromClass.getSimpleName()))
			.append(".")
			.append(fromGetMethod.getName())
			.append("()")
			.append(".");
			
			if(setParameterType.equals(Integer.class)){
				getSetGenerated.append("int");
			}else{
				getSetGenerated.append(castType);
			}
			getSetGenerated.append("Value()");
			
		}else if(fromGetMethod.getReturnType().isPrimitive()){
			 String castType = StringUtils.capitalize(fromGetMethod.getReturnType().getSimpleName());
				getSetGenerated
				.append("new ")
				.append(castType)
				.append("(")
				.append(StringUtils.uncapitalize(fromClass.getSimpleName()))
				.append(".")
				.append(fromGetMethod.getName())
				.append("())");
		}
		return getSetGenerated.toString();
	}

	private <T> T loadClassByInterface(String cname, byte[] bytes, Class interfacei) {

		Class clasLoad = defaultClassLoader.load(cname, bytes);
		T access = null;
		try {
			access = (T) clasLoad.newInstance();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		} 
		return access;
	}

	private void addDefaultConstructor(CtClass newMapperClass) throws CannotCompileException {
		CtConstructor constructor = new CtConstructor(NO_ARGS, newMapperClass);
		constructor.setBody(";");
		newMapperClass.addConstructor(constructor);
	}

	private CtClass createNewClass(String className) {
		ClassPool pool = ClassPool.getDefault();
		return pool.makeClass(className);

	}

	private <FROM, TO>String getMapperClassName(FROM from, Class<TO> toClass) {
		StringBuilder className = new StringBuilder();
		className.append(from.getClass().getSimpleName());
		className.append("TO");
		className.append(toClass.getSimpleName());
		className.append("Mapper");
		
		return className.toString();
	}
}