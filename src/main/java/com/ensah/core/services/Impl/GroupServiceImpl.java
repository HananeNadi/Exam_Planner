package com.ensah.core.services.Impl;

import com.ensah.core.bo.Group;
import com.ensah.core.dao.IGroupDao;
import com.ensah.core.services.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements IGroupService {

    @Autowired
    IGroupDao GroupDao;

    public void addGroup(Group pGroup) {
        GroupDao.save(pGroup);

    }

    public void updateGroup(Long groupId,Group pGroup) {
        GroupDao.save(pGroup);

    }

    public List<Group> getAllGroups() {
        return GroupDao.findAll();

    }

    public void deleteGroup(Long id) {
        if (getGroupById(id) != null){
            GroupDao.deleteById(id);
        }

    }

    public Group getGroupById(Long id) {
        return GroupDao.findById(id).get();

    }

    @Override
    public Group getGroupByTitle(String title) {
        return GroupDao.getGroupByTitle(title);
    }

}
