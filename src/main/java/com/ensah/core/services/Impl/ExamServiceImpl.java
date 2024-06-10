package com.ensah.core.services.Impl;

import com.ensah.core.bo.Exam;
import com.ensah.core.bo.ExamType;
import com.ensah.core.bo.Semester;
import com.ensah.core.bo.Session;
import com.ensah.core.dao.IExamDao;
import com.ensah.core.services.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ExamServiceImpl implements IExamService {

    @Autowired
    IExamDao examDao;

    @Override
    public Long addExam(Exam exam) {
        if (exam.getDuration() == null || exam.getDuration().isEmpty()) {
            exam.setDuration("2h");
        }

        if (exam.getSemester() == null) {
            LocalDate currentDate = LocalDate.now();
            int month = currentDate.getMonthValue();
            exam.setSemester(month >= 9 || month <= 1 ? Semester.AUTOMNE : Semester.PRINTEMPS);
        }

        if (exam.getSession() == null) {
            exam.setSession(Session.NORMAL);
        }
        if (exam.getExamType() == null) {
            LocalDate currentDate = LocalDate.now();
            int month = currentDate.getMonthValue();
            exam.setExamType(month == 11 ? ExamType.DS : month == 1 ? ExamType.EXAM : ExamType.DS);
        }

        return examDao.save(exam).getIdExam();
    }



    @Override
    public void updateExam(Long examId, Exam exam) {
                examDao.save(exam);

    }

    @Override
    public List<Exam> getAllExams() {
        return examDao.findAll();
    }

    @Override
    public void deleteExam(Long id) {
        if (getExamById(id) != null) {
            examDao.deleteById(id);
        }

    }

    @Override
    public Exam getExamById(Long id) {
        return examDao.findById(id).get();
    }

    @Override
    public Exam addDocumentsAfterEXam(Long examId, String report, String  pvFile, String duree) {
        Optional<Exam> optionalExam = examDao.findById(examId);
        if (!optionalExam.isPresent()) {
            throw new RuntimeException("Exam not found with ID: " + examId);
        }

        Exam exam = optionalExam.get();
        exam.setRapport(report);
        exam.setPv(pvFile);
        exam.setReelDuration(duree);
//        exam.setPreuve(preuve);



//        if (report != null && !report.isEmpty()) {
//            String reportPath = saveFile(report);
//            exam.setRapport(reportPath);
//        }
//
//        if (pvFile != null && !pvFile.isEmpty()) {
//            String pvFilePath = saveFile(pvFile);
//            exam.setPv(pvFilePath);
//        }
//
//        if (preuve != null && !preuve.isEmpty()) {
//            String examPreuve = saveFile(preuve);
//            exam.setPreuve(examPreuve);
//        }

        return examDao.save(exam);
    }


}
