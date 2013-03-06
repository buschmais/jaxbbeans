package com.buschmais.jaxbbeans.extension;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.xml.bind.JAXBContext;

import org.slf4j.LoggerFactory;

import com.buschmais.jaxbbeans.JAXBClasses;

/**
 * CDI extension which detects injection points for {@link JAXBContext}
 * instances and creates the corresponding beans.
 * 
 * @author dirk.mahler
 */
public class JAXBContextExtension implements Extension {

	/**
	 * The logger.
	 */
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(JAXBContextExtension.class);

	/**
	 * Holds the detected qualifier annotations and the classes to used for
	 * creating the corresponding {@link JAXBContext}s.
	 */
	private final Map<Annotation, Class<?>[]> qualifiers = new HashMap<Annotation, Class<?>[]>();

	/**
	 * Constructor.
	 */
	public JAXBContextExtension() {
		LOGGER.info("Created " + this.getClass().getSimpleName());
	}

	/**
	 * Processes a injection target.
	 * <p>
	 * Scans the injection target for injection points of type
	 * {@link JAXBContext} and evaluates the provided qualifier annotations for
	 * the presence of {@link JAXBClasses}.
	 * 
	 * @param pit
	 *            The {@link ProcessInjectionTarget} fired by the container.
	 */
	public void processInjectionTarget(@Observes ProcessInjectionTarget<?> pit) {
		for (InjectionPoint injectionPoint : pit.getInjectionTarget()
				.getInjectionPoints()) {
			if (JAXBContext.class.equals(injectionPoint.getType())) {
				for (Annotation qualifier : injectionPoint.getQualifiers()) {
					Class<? extends Annotation> qualifierType = qualifier
							.annotationType();
					JAXBClasses jaxbClasses = qualifierType
							.getAnnotation(JAXBClasses.class);
					if (jaxbClasses != null) {
						LOGGER.debug("Detected JAXBContext injection point at {}.");
						qualifiers.put(qualifier, jaxbClasses.value());
					}

				}
			}
		}
	}

	/**
	 * Creates the {@link JAXBContext} beans from the information collected
	 * during {@link #processInjectionTarget(ProcessInjectionTarget)}.
	 * 
	 * @param afterBeanDiscovery
	 *            The {@link AfterBeanDiscovery} fired by the container.
	 */
	void afterBeanDiscovery(@Observes AfterBeanDiscovery afterBeanDiscovery) {
		for (Entry<Annotation, Class<?>[]> entry : qualifiers.entrySet()) {
			Bean<?> jaxbContextBean = new JAXBContextBean(entry.getKey(),
					entry.getValue());
			LOGGER.debug("Registering JAXBContext bean with qualifier {}.",
					entry.getKey());
			afterBeanDiscovery.addBean(jaxbContextBean);
		}
	}
}
