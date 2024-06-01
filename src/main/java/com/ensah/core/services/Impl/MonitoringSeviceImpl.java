package com.ensah.core.services.Impl;

import com.ensah.core.bo.*;
import com.ensah.core.dao.IExamDao;
import com.ensah.core.dao.IMonitoringDao;
import com.ensah.core.dao.IPersonDao;
import com.ensah.core.services.IMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MonitoringSeviceImpl implements IMonitoringService {
    @Autowired
    IMonitoringDao monitoringDao;

    @Autowired
    IPersonDao personDao;

    @Autowired
    IExamDao examDao;


    //    @Transactional
    public void addMonitoring(Monitoring monitoring) {
        Exam exam = monitoring.getExam();
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null in the monitoring entity");
        }
        Optional<Exam> optionalExam = examDao.findById(exam.getIdExam());
        if (!optionalExam.isPresent()) {
            throw new RuntimeException("Exam not found with ID: " + exam.getIdExam());
        }
        exam = optionalExam.get();
        Educationalelement element = exam.getElement();
        Professor coordinator = element.getCoordinator();
        monitoring.setCoordinator(coordinator);
        String startTime = exam.getStartTime();
        String dateExam = monitoring.getDateExam();
        Long roomId = monitoring.getRoom().getIdRoom(); // Assuming the room ID is available through the exam object
        Administrator selectedAdministrator = findAvailableAdministrator(roomId, dateExam, startTime, 2);
        List<Professor> selectedProfessors = findAvailableProfessors(roomId, dateExam, startTime, 2, 2);
        Set<Professor> professorSet = new HashSet<>(selectedProfessors);

        monitoring.setProfessors(professorSet);
        monitoring.setAdministrator(selectedAdministrator);
        monitoringDao.save(monitoring);
    }

    private Administrator findAvailableAdministrator(Long roomId, String dateExam, String startTime, int threshold) {
        // Check if the room already has an admin assigned for the given date and time
        if (monitoringDao.countAdminsInRoom(roomId, dateExam, startTime) > 0) {
            throw new RuntimeException("Room already has an administrator assigned for the given date and time");
        }
        // Fetch available administrators excluding those already assigned to a monitoring in the same time
        List<Administrator> availableAdministrators = monitoringDao.findAvailableAdministrators(dateExam, startTime);
        // Filter administrators based on the threshold
        List<Administrator> filteredAdministrators = availableAdministrators.stream()
                .filter(admin -> monitoringDao.countAdminAssignments(admin.getIdPerson(), dateExam) < threshold)
                .collect(Collectors.toList());
        if (filteredAdministrators.isEmpty()) {
            throw new RuntimeException("No available administrator for the given time and threshold");
        }
        return filteredAdministrators.get(new Random().nextInt(filteredAdministrators.size()));
    }



    private List<Professor> findAvailableProfessors(Long roomId, String dateExam, String startTime, int threshold, int nbrMonitors) {
        // Check if the room already has the required number of professors assigned for the given date and time
        if (monitoringDao.countProfessorsInRoom(roomId, dateExam, startTime) >= nbrMonitors) {
            throw new RuntimeException("Room already has the required number of professors assigned for the given date and time");
        }
        // Fetch available professors excluding those already assigned to a monitoring in the same time
        List<Professor> availableProfessors = monitoringDao.findAvailableProfessors(dateExam, startTime);
        List<Professor> filteredProfessors = availableProfessors.stream()
                .filter(prof -> monitoringDao.countProfessorAssignments(prof.getIdPerson(), dateExam) < threshold)
                .collect(Collectors.toList());

        if (filteredProfessors.size() < nbrMonitors) {
            throw new RuntimeException("Not enough available professors for the given time and threshold");
        }
        Random random = new Random();
        List<Professor> selectedProfessors = random.ints(0, filteredProfessors.size())
                .distinct()
                .limit(nbrMonitors)
                .mapToObj(filteredProfessors::get)
                .collect(Collectors.toList());

        return selectedProfessors;
    }



    @Override
    public void updateMonitoring(Long idMonitor, Monitoring monitoring) {
        monitoringDao.save(monitoring);
    }

    @Override
    public List<Monitoring> getAllMonitoring() {
        return monitoringDao.findAll();
    }

    @Override
    public void deleteMonitoring(Long id) {
        if (getMonitoringById(id) != null) {
            monitoringDao.deleteById(id);
        }
    }

    @Override
    public Monitoring getMonitoringById(Long id) {
        return monitoringDao.findById(id).get();
    }

    @Override
    public List<Monitoring> getMonitoringByDate(LocalDateTime dateExam) {
        return monitoringDao.findByDateExam(dateExam);
    }

}













//    @Override
//    public void addMonitoringExam(Exam exam, Monitoring monitoring) {
//
//        if (exam.getDuration() == null || exam.getDuration().isEmpty()) {
//            exam.setDuration("2h");
//        }
//        if (exam.getSemester() == null) {
//            LocalDate currentDate = LocalDate.now();
//            int month = currentDate.getMonthValue();
//            exam.setSemester(month >= 9 || month <= 1 ? Semester.AUTOMNE : Semester.PRINTEMPS);
//        }
//
//        if (exam.getSession() == null) {
//            exam.setSession(Session.NORMAL);
//        }
//
//        if (exam.getExamType() == null) {
//            LocalDate currentDate = LocalDate.now();
//            int month = currentDate.getMonthValue();
//            exam.setExamType(month == 11 ? ExamType.DS : month == 1 ? ExamType.EXAM : ExamType.DS);
//        }
//
//        exam = examDao.save(exam);
//
//        Educationalelement element = exam.getElement();
//        Professor coordinator = element.getCoordinator();
//        monitoring.setCoordinator(coordinator);
//        monitoring.setExam(exam);
//
//        monitoringDao.save(monitoring);
//    }