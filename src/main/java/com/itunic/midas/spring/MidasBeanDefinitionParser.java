package com.itunic.midas.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.itunic.midas.io.core.commons.HandlerTools;
import com.itunic.midas.spring.beans.ProtocolXMLBean;

public class MidasBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	private static Logger logger = LoggerFactory.getLogger(MidasBeanDefinitionParser.class);

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
		String port = element.getAttribute("port");
		String threads = element.getAttribute("threads");
		String serialization = element.getAttribute("serialization");
		builder.addPropertyValue("name", name);
		builder.addPropertyValue("port", Integer.parseInt(port));
		builder.addPropertyValue("threads", Integer.parseInt(threads));
		serialization = this.getSerialization(serialization);
		if (!StringUtils.hasText(serialization))
			builder.addPropertyValue("serialization", serialization);
	}

	private String getSerialization(String serialization) {
		switch (serialization) {
		case "JDK":
			return HandlerTools.JDK_RECV;

		default:
			logger.error("未识别的序列化方式！", new Exception());
			return null;
		}
	}
}
