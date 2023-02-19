package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoctorWebsiteApplication {
	//Java needs a dedicated place to 'start' the program which is the main method
	//it does not just start at the top of a file
	//Java is a compiled language

	public static void main(String[] args) {
		//this is a method signature in Java that serves as the entry point for a Java program.
		//When you run a Java program, the Java Virtual Machine (JVM) looks for this method in 
		//the specified class and calls it to start the program's execution.
		
		//public: This keyword is an access modifier that indicates that the method is accessible to other classes in the same package or in other packages.
		//static: This keyword indicates that the method belongs to the class and not to any specific instance of the class.
		//void: This keyword specifies that the method does not return a value.
		//main: This is the name of the method, and it is a reserved name in Java.
		//String[] args: This is an array of strings that holds any command-line arguments passed to the program.
		SpringApplication.run(DoctorWebsiteApplication.class, args);
	}
	
}
