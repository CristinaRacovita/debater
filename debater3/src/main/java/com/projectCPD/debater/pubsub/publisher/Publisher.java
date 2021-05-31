package com.projectCPD.debater.pubsub.publisher;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${jsa.rabbitmq.exchange}")
    private String exchange;

    @Value("${server.port}")
    private Integer serverPort;

    public void produceMsg(String msg, String key) {
        String prefix = "";
        switch (key) {
            case "animal":
                prefix = serverPort + ": Animal Testing - ";
                break;
            case "plastic":
                prefix = serverPort + ": Plastic - ";
                break;
            case "euthanasia":
                prefix = serverPort + ": Euthanasia - ";
                break;

        }
        amqpTemplate.convertAndSend(exchange, key, prefix + msg);
    }
}