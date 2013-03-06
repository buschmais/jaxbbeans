package com.buschmais.jaxbbeans.extension;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.slf4j.LoggerFactory;

public class JAXBContextBean implements Bean<JAXBContext> {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(JAXBContextBean.class);

	private final Annotation qualifier;
	private final Class<?>[] jaxbClasses;

	public JAXBContextBean(Annotation qualifier, Class<?>[] jaxbClasses) {
		this.qualifier = qualifier;
		this.jaxbClasses = jaxbClasses;
	}

	@Override
	public Set<Type> getTypes() {
		Set<Class<?>> types = new HashSet<Class<?>>();
		types.add(JAXBContext.class);
		return new HashSet<Type>(types);
	}

	@Override
	public JAXBContext create(CreationalContext<JAXBContext> creationalContext) {
		LOGGER.debug("Creating JAXBContext instance for qualifier {}.",
				qualifier);
		try {
			return JAXBContext.newInstance(jaxbClasses);
		} catch (JAXBException e) {
			throw new IllegalArgumentException(
					"Cannot create JAXBContext instance for qualifier "
							+ this.qualifier, e);
		}
	}

	@Override
	public void destroy(JAXBContext instance,
			CreationalContext<JAXBContext> creationalContext) {
		creationalContext.release();
	}

	@Override
	public Set<Annotation> getQualifiers() {
		Set<Annotation> qualifiers = new HashSet<Annotation>();
		qualifiers.add(qualifier);
		return qualifiers;
	}

	@Override
	public String getName() {
		return JAXBContext.class.getName();
	}

	@Override
	public Set<Class<? extends Annotation>> getStereotypes() {
		return Collections.emptySet();
	}

	@Override
	public Class<?> getBeanClass() {
		return JAXBContext.class;
	}

	@Override
	public boolean isAlternative() {
		return false;
	}

	@Override
	public boolean isNullable() {
		return false;
	}

	@Override
	public Set<InjectionPoint> getInjectionPoints() {
		return Collections.emptySet();
	}

	@Override
	public Class<? extends Annotation> getScope() {
		return Singleton.class;
	}

}
