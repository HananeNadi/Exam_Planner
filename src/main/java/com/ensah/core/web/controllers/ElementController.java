package com.ensah.core.web.controllers;
import com.ensah.core.bo.Educationalelement;
import com.ensah.core.services.IEducationalelementService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Educationalelement")
public class ElementController {

    @Autowired
    IEducationalelementService educationalelementService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping
    public ResponseEntity<String> addEducationalelementRS(@Valid @RequestBody Educationalelement educationalelement) {
        logger.info("Adding educational element: {}", educationalelement);
        educationalelementService.addElement(educationalelement);
        logger.info("Educational element added successfully.");
        return ResponseEntity.ok("Educational element added successfully.");
    }

    @PutMapping("/{EducationalelementnelId}")
    public ResponseEntity<String> updateEducationalelementRS(@PathVariable Long EducationalelementnelId, @Valid @RequestBody Educationalelement EducationalelementnelDetails) {
        logger.info("Updating educational element with ID: {}", EducationalelementnelId);
        educationalelementService.updateElement(EducationalelementnelId, EducationalelementnelDetails);
        logger.info("Educational element with ID {} updated successfully.", EducationalelementnelId);
        return ResponseEntity.ok("Educational element updated successfully.");
    }

    @GetMapping
    public ResponseEntity<?> getAllEducationalelementRS() {
        logger.info("Fetching all educational elements.");
        List<Educationalelement> elements = educationalelementService.getAllElement();
        if (elements.isEmpty()) {
            logger.info("No educational elements found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No educational elements found.");
        }
        logger.info("Fetched {} educational elements.", elements.size());
        return ResponseEntity.ok(elements);
    }

    @GetMapping("/{EducationalelementnelId}")
    public ResponseEntity<?> getOneEducationalelementRS(@PathVariable Long EducationalelementnelId) {
        logger.info("Fetching educational element with ID: {}", EducationalelementnelId);
        Educationalelement element = educationalelementService.getElementById(EducationalelementnelId);
        if (element == null) {
            logger.info("Educational element with ID {} not found.", EducationalelementnelId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Educational element with ID " + EducationalelementnelId + " not found.");
        }
        logger.info("Fetched educational element: {}", element);
        return ResponseEntity.ok(element);
    }

    @DeleteMapping("/{EducationalelementnelId}")
    public ResponseEntity<String> deleteEducationalelementRS(@PathVariable Long EducationalelementnelId) {
        logger.info("Deleting educational element with ID: {}", EducationalelementnelId);
        educationalelementService.deleteElement(EducationalelementnelId);
        logger.info("Educational element with ID {} has been deleted.", EducationalelementnelId);
        return ResponseEntity.ok("Educational element with ID " + EducationalelementnelId + " has been deleted.");
    }
}
