package com.example.demo.models;

public class User {
	//in Java , 1st we declare the field
	public Long id; // Object Long not Primitive long
	public String username;
	public String password;
	public boolean doctor; // Primitive boolean not Object Boolean
	
	//Declare constructor
	//Constructor in Java have the same name in the class up and there is no return type it assumed that the return type is the User type
	
	//default constructor
	public User() {}
	//in TypeScript: we declare the constructor argument , field and initialization all in one
	
	//2nd we declare constructor`s argument 
	public User(Long id, String username, String password, boolean doctor) {
		//set the field  using 'this.' to local value
		this.id = id;// 1st id is field , 2nd id is local
		this.username = username;
		this.password = password;
		this.doctor = doctor;
		
		
		}
	}

