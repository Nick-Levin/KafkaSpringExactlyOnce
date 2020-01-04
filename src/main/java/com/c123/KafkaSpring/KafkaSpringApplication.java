package com.c123.KafkaSpring;

import com.c123.KafkaSpring.service.EmployeeGenerator;
import com.c123.KafkaSpring.service.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.List;

@SpringBootApplication
public class KafkaSpringApplication implements CommandLineRunner {

	@Autowired
	EmployeeGenerator employeeGenerator;

	@Autowired
	Logger logger;

	@Autowired
	Producer producer;

	@Value("${generator.amount}")
	private int amount;

	public static void main(String ...args) {
		SpringApplication.run(KafkaSpringApplication.class, args);
	}

	@Override
	public void run(String ...args) throws Exception {
		employeeGenerator.generateList(amount)
			.forEach(emp -> {
				try {
					ObjectMapper mapper = new ObjectMapper();
					producer.sendMessageSync((long) emp.get("id") ,mapper.writeValueAsString(emp));
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
					throw new RuntimeException("KAFKA_PRODUCER_ERROR: " + e.getMessage());
				}
			});
	}
}
