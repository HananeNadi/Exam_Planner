package com.ensah.core.services;

import com.ensah.core.bo.Person;
import com.ensah.core.dto.PersonDTO;
import com.ensah.core.utils.ExcelExporter;

import java.util.List;



public interface IPersonService {


    public void addPerson(PersonDTO personDTO);

    public void updatePerson(Long personId, PersonDTO personDTO);

    public List<Person> getAllPersons();

    public void deletePerson(Long id);

    public Person getPersonById(Long id);

    public Person getPersonByCin(String cin);

    public List<Person> findAllProfessors();

    public ExcelExporter preparePersonneExport(List<Person> persons);

}
