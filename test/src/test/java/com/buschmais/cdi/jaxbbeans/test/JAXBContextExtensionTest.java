package com.buschmais.cdi.jaxbbeans.test;

import javax.xml.bind.JAXBException;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.buschmais.jaxbbeans.extension.JAXBContextExtension;
import com.buschmais.jaxbbeans.test.schema.sample.Root;

/**
 * Tests the {@link JAXBContextExtension}.
 * 
 * @author dirk.mahler
 */
public class JAXBContextExtensionTest {

	/**
	 * The weld container.
	 */
	private WeldContainer container;

	/**
	 * Creates the weld container.
	 */
	@Before
	public void createContainer() {
		Weld weld = new Weld();
		this.container = weld.initialize();
	}

	/**
	 * Marshal and unmarshal an object using the {@link Codec}.
	 * 
	 * @throws JAXBException
	 *             If the test fails.
	 */
	@Test
	public void marshalUnmarshal() throws JAXBException {
		Codec schemaCodec = container.instance().select(Codec.class).get();
		Root input = new Root();
		input.setName("Test");
		String xml = schemaCodec.write(input);
		Root output = schemaCodec.read(xml);
		Assert.assertEquals(input.getName(), output.getName());
	}

}
