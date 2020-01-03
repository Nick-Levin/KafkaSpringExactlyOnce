package com.c123.KafkaSpring.service;

import com.c123.KafkaSpring.entity.Counter;
import com.c123.KafkaSpring.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Instant;
import java.util.*;

@Service
public class EmployeeGeneratorImpl implements EmployeeGenerator {

    @Autowired
    CounterRepository counterRepository;

    @Value("${generator.employee.hire_date.min}")
    private int hireDateMin;

    @Value("${generator.employee.hire_date.max}")
    private int hireDateMax;

    @Value("${generator.employee.dept_id.min}")
    private int deptIdMin;

    @Value("${generator.employee.dept_id.max}")
    private int deptIdMax;

    @Value("${generator.employee.salary.min}")
    private int salaryMin;

    @Value("${generator.employee.salary.max}")
    private int salaryMax;

    @Value("${generator.counter_row_name}")
    private String counterRowName;

    private Random random = new Random();

    @PostConstruct
    public void init(){
        if(!counterRepository.findById(counterRowName).isPresent()) {
            counterRepository.save(new Counter(counterRowName, 1));
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

    @Override
    public List<Map<String, Object>> generateList(int length) {
        List<Map<String, Object>> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) list.add(generate());
        return list;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private long getId() {
        Counter empCounter = counterRepository.findById(counterRowName).orElseGet(() -> {
            throw new RuntimeException("counter for employees wasn't initialized");
        });
        counterRepository.save(new Counter(empCounter.getEntityName(), empCounter.getLastId()+1));
        return empCounter.getLastId();
    }

    private String getName() {
        return GeneratorConstants.getName();
    }

    private Date getHireDate() {
        int days = random.nextInt(hireDateMax - hireDateMin) + hireDateMin ;
        return new Date(Instant.now().minusSeconds(60*60*24*days).toEpochMilli());
    }

    private double getSalary() {
        int intSalary = random.nextInt(salaryMax - salaryMin) + salaryMin;
        return new BigDecimal(
                intSalary == salaryMax ?
                intSalary :
                intSalary + random.nextDouble())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private long getDeptId() {
        return random.nextInt(deptIdMax - deptIdMin) + deptIdMin;
    }

    private static class GeneratorConstants {
        private static final String[] NAMES = {
                "Christina","Aiko", "Gus", "Lina", "Tammie","Tiffany", "Mariel","Mavis","Alease","Aisha",
                "Lavern","Shaunte","Wendie","Cyrus","America","Marcy","Vasiliki","Marie","Kira",
                "Lynnette","Vernon","Janette","Gale","Brunilda","Sharda","Weldon","Delta","Thomas",
                "Barabara","Svetlana","Dierdre","Lani","Phylis","Peggie","Lavenia","Clement","Nicolasa",
                "Carisa","Emilee","Shawnee","Londa","Jacqulyn","Paola","Emmie","Denis","Chastity",
                "Fritz","Waneta","Claretta","Mervin"
        };
        private static final Random RANDOM = new Random();

        private static String getName() {
            return NAMES[RANDOM.nextInt(NAMES.length)];
        }

        private static String getName(int index) {
            return NAMES[index];
        }
    }

}
