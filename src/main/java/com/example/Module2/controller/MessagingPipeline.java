package com.example.Module2.controller;


import com.example.Module2.service.MessagePipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessagingPipeline {
    @Autowired
    MessagePipelineService mps;

    @RequestMapping(value = "/send", method = {RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE,RequestMethod.GET})

    public String publishMessage(@RequestBody String message){
        mps.sendMessage(message);

        return "Data Published";
    }
}
