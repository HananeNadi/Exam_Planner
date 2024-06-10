package com.ensah.core.web.controllers;

import com.ensah.core.bo.Monitoring;
import com.ensah.core.services.IMonitoringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("api/monitorings")
public class MonitoringController {
    @Autowired
    private IMonitoringService monitoringService;


    @PostMapping
    public ResponseEntity<String> addMonitoringRS(@Valid @RequestBody Monitoring monitoring,@RequestParam int nbrMonitors) {
        try {
            monitoringService.addMonitoring(monitoring,nbrMonitors);
            return ResponseEntity.ok("Monitoring added successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to add monitoring: " + e.getMessage());
        }
    }

    @PutMapping("/{monitoringId}")
    public ResponseEntity<Monitoring> updateMonitoringRS(@PathVariable Long monitoringId, @Valid @RequestBody Monitoring monitoringDetails) {
        monitoringService.updateMonitoring(monitoringId, monitoringDetails);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Monitoring>> getAllMonitoringRS() {
        return ResponseEntity.ok(monitoringService.getAllMonitoring());
    }



    @GetMapping("/{monitoringId}")
    public ResponseEntity<Monitoring> getOneMonitoringRS(@PathVariable Long monitoringId) {
        return ResponseEntity.ok(monitoringService.getMonitoringById(monitoringId));

    }

    @DeleteMapping("/{monitoringId}")
    public ResponseEntity<Void> deleteMonitporingRS(@PathVariable Long monitoringId) {
        monitoringService.deleteMonitoring(monitoringId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/date/{dateExam}")
    public ResponseEntity<List<Monitoring>> getMonitoringByDate(@PathVariable String dateExam) {
//        LocalDateTime parsedDateTime = LocalDateTime.parse(dateExam, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        System.out.println("date exam is " + parsedDateTime);
        return ResponseEntity.ok(monitoringService.getMonitoringByDate(dateExam));
    }


//    @PutMapping("/{monitoringId}/addProfessorsToMonitoring")
//    public ResponseEntity<Monitoring>addProfessorsToMonitoring(@PathVariable Long monitoringId, @RequestBody int numberOfProfessors) {
//        monitoringService.addProfessorsToMonitoring(monitoringId, numberOfProfessors);
//        return new ResponseEntity(HttpStatus.OK);
//    }

}
