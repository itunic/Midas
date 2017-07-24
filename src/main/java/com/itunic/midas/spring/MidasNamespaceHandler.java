package com.itunic.midas.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MidasNamespaceHandler extends NamespaceHandlerSupport{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		registerBeanDefinitionParser("protocol", new MidasBeanDefinitionParser());
	}

}
