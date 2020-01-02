package com.c123.KafkaSpring.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="counter")
public class Counter {

    @Id
    @Column(name="entity_name", nullable=false, unique=true)
    private String entityName;

    @NotNull
    @Column(name="last_id", nullable=false)
    private long lastId;

}
