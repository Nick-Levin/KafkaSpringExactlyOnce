package com.c123.KafkaSpring.service;

import com.c123.KafkaSpring.entity.Counter;
import com.c123.KafkaSpring.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmployeeGeneratorImpl implements EmployeeGenerator {

    @Autowired
    CounterRepository counterRepository;

    @Value("${generator.employee.hire_date.min}")
    int hireDateMin;

    @Value("${generator.employee.hire_date.max}")
    int hireDateMax;

    @Value("${generator.employee.dept_id.min}")
    int deptIdMin;

    @Value("${generator.employee.dept_id.max}")
    int deptIdMax;

    @Value("${generator.employee.salary.min}")
    int salaryMin;

    @Value("${generator.employee.salary.max}")
    int salaryMax;

    private Random random = new Random();

    @PostConstruct
    public void init(){
        if(!counterRepository.findById("employees").isPresent()) {
            counterRepository.save(new Counter("employees", 1));
        }
    }

    @Override
    public Map<String, Object> generate() {
        Map<String, Object> employee = new HashMap<>();
        employee.put("id", getId());
        employee.put("name", getName());
        employee.put("hire_date", getHireDate());
        employee.put("operation_date", new Date(Instant.now().toEpochMilli()));
        employee.put("salary", getSalary());
        employee.put("dept_id", getDeptId());
        return employee;
    }

    public long getId() {
        return counterRepository.findById("employees").orElseGet(() -> {
            throw new RuntimeException("counter for employees wasn't initialized");
        }).getLastId();
    }

    private String getName() {
        return GeneratorConstants.getName();
    }

    private Date getHireDate() {
        int days = random.nextInt(hireDateMax - hireDateMin) + hireDateMin ;
        return new Date(Instant.now().minusSeconds(60*60*24*days).toEpochMilli());
    }

    private double getSalary() {
        int salary = random.nextInt(salaryMax - salaryMin) + salaryMin;
        return salary == salaryMax ? salary : salary + random.nextDouble();
    }

    private long getDeptId() {
        return random.nextInt(deptIdMax - deptIdMin) + deptIdMin;
    }

    private static class GeneratorConstants {
        private static String[] names = {"Nick", "Eyal", "Regev"};
        private static Random random = new Random();

        public static String getName() {
            return names[random.nextInt(names.length)];
        }

        public static String getName(int index) {
            return names[index];
        }
    }

}
