package com.ensah.core.services.Impl;

import com.ensah.core.bo.Educationalelement;
import com.ensah.core.bo.Exam;
import com.ensah.core.bo.Professor;
import com.ensah.core.bo.Session;
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
        if (exam.getDuration() == null){
            exam.setDuration("2h");
        }
        if (exam.getSession() == null) {
            exam.setSession(Session.NORMAL);
        }



        examDao.save(exam);

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






    //TO _ DO
//    public void assignRoomsToExam(Long examId, List<RoomAssignment> roomAssignments) {
//        Exam exam = examDao.findById(examId).orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
//
//    }


}
