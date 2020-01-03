package com.c123.KafkaSpring;

import com.c123.KafkaSpring.service.EmployeeGenerator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaSpringApplication implements CommandLineRunner {

	@Autowired
	EmployeeGenerator employeeGenerator;

	@Autowired
	Logger logger;

	public static void main(String ...args) {
		SpringApplication.run(KafkaSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		employeeGenerator.generateList(10).forEach(emp -> logger.debug(emp.toString()));

	}
}
