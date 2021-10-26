package com.yxl.excise.server.session;

import lombok.Data;

import java.util.Set;

@Data
public class Group {

    private String groupName;
    private Set<String> members;

    public Group(String groupName, Set<String> members) {
        this.groupName = groupName;
        this.members = members;
    }
}
