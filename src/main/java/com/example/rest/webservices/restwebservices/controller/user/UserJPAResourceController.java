package com.example.rest.webservices.restwebservices.controller.user;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

import com.example.rest.webservices.restwebservices.payload.user.Post;
import com.example.rest.webservices.restwebservices.payload.user.PostRepository;
import com.example.rest.webservices.restwebservices.payload.user.User;
import com.example.rest.webservices.restwebservices.payload.user.UserDAOService;
import com.example.rest.webservices.restwebservices.payload.user.UserNotFoundException;
import com.example.rest.webservices.restwebservices.payload.user.UserRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class UserJPAResourceController {
	
	@Autowired
	private UserRepository  repository;
	
	@Autowired
	private PostRepository  postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllusers(){
		return repository.findAll();
		
	}	
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable Integer id){
		Optional<User> user = repository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
						
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllusers());
		
		EntityModel<User> resource = EntityModel.of(user.get());
		resource.add(linkTo.withRel("all-users"));
		
		//HATEOAS
		
		return resource;	
	}


	@DeleteMapping("/jpa/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Integer id){
		User user = repository.findById(id).get();
		repository.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException("id-"+id);
		}
		return ResponseEntity.noContent().build();	
	}
	
	
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User createdUser =  repository.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllusers(@PathVariable Integer id){
		Optional<User> user = repository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		return user.get().getPosts();	
		
	}

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<User> createPost(@PathVariable Integer id , @RequestBody Post post ) {
		Optional<User> user = repository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		post.setUser(user.get());
		Post createdPost =  postRepository.save(post);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdPost.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
}
