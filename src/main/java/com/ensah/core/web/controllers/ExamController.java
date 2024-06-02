package com.ensah.core.web.controllers;
import com.ensah.core.bo.Exam;
import com.ensah.core.services.IExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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



    @PostMapping()
    public ResponseEntity<Long> addExamRS(@RequestBody Exam exam) {

        return ResponseEntity.ok(examService.addExam(exam));
    }


    @PutMapping("/{examId}")
    public ResponseEntity<Exam> updateExamRS(@PathVariable Long examId, @Valid @RequestBody Exam examDetails) {
        examService.updateExam(examId, examDetails);
        return ResponseEntity.noContent().build();
    }



    @GetMapping
    public ResponseEntity<List<Exam>> getAllExamRS() {
        return ResponseEntity.ok(examService.getAllExams());
    }




    @GetMapping("/{examId}")
    public ResponseEntity<Exam> getOneExamRS(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getExamById(examId));

    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExamRS(@PathVariable Long examId) {
        examService.deleteExam(examId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/report/{examId}")
    public ResponseEntity<Exam> addReport(@PathVariable Long examId,
                                          @RequestParam String report,
                                          @RequestParam(required = false) MultipartFile pvFile,
                                          @RequestParam(required = false) MultipartFile examStatementFile) {
        try {
            Exam exam = examService.addReport(examId, report, pvFile, examStatementFile);
            return ResponseEntity.ok(exam);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }


}


