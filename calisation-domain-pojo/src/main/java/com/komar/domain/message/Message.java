package com.komar.domain.message;

public class Message {

    private final MessageType messageType;
    private final String messageText;

    public Message(MessageType messageType, String messageText) {
        this.messageType = messageType;
        this.messageText = messageText;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getMessageText() {
        return messageText;
    }
}
