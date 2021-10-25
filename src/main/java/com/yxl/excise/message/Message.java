package com.yxl.excise.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {

    public static Class<?> getMessageClass(int messageType){
        return messageClasses.get(messageType);
    }

    private int sequenceId;
    private int messageType;

    public abstract int getMessageType();

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;
    public static final int ChatRequestMessage = 2;

    public static Map<Integer,Class<?>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(LoginRequestMessage,LoginRequestMessage.class);
        messageClasses.put(LoginResponseMessage,LoginResponseMessage.class);
        messageClasses.put(ChatRequestMessage,ChatRequestMessage.class);
    }
}
