package com.ensah.core.services.Impl;

import com.ensah.core.bo.Exam;
import com.ensah.core.bo.ExamType;
import com.ensah.core.bo.Semester;
import com.ensah.core.bo.Session;
import com.ensah.core.dao.IExamDao;
import com.ensah.core.services.IExamService;
import com.ensah.core.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
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
            exam.setExamType(month == 11  || month == 4 ? ExamType.DS : ExamType.EXAM);
        }

        return examDao.save(exam).getIdExam();
    }



    @Override
    public void updateExam(Long examId, Exam pExam) {
        Optional<Exam> examOptional = examDao.findById(examId);
        if (examOptional.isEmpty()) {
            throw new ResourceNotFoundException("Exam", "id", examId);
        }
        Exam exam = examOptional.get();

        if (pExam.getStartTime() != null) {
            exam.setStartTime(pExam.getStartTime());
        }

        if (pExam.getEndTime() != null) {
            exam.setEndTime(pExam.getEndTime());
        }

        if (pExam.getDuration() != null) {
            exam.setDuration(pExam.getDuration());
        }

        if (pExam.getReelDuration() != null) {
            exam.setReelDuration(pExam.getReelDuration());
        }

        if (pExam.getPreuve() != null) {
            exam.setPreuve(pExam.getPreuve());
        }

        if (pExam.getPv() != null) {
            exam.setPv(pExam.getPv());
        }

        if (pExam.getRapport() != null) {
            exam.setRapport(pExam.getRapport());
        }

        if (pExam.getElement() != null) {
            exam.setElement(pExam.getElement());
        }

        if (pExam.getSession() != null) {
            exam.setSession(pExam.getSession());
        }

        if (pExam.getSemester() != null) {
            exam.setSemester(pExam.getSemester());
        }

        if (pExam.getExamType() != null) {
            exam.setExamType(pExam.getExamType());
        }

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
    public Exam addDocumentsAfterEXam(Long examId, String report, MultipartFile pvFile, MultipartFile preuve) throws IOException {
        Optional<Exam> optionalExam = examDao.findById(examId);
        if (!optionalExam.isPresent()) {
            throw new RuntimeException("Exam not found with ID: " + examId);
        }

        Exam exam = optionalExam.get();
        exam.setRapport(report);

        if (pvFile != null && !pvFile.isEmpty()) {
            String pvFilePath = saveFile(pvFile);
            exam.setPv(pvFilePath);
        }

        if (preuve != null && !preuve.isEmpty()) {
            String examPreuve = saveFile(preuve);
            exam.setPreuve(examPreuve);
        }

        return examDao.save(exam);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = "/rapport/" + fileName;
        File dest = new File(filePath);
        file.transferTo(dest);

        return filePath;
    }
}



