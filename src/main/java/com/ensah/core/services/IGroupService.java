package com.ensah.core.services;

import com.ensah.core.bo.Group;

import java.util.List;


public interface IGroupService {

    public void addGroup(Group pGroup);

    public void updateGroup(Long groupId,Group pGroup);

    public List<Group> getAllGroups();

    public void deleteGroup(Long id);

    public Group getGroupById(Long id);

    public Group getGroupByTitle(String title);

}
