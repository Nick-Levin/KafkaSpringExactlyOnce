package com.c123.KafkaSpring.repository;

import com.c123.KafkaSpring.entity.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, String> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    <S extends Sequence> S save(S s);
}
