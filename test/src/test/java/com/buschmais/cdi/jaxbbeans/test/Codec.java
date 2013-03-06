package com.buschmais.cdi.jaxbbeans.test;

import java.io.StringReader;
import java.io.StringWriter;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import com.buschmais.jaxbbeans.test.schema.sample.Root;

/**
 * Bean using an injection point of type {@link JAXBContext} and qualified with
 * {@link Sample}.
 * 
 * @author dirk.mahler
 */
public class Codec {

	/**
	 * The injected {@link JAXBContext}.
	 */
	@Inject
	@Sample
	private JAXBContext jaxbContext;

	/**
	 * Reads an XML document from a {@link String}.
	 * 
	 * @param input
	 *            The input {@link String}.
	 * @return The unmarshalled object.
	 * @throws JAXBException
	 *             If unmarshalling fails.
	 */
	public Root read(String input) throws JAXBException {
		return jaxbContext
				.createUnmarshaller()
				.unmarshal(new StreamSource(new StringReader(input)),
						Root.class).getValue();
	}

	/**
	 * Writes an XML document from a {@link Root} object.
	 * 
	 * @param input
	 *            The input {@link Root} object.
	 * @return The marshalled XML document as {@link String}.
	 * @throws JAXBException
	 *             If marshalling fails.
	 */
	public String write(Root input) throws JAXBException {
		StringWriter writer = new StringWriter();
		jaxbContext.createMarshaller().marshal(input, writer);
		return writer.toString();
	}
}
