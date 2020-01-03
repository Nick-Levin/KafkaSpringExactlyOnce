package com.c123.KafkaSpring.service;

import java.util.List;
import java.util.Map;

public interface EmployeeGenerator {

    Map<String, Object> generate();
    List<Map<String, Object>> generateList(int length);

}
