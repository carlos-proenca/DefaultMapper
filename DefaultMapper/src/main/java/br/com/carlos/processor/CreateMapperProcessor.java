package br.com.carlos.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import br.com.carlos.mapper.ClassMapperHeader;
import br.com.carlos.mapper.MapMethod;
import br.com.carlos.util.AnnotationUtil;

/**
 * Test of Create Mapper Test class.
 * 
 * Tests business logic of annotation processor class creation
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
@SupportedAnnotationTypes("br.com.carlos.annotations.CreateMapper")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CreateMapperProcessor extends AbstractProcessor {
	
	private Elements elementUtils;
	private ProcessingEnvironment processingEnvironment;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {
	    super.init(processingEnvironment);
	    this.processingEnvironment = processingEnvironment;
	    this.elementUtils = processingEnvironment.getElementUtils();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
		try {
			for (TypeElement annotation : annotations) {
				for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
					JavaFileObject newMapperClass = createNewMapperClass(element);

					PrintWriter out = new PrintWriter(newMapperClass.openWriter());
					
					out.print(new ClassMapperHeader(element).toString());
					out.print(new MapMethod(element, this.elementUtils).toString());
					out.println("}");
					out.close();
				}
			}
		} catch (Exception e) {
			this.processingEnvironment.getMessager().printMessage(Kind.ERROR, "[Create Mapper Error] - "+e.getLocalizedMessage()+"\n "+
					Arrays.asList(e.getStackTrace()).stream().map(Objects::toString).collect(Collectors.joining("\n")));	
		}
		return Boolean.TRUE;
	}	

	private JavaFileObject createNewMapperClass(final Element element) throws IOException {
		String fromClass = AnnotationUtil.getCreateMapperValue(element);
		JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(fromClass+"Mapper");
		cleanFile(builderFile);
		return builderFile;
	}

	private void cleanFile(final JavaFileObject builderFile) throws IOException {
		PrintWriter out = new PrintWriter(builderFile.openWriter());
		out.print("");
		out.close();
	}
}
