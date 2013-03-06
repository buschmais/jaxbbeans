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

public class JAXBContextExtension implements Extension {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(JAXBContextExtension.class);

	private final Map<Annotation, Class<?>[]> qualifiers = new HashMap<Annotation, Class<?>[]>();

	public JAXBContextExtension() {
		LOGGER.info("Created " + this.getClass().getSimpleName());
	}

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
