package com.itunic.midas;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringCustomTest {
	public static void main(String[] args) {  
      ApplicationContext ac = new ClassPathXmlApplicationContext("test.xml");
        new ClassPathXmlApplicationContext("test2.xml");  
    }  
}
