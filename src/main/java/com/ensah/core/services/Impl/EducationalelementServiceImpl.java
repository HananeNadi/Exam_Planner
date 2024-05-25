package com.ensah.core.services.Impl;

import com.ensah.core.bo.Educationalelement;
import com.ensah.core.bo.Group;
import com.ensah.core.dao.IEducationalelementDao;
import com.ensah.core.services.IEducationalelementService;
import com.ensah.core.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class EducationalelementServiceImpl implements IEducationalelementService {

    @Autowired
    IEducationalelementDao elementDao;

    public void addElement(Educationalelement element) {
        elementDao.save(element);

    }

    public void updateElement(Long elementId, Educationalelement pelement) {
        Optional<Educationalelement> educationalelementOptional = elementDao.findById(elementId);
        if (educationalelementOptional.isEmpty()) {
            throw new ResourceNotFoundException("Educationalelement", "id", elementId);
        }
        Educationalelement educationalelement = educationalelementOptional.get();

        if (pelement.getTitle() != null) {
            educationalelement.setTitle(pelement.getTitle());
        }

        if (pelement.getLevel() != null) {
            educationalelement.setLevel(pelement.getLevel());
        }

        if (pelement.getProfessor() != null) {
            educationalelement.setProfessor(pelement.getProfessor());
        }

        if (pelement.getCoordinator() != null) {
            educationalelement.setCoordinator(pelement.getCoordinator());
        }

        elementDao.save(educationalelement);
    }




    public List<Educationalelement> getAllElement() {
        return elementDao.findAll();    }

    public void deleteElement(Long id) {
        if (getElementById(id) != null){
            elementDao.deleteById(id);

        }

    }

    public Educationalelement getElementById(Long id) {
        return elementDao.findById(id).get();

    }

    public Educationalelement getElementByTitle(String title) {
        return elementDao.getPersonByTitle(title);
    }
}
