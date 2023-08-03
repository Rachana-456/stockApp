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

    @GetMapping("/send")
    @PreAuthorize("isAuthenticated()")
    public String publishMessage(@RequestBody String message , @RequestHeader("Authorization") String autho){
        String jwtToken=autho.substring(7);
        mps.sendMessage(message,jwtToken);
        return "Data Published";
    }
}
