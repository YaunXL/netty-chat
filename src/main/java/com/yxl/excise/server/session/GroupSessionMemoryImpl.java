package com.yxl.excise.server.session;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class GroupSessionMemoryImpl implements GroupSession{

    private final Map<String,Group> groups = new ConcurrentHashMap<>();

    @Override
    public Group createGroup(String name, Set<String> members) {
        //创建一个群group
        Group group = groups.get(name);
        if(group!=null){
           log.error("群组{}已存在,请重新创建！",name);
        }
        Group newGroup = new Group(name,members);
        groups.put(name,newGroup);
        return newGroup;
    }

    @Override
    public Group joinGroup(String name, String member) {
        Group group = groups.get(name);
        if(group==null){
            log.error("该群组{}不存在,请先创建！",name);
            return null;
        }
        Set<String> members = group.getMembers();
        if(members.contains(member)){
            log.error("该成员{}已在群聊中！",member);
        }
        members.add(member);
        return group;
    }

    @Override
    public Group removerMember(String name, String member) {
        //移除群成员
        Group group = groups.get(name);
        if(group==null){
            log.error("该群组{}不存在,请先创建！",name);
            return null;
        }
        Set<String> members = group.getMembers();
        members.remove(member);
        return group;
    }

    @Override
    public Group removeGroup(String name) {
        Group group = groups.remove(name);
        return group;
    }

    @Override
    public Set<String> getMembers(String name) {
        Group group = groups.get(name);
        Set<String> members = group.getMembers();
        return members;
    }

    @Override
    public List<Channel> getMembersChannel(String name) {
        List<Channel> res = new ArrayList<>();
        Set<String> members = getMembers(name);
        Session session = SessionFactory.createSession();
        for (String member:
             members) {
            Channel channel = session.getChannel(member);
            res.add(channel);
        }
        return res;
    }
}
