package com.itunic.midas.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		// TODO Auto-generated method stub
		System.out.println(element.getLocalName()+"2 "+this);
		String userName = element.getAttribute("userName");
		String email = element.getAttribute("email");
		System.out.println(userName);
		if (StringUtils.hasText(userName)) {
			builder.addPropertyValue("userName", userName);
		}
		if (StringUtils.hasText(email)) {
			builder.addPropertyValue("email", email);
		}
	}

	@Override
	protected Class<?> getBeanClass(Element element) {
		System.out.println(element.getLocalName()+"   "+this);
		// TODO Auto-generated method stub
		return User.class;
	}

}
