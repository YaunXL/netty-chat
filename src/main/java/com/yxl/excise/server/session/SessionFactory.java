package com.yxl.excise.server.session;

public class SessionFactory {

    public static Session createSession(){
        return new SessionMemoryImpl();
    }
}
