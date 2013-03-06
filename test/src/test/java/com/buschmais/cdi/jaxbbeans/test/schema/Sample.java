package com.buschmais.cdi.jaxbbeans.test.schema;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import com.buschmais.jaxbbeans.JAXBClasses;
import com.buschmais.jaxbbeans.test.schema.sample.ObjectFactory;

@Qualifier
@JAXBClasses(ObjectFactory.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sample {
}
