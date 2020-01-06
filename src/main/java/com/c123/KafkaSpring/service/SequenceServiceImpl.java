package com.c123.KafkaSpring.service;

import com.c123.KafkaSpring.config.SequenceConfiguration;
import com.c123.KafkaSpring.entity.Sequence;
import com.c123.KafkaSpring.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    SequenceRepository sequenceRepository;

    @Autowired
    SequenceConfiguration sequenceConfiguration;

    private Map<String, Sequence> sequences = new HashMap<>();

    @PostConstruct
    private void init() {
        sequenceConfiguration.getNames().forEach(name -> {
            if(!isExists(name)) {
                Sequence sequence = new Sequence(
                        name,
                        sequenceConfiguration.getStart(),
                        sequenceConfiguration.getStart(),
                        sequenceConfiguration.getSkip()
                );
                sequenceRepository.save(sequence);
                sequences.put(name, sequence);
            } else {
                Sequence sequence = sequenceRepository.findById(name).get();
                sequence.setSize(sequenceConfiguration.getSkip());
                sequence.setTarget(sequence.getLastId());
                sequences.put(name, sequence);
            }
        });
    }

    public long nextVal(String name) {
        Sequence sequence = sequences.get(name);
        if(sequence.isAtLimit()) getNextGroup(sequence.getName());
        return sequence.getAndIncrement();
    }

    private void getNextGroup(String name) {
        sequenceRepository.save(sequences.get(name).expand());
    }

    private boolean isExists(String name) {
        return sequenceRepository.existsById(name);
    }

}
