package com.buschmais.jaxbbeans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;

/**
 * Applying this meta annotation to a qualifier annotation (i.e. an annotation
 * carrying {@link Qualifier}) will create a bean of type {@link JAXBContext}
 * with the given qualifier with scope {@link Singleton}, no additional producer
 * field or method is needed.
 * <p>
 * The {@link #value()} requires an array of classes which will be passed to
 * {@link JAXBContext#newInstance(Class...)}:
 * 
 * <pre>
 * &#064;Qualifier
 * &#064;JAXBClasses(ObjectFactory.class)
 * &#064;Retention(RetentionPolicy.RUNTIME)
 * public @interface Configuration {
 * }
 * </pre>
 * 
 * The {@link JAXBContext} instance is now ready to be injected in any bean
 * using the qualifier annotation:
 * 
 * <pre>
 * public class ConfigReader {
 * 	&#064;Inject
 * 	&#064;Configuration
 * 	private JAXBContext jaxbContext;
 * }
 * </pre>
 * 
 * @author dirk.mahler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface JAXBClasses {

	/**
	 * @return The classes which will be passed to
	 *         {@link JAXBContext#newInstance(Class...)}.
	 */
	Class<?>[] value();
}
