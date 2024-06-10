package com.ensah.core.web.controllers;
import com.ensah.core.bo.Exam;
import com.ensah.core.services.IExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;





@RestController
@RequestMapping("api/Exam")
public class ExamController {

    @Autowired
    IExamService examService;



    @PostMapping()
    public ResponseEntity<Long> addExamRS(@RequestBody Exam exam) throws IOException {

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
                                          @RequestParam("duree") String duree,
                                          @RequestParam("report") MultipartFile report,
                                          @RequestParam("pvFile") MultipartFile pvFile) throws IOException {
        String repotfileName = report.getOriginalFilename();
        String filePath = "/rapport/" + repotfileName; // Adjust path based on your property
        File dest = new File(filePath);
        report.transferTo(dest);

        String pvfileName = pvFile.getOriginalFilename();
        String filePathpv = "/rapport/" + pvfileName; // Adjust path based on your property
        File destPV = new File(filePathpv);
        pvFile.transferTo(destPV);

//        String examStatementName = examStatementFile.getOriginalFilename();
//        String filePathStatemnt = "/rapport/" + examStatementName; // Adjust path based on your property
//        File destStatement = new File(filePathStatemnt);
//        examStatementFile.transferTo(destStatement);


        Exam exam = examService.addDocumentsAfterEXam(examId, filePath, filePathpv, duree);
        return ResponseEntity.ok(exam);
    }


}


