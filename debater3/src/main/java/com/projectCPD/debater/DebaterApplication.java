package com.projectCPD.debater;

import com.projectCPD.debater.controller.TopicWindowController;
import com.projectCPD.debater.pubsub.publisher.Publisher;
import com.projectCPD.debater.pubsub.subscriber.Subscriber;
import com.projectCPD.debater.sockets.Token;
import com.projectCPD.debater.sockets.TokenState;
import com.projectCPD.debater.sockets.client.TokenClient;
import com.projectCPD.debater.sockets.server.TokenServer;
import com.projectCPD.debater.view.TopicWindowView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.swing.*;
import java.io.IOException;

@SpringBootApplication
public class DebaterApplication {
    private static final int SERVER_PORT = 3333;
    private static final int CLIENT_PORT = 6756;
    private static final String START_TOKEN = TokenState.PENDING.label;
    private static final String CLIENT_IP = "127.0.0.1";

    @Bean
    public void runOnStartup() {
        Token.value = START_TOKEN;

        TokenServer tokenServer = new TokenServer(SERVER_PORT);
        tokenServer.start();

        TokenClient tokenClient = new TokenClient(CLIENT_PORT, CLIENT_IP);
        tokenClient.start();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DebaterApplication.class, args);
        Publisher publisher = context.getBean(Publisher.class);
        Subscriber subscriber = context.getBean(Subscriber.class);

        System.setProperty("java.awt.headless", "false");
        SwingUtilities.invokeLater(() -> {
            int port = context.getBean(Environment.class).getProperty("server.port", Integer.class, 8080);
            TopicWindowView topicWindowView = new TopicWindowView("Debating: ", subscriber, port);
            TopicWindowController topicWindowController = new TopicWindowController(topicWindowView, publisher);
        });
    }

}
