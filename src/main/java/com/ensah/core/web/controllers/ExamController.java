package com.ensah.core.web.controllers;

import com.ensah.core.bo.Exam;
import com.ensah.core.services.IExamService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/Exam")
public class ExamController {

    @Autowired
    IExamService examService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping
    public ResponseEntity<String> addExamRS(@Valid @RequestBody Exam exam) {
        try {
            Long examId = examService.addExam(exam);
            logger.info("Exam added successfully with ID: {}", examId);
            return ResponseEntity.ok("Exam added successfully.");
        } catch (RuntimeException e) {
            logger.error("Failed to add exam: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to add exam: " + e.getMessage());
        }
    }

    @PutMapping("/{examId}")
    public ResponseEntity<String> updateExamRS(@PathVariable Long examId, @Valid @RequestBody Exam examDetails) {
        try {
            examService.updateExam(examId, examDetails);
            logger.info("Exam with ID {} updated successfully.", examId);
            return ResponseEntity.ok("Exam updated successfully.");
        } catch (RuntimeException e) {
            logger.error("Failed to update exam: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to update exam: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllExamRS() {
        logger.info("Fetching all exams.");
        List<Exam> exams = examService.getAllExams();
        if (exams.isEmpty()) {
            logger.info("No exams found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No exams found.");
        }
        logger.info("Fetched {} exams.", exams.size());
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<?> getOneExamRS(@PathVariable Long examId) {
        logger.info("Fetching exam with ID: {}", examId);
        Exam exam = examService.getExamById(examId);
        if (exam == null) {
            logger.info("Exam with ID {} not found.", examId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Exam with ID " + examId + " not found.");
        }
        logger.info("Fetched exam: {}", exam);
        return ResponseEntity.ok(exam);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<String> deleteExamRS(@PathVariable Long examId) {
        try {
            examService.deleteExam(examId);
            logger.info("Exam with ID {} has been deleted.", examId);
            return ResponseEntity.ok("Exam with ID " + examId + " has been deleted.");
        } catch (RuntimeException e) {
            logger.error("Failed to delete exam: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to delete exam: " + e.getMessage());
        }
    }

    @PostMapping("/report/{examId}")
    public ResponseEntity<Exam> addReport(@PathVariable Long examId,
                                          @RequestParam String report,
                                          @RequestParam(required = false) MultipartFile pvFile,
                                          @RequestParam(required = false) MultipartFile examStatementFile) {
        try {
            Exam exam = examService.addDocumentsAfterEXam(examId, report, pvFile, examStatementFile);
            return ResponseEntity.ok(exam);
        } catch (IOException e) {
            logger.error("Failed to add report: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
