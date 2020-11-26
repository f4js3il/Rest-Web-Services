package com.example.rest.webservices.restwebservices.controller.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.LocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.webservices.restwebservices.payload.helloworld.HelloWorldBean;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
//	@GetMapping(path = "/hello-world/internationalization")
//	public String helloWorld(@RequestHeader(name="Accept-Language",required=false) Locale locale) {
//		return messageSource.getMessage("good.morning.message",null, locale);
//	}
	
	@GetMapping(path = "/hello-world/internationalization")
	public String helloWorld() {
		return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());
	}
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello world");
	}
	
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean("hello world "+name);
	}

}
