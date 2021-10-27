package com.yxl.excise.server.session;

public class GroupSessionFactory {

    public static GroupSession createGroupSessionMemory(){
        return new GroupSessionMemoryImpl();
    }
}
