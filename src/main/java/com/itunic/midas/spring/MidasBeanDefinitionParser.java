package com.itunic.midas.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import com.itunic.midas.io.core.ResponseDispatcher;
import com.itunic.midas.spring.beans.ProtocolXMLBean;

public class MidasBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		String name = element.getLocalName();
		Class<?> clazz = null;
		switch (name) {
		case "protocol":
			clazz = ProtocolXMLBean.class;
			break;
		default:
			break;
		}
		return clazz;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		String name = element.getAttribute("name");
		Integer port = Integer.parseInt(element.getAttribute("port"));
		Integer threads = Integer.parseInt(element.getAttribute("threads"));
		String serialization = element.getAttribute("serialization");
		ResponseDispatcher rd = new ResponseDispatcher(port);
		rd.run();
	}

}
