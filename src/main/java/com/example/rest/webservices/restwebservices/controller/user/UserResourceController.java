package com.example.rest.webservices.restwebservices.controller.user;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.webservices.restwebservices.payload.user.User;
import com.example.rest.webservices.restwebservices.payload.user.UserDAOService;
import com.example.rest.webservices.restwebservices.payload.user.UserNotFoundException;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class UserResourceController {
	
	@Autowired
	private UserDAOService service;
	
	@GetMapping("/users")
	public List<User> retrieveAllusers(){
		return service.findAll();
		
	}	
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable Integer id){
		User user = service.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("id-"+id);
		}
						
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllusers());
		
		EntityModel<User> resource = EntityModel.of(user);
		resource.add(linkTo.withRel("all-users"));
		
		//HATEOAS
		
		return resource;	
	}


	@DeleteMapping("/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Integer id){
		User user = service.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException("id-"+id);
		}
		return ResponseEntity.noContent().build();	
	}
	
	
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User createdUser =  service.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}

}
