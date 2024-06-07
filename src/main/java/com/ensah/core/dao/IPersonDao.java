package com.ensah.core.dao;

import com.ensah.core.bo.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IPersonDao extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);
    boolean existsByCin(String cin);

    List<Person> findAllByType(String type);

    Person getPersonByCin(String cin);
}
