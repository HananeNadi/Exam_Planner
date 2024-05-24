package com.ensah.core.services.Impl;

import com.ensah.core.bo.Exam;
import com.ensah.core.dao.IExamDao;
import com.ensah.core.services.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ExamServiceImpl implements IExamService {

    @Autowired
    IExamDao examDao;

    @Override
    public void addExam(Exam exam) {
        examDao.save(exam);

    }

    @Override
    public void updateExam(Long examId,Exam exam) {
        examDao.save(exam);

    }

    @Override
    public List<Exam> getAllExams() {
        return null;
    }
    @Override
    public void deleteExam(Long id) {

    }

    @Override
    public Exam getExamById(Long id) {

        return examDao.findById(id).get();
    }

//    public Exam getExamByTitle(String title) {
//        return examDao.getExamByTitle(title);}



}
