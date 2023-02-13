package com.razkuuuuuuu.touragency.web.message;

import java.io.Serializable;

import static com.razkuuuuuuu.touragency.web.message.Message.MessageType.INFO;

public class Message implements Serializable {
    public enum MessageType {
        INFO, ERROR
    }
    private MessageType messageType;
    private String messageCode;
    public Message() {
        messageCode="";
        messageType=INFO;
    }
    public String getType() {
        return messageType.name();
    }
    public String getMessageCode(){
        return messageCode;
    }
    public void setMessageType(MessageType type) {
        this.messageType=type;
    }
    public void setMessageCode(String code) {
        this.messageCode=code;
    }
}
