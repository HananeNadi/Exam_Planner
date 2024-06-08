package com.ensah.core.services.Impl;

import com.ensah.core.bo.*;
import com.ensah.core.dao.IDepartementDao;
import com.ensah.core.dao.IEducationalelementDao;
import com.ensah.core.dao.IPersonDao;
import com.ensah.core.dao.ISectorDao;
import com.ensah.core.dto.PersonDTO;
import com.ensah.core.utils.ExcelExporter;
import com.ensah.core.web.exceptions.ResourceNotFoundException;
import com.ensah.core.services.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl implements IPersonService {

    @Autowired
    IPersonDao personDao;
    @Autowired
    IEducationalelementDao educationalelementDao;
    @Autowired
    IDepartementDao departementDao;
    @Autowired
    ISectorDao sectorDao;



    public void addPerson(PersonDTO personDTO) {
        if (personDao.existsByCin(personDTO.getCin())) {
            throw new RuntimeException("Person with this Cin already exists");
        }
        if (personDao.existsByEmail(personDTO.getEmail())) {
            throw new RuntimeException("Person with this email already exists");
        }

        Person person;
        if ("Professor".equals(personDTO.getType())){
            Professor professor = new Professor();
            professor.setType(personDTO.getType()); // Set the type attribute
            professor.setFirstName(personDTO.getFirstName());
            professor.setLastName(personDTO.getLastName());
            professor.setEmail(personDTO.getEmail());
            professor.setCin(personDTO.getCin());
            professor.setSpeciality(personDTO.getSpeciality());
            professor.setDepartement(departementDao.findById(personDTO.getIdDepartement()).orElseThrow(() -> new IllegalArgumentException("Invalid departement ID")));
            professor.setSector(sectorDao.findById(personDTO.getIdSector()).orElseThrow(() -> new IllegalArgumentException("Invalid Sector ID")));

            person = professor;
        } else if ("Administrator".equals(personDTO.getType())) {
            Administrator administrator = new Administrator();
            administrator.setType(personDTO.getType()); // Set the type attribute
            administrator.setFirstName(personDTO.getFirstName());
            administrator.setLastName(personDTO.getLastName());
            administrator.setEmail(personDTO.getEmail());
            administrator.setCin(personDTO.getCin());
            administrator.setGrade(personDTO.getGrade());
            person = administrator;
        } else {
            throw new IllegalArgumentException("Invalid person data");
        }

        personDao.save(person);
    }


    @Override
    public void updatePerson(Long personId, PersonDTO personDTO) {
        Optional<Person> personOptional = personDao.findById(personId);
        if (personOptional.isEmpty()) {
            throw new ResourceNotFoundException("Person", "id", personId);
        }

        Person person = personOptional.get();

        // Update common fields if they are not null
        if (personDTO.getFirstName() != null) {
            person.setFirstName(personDTO.getFirstName());
        }
        if (personDTO.getLastName() != null) {
            person.setLastName(personDTO.getLastName());
        }
        if (personDTO.getCin() != null) {
            person.setCin(personDTO.getCin());
        }

        if (person instanceof Professor) {
            if (personDTO.getSpeciality() != null) {
                ((Professor) person).setSpeciality(personDTO.getSpeciality());
            }
        } else if (person instanceof Administrator) {
            if (personDTO.getGrade() != null) {
                ((Administrator) person).setGrade(personDTO.getGrade());
            }
        } else {
            throw new IllegalArgumentException("Invalid person type");
        }

        personDao.save(person);
    }


    public List<Person> getAllPersons() {
        return personDao.findAll();

    }
    public void deletePerson(Long id) {
        Person personToDelete = personDao.findById(id).get();

        if ("Professor".equals(personToDelete.getType())) {
            Professor professor = (Professor) personToDelete;
            for (Group group : professor.getGroups()) {
                group.getProfessors().remove(professor);
            }

            personDao.deleteById(id);
        }
    }



    public Person getPersonById(Long id) {
        Optional<Person> personOptional = personDao.findById(id);
        if (personOptional.isPresent()) {
            return personOptional.get();
        } else {
            System.out.println("Person with id " + id + " doesn't exist");
            return null;
        }
    }


    public Person getPersonByCin(String cin) {
        return personDao.getPersonByCin(cin);
    }

    public List<Person> findAllProfessors() {
        return personDao.findAllByType("Professor");
    }

    public ExcelExporter preparePersonneExport(List<Person> persons) {
        String[] columnNames = new String[] { "FirstName", "LastName", "CIN", "Email", "Type" };
        String[][] data = new String[persons.size()][5];
        int i = 0;
        for (Person u : persons) {
            data[i][0] = u.getFirstName();
            data[i][1] = u.getLastName();
            data[i][2] = u.getCin();
            data[i][3] = u.getEmail();
            data[i][4] = u.getType();
            i++;
        }
        return new ExcelExporter(columnNames, data, "persons");
    }    

}
