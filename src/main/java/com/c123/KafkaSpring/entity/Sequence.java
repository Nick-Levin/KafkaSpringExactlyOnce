package com.c123.KafkaSpring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sequences")
public class Sequence {

    @Id
    @Column(name="name", nullable=false, unique=true)
    private String name;

    @Column(name="value", nullable=false)
    private long lastId;

    @Transient
    private long target;

    @Transient
    private long size;

    public Sequence expand() {
        target += size;
        return new Sequence(name, target, 0, 0);
    }

    public long getAndIncrement() {
        long current = lastId;
        this.lastId++;
        return current;
    }

    public boolean isAtLimit() {
        return lastId == target;
    }

}
