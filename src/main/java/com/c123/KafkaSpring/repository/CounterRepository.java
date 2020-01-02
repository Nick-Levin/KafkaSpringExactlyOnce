package com.c123.KafkaSpring.repository;

import com.c123.KafkaSpring.entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends JpaRepository<Counter, String> {
}
