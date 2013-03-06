package com.buschmais.cdi.jaxbbeans.test;

import javax.xml.bind.JAXBException;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.buschmais.cdi.jaxbbeans.test.schema.Codec;
import com.buschmais.jaxbbeans.test.schema.sample.Root;

public class JAXBContextExtensionTest {

	private WeldContainer container;

	@Before
	public void createContainer() {
		Weld weld = new Weld();
		this.container = weld.initialize();
	}

	@Test
	public void marshalUnmarshal() throws JAXBException {
		Codec schemaCodec = container.instance()
				.select(Codec.class).get();
		Root input = new Root();
		input.setName("Test");
		String xml = schemaCodec.write(input);
		Root output = schemaCodec.read(xml);
		Assert.assertEquals(input.getName(), output.getName());
	}

}
