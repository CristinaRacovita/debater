package com.projectCPD.debater.pubsub.subscriber;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {
    private String message;

    @RabbitListener(queues = "${jsa.rabbitmq.queue2}")
    public void receivedPlasticMessage(String msg) {
        message = msg;
    }

    @RabbitListener(queues = "${jsa.rabbitmq.queue3}")
    public void receivedEuthanasiaMessage(String msg) {
        message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
