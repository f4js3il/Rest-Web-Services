package com.example.rest.webservices.restwebservices.controller.filtering;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.webservices.restwebservices.payload.somebean.SomeBean;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	@GetMapping(value = "/filtering")
	public MappingJacksonValue retrieveSomeBean() {				
		Set<String> propertyArray = new HashSet<String>();
		propertyArray.add("field4");
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(propertyArray);
		FilterProvider filters = new SimpleFilterProvider()
				.addFilter("SomeBeanFilter", filter);	
		MappingJacksonValue mapping = new MappingJacksonValue(new SomeBean("value1","value2","value3", "value4"));
		mapping.setFilters(filters);			
		return mapping;
	}
	
	@GetMapping(value = "/filtering-list")
	public List<SomeBean> retrieveListOfSomeBeans() {	
		return Arrays.asList(
				new SomeBean("value1","value2","value3","value4"),
				new SomeBean("value5","value6","value7","value8"));
	}

}
