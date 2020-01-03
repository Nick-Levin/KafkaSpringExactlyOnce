package com.c123.KafkaSpring;

import com.c123.KafkaSpring.service.EmployeeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaSpringApplication implements CommandLineRunner {

	@Autowired
	EmployeeGenerator employeeGenerator;

	public static void main(String ...args) {
		SpringApplication.run(KafkaSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		employeeGenerator.generateList(50).forEach(System.out::println);

	}
}
