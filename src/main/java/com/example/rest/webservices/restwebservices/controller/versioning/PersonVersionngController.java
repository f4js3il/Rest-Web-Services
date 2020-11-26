package com.example.rest.webservices.restwebservices.controller.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.webservices.restwebservices.versioning.Name;
import com.example.rest.webservices.restwebservices.versioning.PersonV1;
import com.example.rest.webservices.restwebservices.versioning.PersonV2;

@RestController
public class PersonVersionngController {
	
	@GetMapping("v1/person")
	public PersonV1 getpersonV1() {
	return new PersonV1("Shalu Chandran");
	}
	
	@GetMapping("v2/person")
	public PersonV2 getpersonV2() {
	return new PersonV2(new Name("Shalu","Chandran"));
	}
	
	@GetMapping(value = "/person/param", params = "version=1")
	public PersonV1 paramV1() {
		return new PersonV1("Bob Charlie");
	}

	@GetMapping(value = "/person/param", params = "version=2")
	public PersonV2 paramV2() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	@GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
	public PersonV1 headerV1() {
		return new PersonV1("Bob Charlie");
	}

	@GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
	public PersonV2 headerV2() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	

	@GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v1+json")
	public PersonV1 producesV1() {
		return new PersonV1("Bob Charlie");
	}

	@GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v2+json")
	public PersonV2 producesV2() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}

}
