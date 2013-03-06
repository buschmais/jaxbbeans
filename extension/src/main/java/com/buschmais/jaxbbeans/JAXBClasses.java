package com.buschmais.jaxbbeans;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JAXBClasses {
	Class<?>[] value();
}
