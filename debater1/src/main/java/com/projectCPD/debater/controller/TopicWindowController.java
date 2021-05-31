package com.projectCPD.debater.controller;

import com.projectCPD.debater.pubsub.KeyEnum;
import com.projectCPD.debater.pubsub.publisher.Publisher;
import com.projectCPD.debater.view.TopicWindowView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopicWindowController {
    private final TopicWindowView topicWindowView;
    private final Publisher publisher;

    public TopicWindowController(TopicWindowView topicWindowView, Publisher publisher) {
        this.topicWindowView = topicWindowView;
        this.publisher = publisher;

        this.topicWindowView.addSendButton1Listener(new SendTopic1ActionListener());
        this.topicWindowView.addSendButton2Listener(new SendTopic2ActionListener());
    }

    public class SendTopic1ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String message = topicWindowView.getTextView();
            publisher.produceMsg(message, KeyEnum.PLASTIC.label);
            topicWindowView.emptyTextView();
        }
    }

    public class SendTopic2ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String message = topicWindowView.getTextView();
            publisher.produceMsg(message, KeyEnum.EUTHANASIA.label);
            topicWindowView.emptyTextView();
        }
    }
}
