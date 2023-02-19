package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;

@RestController //let Spring Knows the method inside this class needs to be connected to Tomcat
				//in particular these methods will be connected to Tomcat in such away that we will implement a rest service
@RequestMapping("/users")//tell Tomcat the path "/users"
@CrossOrigin 
public class UsersController {
	
	private Long nextUserId = 0L;
	//make a field for user 
	//Array in Java is fixed(we can not add or delete) so we do not initializ the User list in this way : private User[] users = new User[] {}
	//we are going to use ArrayList so we can add or delete users
	private ArrayList<User> users = new ArrayList<>(List.of(new User[] {
			//if I put only 0 by default it is integer so we use 0L to declare it as Long
			//use "," because it is an array initializer
			//We don't need a semicolon inside of an array initializer
			new User(0L, "user1", "pass", false),
			new User(1L, "user2", "pass", false),
			new User(2L, "user3", "pass", true),
			new User(3L, "user4", "pass", true),
	}));
	
	// FE: this.http.get<User[]>(`http://localhost:8080/users?username=${username}&password=${password}`}
	//	   HttpClient made an http request
	// BE: Tomcat received the request
	//     Tomcat called the function below b/c it mapped to /users, Get
	
//	example of return all user
//	@GetMapping
//	public User[] getAll() {
//		return users;
//	}
	
	
	//for login
	@GetMapping
	//these "username=${username}&password=${password}" called URL , Spring calls them RequestParam
	//Iterable<User> means I can return a list of User Object
	//always return a list which could be interpreted as iterable
	public Iterable<User> getByUsernameAndPassword(@RequestParam(required = false) String username,@RequestParam(required = false) String password ) {
			//this if condition functionality to get all the users 
		if (username == null && password == null) 
				return users;	
		// to check if the username is taken, if the object is null
		//FE: this.http.get<User[]>(`http://localhost:8080/users?username=${username}`}
		if (password == null) 
			for(User user : users) {
				if (user.username.equals(username)) {
					//return an array with a single object [{id:,username:,password:,doctor:}]
					return List.of(new User[]{user});
				}
			} 
			else 
				//this.http.get<User[]>(`http://localhost:8080/users?username=${username}&password=${password}`}
				for(User user : users) {
					//check the content of the user.username = the content of username and (2 separate Object , their content is the same)  
					//for string : == is looking at memory locations , equal method is looking at the content of the memory at each of these locations
					//camparing value with equal(), comparing location with ==
					if (user.username.equals(username) && user.password.equals(password)) {
						//return the first one match
						return List.of(new User[]{user});
					}
				}
		return List.of(new User[]{});
	};

		
	
	//for register
	//this.http.post(`http://localhost:3000/users`,{id: null, username,password,doctor})
	@PostMapping
	public ResponseEntity<Void> register(@RequestBody User user) {
		for(User existingUser : users) {
			if(user.username.equals(existingUser.username)) {
				//HttpStatus is an enum class in the Spring Framework for Java that represents the HTTP status codes.
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		}
		//override the id with a current value of nextUserId
		user.id = nextUserId++;
		users.add(user);
		//return status for the FE
		//ResponseEntity :represents the entire HTTP response, response with a specific status code, such as 404 (not found)
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	

	
}
