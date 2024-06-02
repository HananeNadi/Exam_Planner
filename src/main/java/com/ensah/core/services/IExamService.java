package com.ensah.core.services;


import com.ensah.core.bo.Exam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IExamService {

    public Long addExam(Exam exam);

    public void updateExam(Long examId,Exam exam);

    public List<Exam> getAllExams();

    public void deleteExam(Long id);

    public Exam getExamById(Long id);
    public Exam addReport(Long examId, String report, MultipartFile pvFile, MultipartFile preuve) throws IOException;






}
