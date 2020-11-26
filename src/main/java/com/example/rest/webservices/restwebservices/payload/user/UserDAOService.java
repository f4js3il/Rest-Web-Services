package com.example.rest.webservices.restwebservices.payload.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {
	
	private static Integer usersCount = 3;
	private static List<User> users = new ArrayList<User>();
	
	static {
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Jack", new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user){
		if(user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(Integer id) {		
		return users.stream().filter(user-> user.getId().equals(id)).findFirst().orElse(null);
	}
	
	public User deleteById(Integer id) {
	for (User user : users) {
		if(user.getId().equals(id)) {
			users.remove(user);
			return user;
		}		
	}
	return null;
	}
	
}
