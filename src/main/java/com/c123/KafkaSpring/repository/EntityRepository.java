package com.c123.KafkaSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface EntityRepository extends JpaRepository<Map<String, Object>, Long> {
}
