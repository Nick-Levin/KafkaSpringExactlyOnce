package com.c123.KafkaSpring.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("sequences")
public class SequenceConfiguration {

    private int start;
    private int skip;
    private List<String> names;

}
