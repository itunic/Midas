package com.itunic.midas.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		registerBeanDefinitionParser("queao", new UserBeanDefinitionParser());
		registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
	}

}
