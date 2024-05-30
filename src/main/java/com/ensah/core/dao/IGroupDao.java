package com.ensah.core.dao;

import com.ensah.core.bo.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IGroupDao extends JpaRepository<Group, Long> {
    Group getGroupByTitle(String title);

}
