package com.projectCPD.debater.view;

import com.projectCPD.debater.pubsub.subscriber.Subscriber;
import com.projectCPD.debater.sockets.Token;
import com.projectCPD.debater.sockets.TokenState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TopicWindowView extends JFrame {
    private final JLabel currentToken = new JLabel();

    private final JTextField message = new JTextField(15);
    private final JTextArea chatArea = new JTextArea(10, 50);

    private final JButton topic1Button = new JButton("Send - Plastic Topic");
    private final JButton topic2Button = new JButton("Send - Animal Testing Topic");

    private Subscriber subscriber;
    private Integer serverPort;

    Timer timer = new Timer(1000, e -> {
        if (Token.value != null && Token.value.equals(TokenState.DEBATE.label)) {
            currentToken.setText("Now  you can debate!");
            message.setEditable(true);

            topic1Button.setEnabled(true);
            topic2Button.setEnabled(true);
        } else {
            currentToken.setText("Waiting....");
            message.setEditable(false);

            topic1Button.setEnabled(false);
            topic2Button.setEnabled(false);
        }

        if (subscriber != null && subscriber.getMessage() != null && !subscriber.getMessage().contains(serverPort.toString())) {
            writeMessage(subscriber.getMessage());
            subscriber.setMessage(null);
        }
    });

    public TopicWindowView(String topicName, Subscriber subscriber, Integer serverPort) {
        this.subscriber = subscriber;
        this.serverPort = serverPort;

        this.setTitle(topicName + serverPort);
        this.setSize(600, 300);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatArea.setEditable(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.add(topic1Button);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(topic2Button);

        JPanel messagePanel = new JPanel();
        messagePanel.add(message);

        JPanel chatPanel = new JPanel();
        chatPanel.add(scrollPane);

        JPanel textButtonPanel = new JPanel();
        textButtonPanel.add(buttonPanel1);
        textButtonPanel.add(messagePanel);
        textButtonPanel.add(buttonPanel);
        textButtonPanel.setLayout(new BoxLayout(textButtonPanel, BoxLayout.X_AXIS));

        JPanel labelPanel = new JPanel();
        labelPanel.add(currentToken);

        JPanel allPanel = new JPanel();
        allPanel.add(labelPanel);
        allPanel.add(chatPanel);
        allPanel.add(textButtonPanel);
        allPanel.setLayout(new BoxLayout(allPanel, BoxLayout.Y_AXIS));

        timer.start();
        this.setVisible(true);
        this.add(allPanel);
    }

    public void writeMessage(String message) {
        String currentText = chatArea.getText();
        chatArea.setText(currentText + message + "\n");
    }

    public void addSendButton1Listener(ActionListener listener) {
        topic1Button.addActionListener(listener);
    }

    public void addSendButton2Listener(ActionListener listener) {
        topic2Button.addActionListener(listener);
    }

    public void emptyTextView() {
        message.setText("");
    }

    public String getTextView() {
        return message.getText();
    }

}
