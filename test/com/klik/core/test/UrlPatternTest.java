package com.klik.core.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.klik.core.service.IUrlPatternService;

@Component("urlPatternTest")
public class UrlPatternTest {

	@Autowired
	IUrlPatternService urlPatternService;
	
	public void test1() throws Exception{
		System.out.println(urlPatternService.getCategries("http://ucar.auto.sina.com.cn/beijing/"));
		
	}
	
	public static void main(String[] args) throws Exception{
		AbstractApplicationContext ctx= new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		ctx.registerShutdownHook();
		UrlPatternTest test=(UrlPatternTest)ctx.getBean("urlPatternTest");
		test.test1();
	}
	
}
