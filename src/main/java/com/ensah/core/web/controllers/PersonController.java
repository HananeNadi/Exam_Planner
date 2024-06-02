package com.ensah.core.web.controllers;


import com.ensah.core.bo.Person;
import com.ensah.core.bo.User;
import com.ensah.core.dao.IUserDao;
import com.ensah.core.dto.PersonDTO;
import com.ensah.core.services.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/personnel")
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    IPersonService personService;
    @Autowired
    IUserDao userDao;
    @Autowired
    PasswordEncoder encoder;
    private final SecureRandom secureRandom = new SecureRandom();

    @PostMapping()
    public ResponseEntity<Void> addPerson(@RequestBody PersonDTO personDTO) {
        personService.addPerson(personDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{personnelId}")
    public ResponseEntity<Void> updatePerson(@PathVariable Long personnelId, @RequestBody PersonDTO personDTO) {
        personService.updatePerson(personnelId, personDTO);
        return ResponseEntity.ok().build();

    }
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersonRS() {
        List<Person> Persons = personService.getAllPersons();

        if (Persons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Persons, HttpStatus.OK);
    }


    @GetMapping("/{personnelId}")
    public ResponseEntity<Person> getOnePersonRS(@PathVariable Long personnelId) {
        Person Person = personService.getPersonById(personnelId);

        if (Person== null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(Person, HttpStatus.OK);
    }

    @DeleteMapping("/{personnelId}")
    public ResponseEntity<Void> deletePersonRS(@PathVariable Long personnelId) {
        personService.deletePerson(personnelId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/{personnelId}")
    public ResponseEntity<String> setPersonnelUser(@PathVariable Long personnelId) {
        Person p = personService.getPersonById(personnelId);
        String password = generateRandomPassword(10);
        User u = new User(p.getEmail(), encoder.encode(password), "ROLE_USER");
        userDao.save(u);
        return ResponseEntity.ok(password);
    }

    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return password.toString();
    }

    //validation handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handValidEx(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        List<ObjectError> validationErros = ex.getBindingResult().getAllErrors();
        for (ObjectError error : validationErros) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }

        return ResponseEntity.badRequest().body(errors);
    }


}


