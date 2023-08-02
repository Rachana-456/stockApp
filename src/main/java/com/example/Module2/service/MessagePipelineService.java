package com.example.Module2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePipelineService {

    @Autowired
    private KafkaTemplate<String,Object> template;
    private String topic = "send";
    public void sendMessage( String message){
        template.send(topic,message);

    }
}
