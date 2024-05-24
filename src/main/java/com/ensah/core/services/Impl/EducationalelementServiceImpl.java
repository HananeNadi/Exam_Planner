package com.ensah.core.services.Impl;

import com.ensah.core.bo.Educationalelement;
import com.ensah.core.dao.IEducationalelementDao;
import com.ensah.core.services.IEducationalelementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class EducationalelementServiceImpl implements IEducationalelementService {

    @Autowired
    IEducationalelementDao elementDao;

    public void addElement(Educationalelement element) {
        elementDao.save(element);

    }

    public void updateElement(Long elementId,Educationalelement element) {
        elementDao.save(element);


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
