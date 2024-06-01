package com.ensah.core.dao;

import com.ensah.core.bo.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IPersonDao extends JpaRepository<Person, Long> {


    List<Person> findAllByType(String type);

    Person getPersonByCin(String cin);
}
