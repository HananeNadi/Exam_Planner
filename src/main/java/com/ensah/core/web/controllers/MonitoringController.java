package com.ensah.core.web.controllers;

import com.ensah.core.bo.Monitoring;
import com.ensah.core.services.IMonitoringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("api/monitorings")
public class MonitoringController {

    @Autowired
    private IMonitoringService monitoringService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping
    public ResponseEntity<String> addMonitoringRS(@Valid @RequestBody Monitoring monitoring) {
        try {
            monitoringService.addMonitoring(monitoring);
            logger.info("Monitoring added successfully.");
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Failed to add monitoring: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to add monitoring: " + e.getMessage());
        }
    }

    @PutMapping("/{monitoringId}")
    public ResponseEntity<String> updateMonitoringRS(@PathVariable Long monitoringId, @Valid @RequestBody Monitoring monitoringDetails) {
        try {
            monitoringService.updateMonitoring(monitoringId, monitoringDetails);
            logger.info("Monitoring with ID {} updated successfully.", monitoringId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Failed to update monitoring: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to update monitoring: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Monitoring>> getAllMonitoringRS() {
        logger.info("Fetching all monitorings.");
        List<Monitoring> monitorings = monitoringService.getAllMonitoring();
        logger.info("Fetched {} monitorings.", monitorings.size());
        return ResponseEntity.ok(monitorings);
    }

    @GetMapping("/{monitoringId}")
    public ResponseEntity<Monitoring> getOneMonitoringRS(@PathVariable Long monitoringId) {
        logger.info("Fetching monitoring with ID: {}", monitoringId);
        Monitoring monitoring = monitoringService.getMonitoringById(monitoringId);
        if (monitoring == null) {
            logger.info("Monitoring with ID {} not found.", monitoringId);
            return ResponseEntity.notFound().build();
        }
        logger.info("Fetched monitoring: {}", monitoring);
        return ResponseEntity.ok(monitoring);
    }

    @DeleteMapping("/{monitoringId}")
    public ResponseEntity<String> deleteMonitoringRS(@PathVariable Long monitoringId) {
        try {
            monitoringService.deleteMonitoring(monitoringId);
            logger.info("Monitoring with ID {} deleted successfully.", monitoringId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Failed to delete monitoring: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to delete monitoring: " + e.getMessage());
        }
    }

    @GetMapping("/date/{dateExam}")
    public ResponseEntity<List<Monitoring>> getMonitoringByDate(@PathVariable String dateExam) {
        logger.info("Fetching monitorings for date: {}", dateExam);
        List<Monitoring> monitorings = monitoringService.getMonitoringByDate(dateExam);
        logger.info("Fetched {} monitorings for date: {}", monitorings.size(), dateExam);
        return ResponseEntity.ok(monitorings);
    }
}
