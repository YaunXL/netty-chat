package com.yxl.excise.server.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

public interface GroupSession {

    Group createGroup(String name, Set<String> members);

    Group joinGroup(String name,String member);

    /**
     * 删除群里的成员
     * @param name
     * @param member
     * @return
     */
    Group removerMember(String name,String member);

    /**
     * 删除群
     * @param name
     * @return
     */
    Group removeGroup(String name);

    /**
     * 根据群名获取群成员
     * @param name
     * @return
     */
    Set<String> getMembers(String name);

    /**
     * 获取群成员的channel
     * @param name
     * @return
     */
    List<Channel> getMembersChannel(String name);
}
