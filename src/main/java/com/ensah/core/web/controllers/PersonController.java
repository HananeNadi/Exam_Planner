package com.ensah.core.web.controllers;
import com.ensah.core.bo.Person;
import com.ensah.core.bo.User;
import com.ensah.core.dao.IUserDao;
import com.ensah.core.dto.PersonDTO;
import com.ensah.core.services.IPersonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.util.List;

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

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping()
    public ResponseEntity<String> addPerson(@RequestBody PersonDTO personDTO) {
        logger.info("Adding person with details: {}", personDTO);
        try {
            personService.addPerson(personDTO);
            logger.info("Person was added successfully.");
            return ResponseEntity.ok("Person was added successfully.");
        } catch (RuntimeException e) {
            logger.error("Failed to add person: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to add person: " + e.getMessage());
        }
    }

    @PutMapping("/{personnelId}")
    public ResponseEntity<String> updatePerson(@PathVariable Long personnelId, @RequestBody PersonDTO personDTO) {
        logger.info("Updating person with ID: {} and details: {}", personnelId, personDTO);
        personService.updatePerson(personnelId, personDTO);
        logger.info("Person was updated successfully.");
        return ResponseEntity.ok("Person was updated successfully.");
    }

    @GetMapping
    public ResponseEntity<?> getAllPersonRS() {
        logger.info("Fetching all persons.");
        List<Person> persons = personService.getAllPersons();
        if (persons.isEmpty()) {
            logger.info("No persons found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No persons found.");
        }
        logger.info("Fetched {} persons.", persons.size());
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{personnelId}")
    public ResponseEntity<?> getOnePersonRS(@PathVariable Long personnelId) {
        logger.info("Fetching person with ID: {}", personnelId);
        Person person = personService.getPersonById(personnelId);
        if (person == null) {
            logger.info("Person with ID {} not found.", personnelId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Person with ID " + personnelId + " not found");
        }
        logger.info("Fetched person: {}", person);
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/{personnelId}")
    public ResponseEntity<String> deletePersonRS(@PathVariable Long personnelId) {
        logger.info("Deleting person with ID: {}", personnelId);
        personService.deletePerson(personnelId);
        logger.info("Person with ID {} has been deleted.", personnelId);
        return ResponseEntity.ok("Person with ID " + personnelId + " has been deleted.");
    }

    @PostMapping("/user/{personnelId}")
    public ResponseEntity<String> setPersonnelUser(@PathVariable Long personnelId) {
        logger.info("Setting user for personnel with ID: {}", personnelId);
        Person p = personService.getPersonById(personnelId);
        String password = generateRandomPassword(10);
        User u = new User(p.getEmail(), encoder.encode(password), "ROLE_USER");
        userDao.save(u);
        logger.info("User created for personnel ID {}. Generated password: {}", personnelId, password);
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
}
