package com.buschmais.cdi.jaxbbeans.test.schema;

import java.io.StringReader;
import java.io.StringWriter;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import com.buschmais.jaxbbeans.test.schema.sample.Root;

public class Codec {

	private final JAXBContext jaxbContext;

	@Inject
	public Codec(@Sample JAXBContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}

	public Root read(String input) throws JAXBException {
		return jaxbContext
				.createUnmarshaller()
				.unmarshal(new StreamSource(new StringReader(input)),
						Root.class).getValue();
	}

	public String write(Root input) throws JAXBException {
		StringWriter writer = new StringWriter();
		jaxbContext.createMarshaller().marshal(input, writer);
		return writer.toString();
	}
}
