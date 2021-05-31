package com.projectCPD.debater.pubsub.subscriber;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {
    private String message;

    @RabbitListener(queues = "${jsa.rabbitmq.queue1}")
    public void receivedAnimalMessage(String msg) {
        System.out.println(msg);
        message = msg;
    }

    @RabbitListener(queues = "${jsa.rabbitmq.queue2}")
    public void receivedPlasticMessage(String msg) {
        System.out.println(msg);
        message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
