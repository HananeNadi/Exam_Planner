package com.ensah.core.services.Impl;

import com.ensah.core.bo.*;
import com.ensah.core.dao.IExamDao;
import com.ensah.core.dao.IMonitoringDao;
import com.ensah.core.dao.IPersonDao;
import com.ensah.core.services.IMonitoringService;
import com.ensah.core.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void addMonitoring(Monitoring monitoring,int nbrMonitors) {
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
        
        Long roomId = monitoring.getRoom().getIdRoom();
        Administrator selectedAdministrator = findAvailableAdministrator(roomId, dateExam, startTime, 2);
        monitoring.setAdministrator(selectedAdministrator);

        List<Professor> selectedProfessors = findAvailableProfessors(roomId, dateExam, startTime, 2,nbrMonitors);
        Set<Professor> professorSet = new HashSet<>(selectedProfessors);
        monitoring.setProfessors(professorSet);

        monitoringDao.save(monitoring);
    }


    private Administrator findAvailableAdministrator(Long roomId, String dateExam, String startTime, int threshold) {
        // Check if the room already has an admin assigned for the given date and time
        if (monitoringDao.countAdminsInRoom(roomId, dateExam, startTime) > 0) {
            throw new RuntimeException("Room already has an administrator assigned for the given date and time");
        }

        List<Administrator> availableAdministrators = monitoringDao.findAvailableAdministrators(dateExam, startTime);

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
            throw new RuntimeException("Room already has the required number of professors ");
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
    public void updateMonitoring(Long idMonitor, Monitoring pMonitoring) {
        Optional<Monitoring> monitoringOptional = monitoringDao.findById(idMonitor);
        if (monitoringOptional.isEmpty()) {
            throw new ResourceNotFoundException("Monitoring", "id", idMonitor);
        }
        Monitoring monitoring = monitoringOptional.get();

        // Update monitoring properties
        if (pMonitoring.getDateExam() != null) {
            monitoring.setDateExam(pMonitoring.getDateExam());
        }
        if (pMonitoring.getProfessors() != null) {
            monitoring.setProfessors(pMonitoring.getProfessors());
        }
        if (pMonitoring.getCoordinator() != null) {
            monitoring.setCoordinator(pMonitoring.getCoordinator());
        }
        if (pMonitoring.getAdministrator() != null) {
            monitoring.setAdministrator(pMonitoring.getAdministrator());
        }
        if (pMonitoring.getRoom() != null) {
            monitoring.setRoom(pMonitoring.getRoom());
        }
        if (pMonitoring.getExam() != null) {
            monitoring.setExam(pMonitoring.getExam());
        }

        // Save the updated monitoring
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
    public List<Monitoring> getMonitoringByDate(String dateExam) {
        return monitoringDao.findByDateExam(dateExam);
    }

}
