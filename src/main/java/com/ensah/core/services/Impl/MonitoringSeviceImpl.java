package com.ensah.core.services.Impl;

import com.ensah.core.bo.*;
import com.ensah.core.dao.IExamDao;
import com.ensah.core.dao.IMonitoringDao;
import com.ensah.core.dao.IPersonDao;
import com.ensah.core.services.IMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void addMonitoring(Monitoring monitoring, int numberOfProfessors, int maxSeuil) {}
//        Exam exam = monitoring.getExam();
//        if (exam != null) {
//            Optional<Exam> optionalExam = examDao.findById(exam.getIdExam());
//            if (optionalExam.isPresent()) {
//                exam = optionalExam.get();
//                Educationalelement element = exam.getElement();
//                Professor coordinator = element.getCoordinator();
//                monitoring.setCoordinator(coordinator);
//
//                String startTime = exam.getStartTime();
//                String dateExam = monitoring.getDateExam();
//
//                // Find available professors
//                List<Professor> availableProfessors = monitoringDao.findAvailableProfessors(dateExam, startTime);
//
//                // Check if enough professors are available
//                if (availableProfessors.size() < numberOfProfessors) {
//                    throw new RuntimeException("Not enough available professors for the given time");
//                }
//
//                Set<Professor> selectedProfessors = new HashSet<>();
//                Random random = new Random();
//                for (int i = 0; i < numberOfProfessors; i++) {
//                    // Filter professors based on maxSeuil
//                    List<Professor> eligibleProfessors = availableProfessors.stream()
//                            .filter(professor -> getMonitoringCountForProfessorOnDate(professor, dateExam) < maxSeuil)
//                            .collect(Collectors.toList());
//
//                    if (eligibleProfessors.isEmpty()) {
//                        throw new RuntimeException("No available professors within the maxSeuil limit for the given time");
//                    }
//
//                    Professor selectedProfessor = eligibleProfessors.get(random.nextInt(eligibleProfessors.size()));
//                    selectedProfessors.add(selectedProfessor);
//                    availableProfessors.remove(selectedProfessor); // Ensure each professor is assigned only once
//                }
//
//                // Assign selected professors to the monitoring
//                monitoring.setProfessors(selectedProfessors);
//
//                monitoringDao.save(monitoring);
//            } else {
//                throw new RuntimeException("Exam not found with ID: " + exam.getIdExam());
//            }
//        } else {
//            throw new IllegalArgumentException("Exam cannot be null in the monitoring entity");
//        }
//    }
//
//    // Method to get the count of monitorings assigned to a professor on a specific date
//    private int getMonitoringCountForProfessorOnDate(Professor professor, String date) {
//        return monitoringDao.countByProfessorsAndDateExam(professor, date);
//    }



//    @Override
//    public void addMonitoring(Monitoring monitoring) {
//        Exam exam = monitoring.getExam();
//        if (exam != null) {
//            Optional<Exam> optionalExam = examDao.findById(exam.getIdExam());
//            if (optionalExam.isPresent()) {
//                exam = optionalExam.get();
//                Educationalelement element = exam.getElement();
//                Professor coordinator = element.getCoordinator();
//                monitoring.setCoordinator(coordinator);
//                String startTime = exam.getStartTime();
//                String dateExam = monitoring.getDateExam();
//                // Fetch available administrators excluding those already assigned to a monitoring in the same time
//                List<Administrator> availableAdministrators = monitoringDao.findAvailableAdministrators(dateExam,startTime);
//
//                if (availableAdministrators.isEmpty()) {
//                    throw new RuntimeException("No available Absence controller for the given time");
//                }
//                Administrator selectedAdministrator = availableAdministrators.get(new Random().nextInt(availableAdministrators.size()));
//                monitoring.setAdministrator(selectedAdministrator);
//
//                monitoringDao.save(monitoring);
//            } else {
//                throw new RuntimeException("Exam not found with ID: " + exam.getIdExam());
//            }
//        } else {
//            throw new IllegalArgumentException("Exam cannot be null in the monitoring entity");
//        }
//    }


    @Transactional
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
        List<Professor> selectedProfessors = findAvailableProfessors(roomId, dateExam, startTime, 2, 2); // Example threshold and number of professors
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



    private List<Professor> findAvailableProfessors(Long roomId, String dateExam, String startTime, int threshold, int numberOfProfessors) {
        // Check if the room already has the required number of professors assigned for the given date and time
        if (monitoringDao.countProfessorsInRoom(roomId, dateExam, startTime) >= numberOfProfessors) {
            throw new RuntimeException("Room already has the required number of professors assigned for the given date and time");
        }

        // Fetch available professors excluding those already assigned to a monitoring in the same time
        List<Professor> availableProfessors = monitoringDao.findAvailableProfessors(dateExam, startTime);
        // Filter professors based on the threshold
        List<Professor> filteredProfessors = availableProfessors.stream()
                .filter(prof -> monitoringDao.countProfessorAssignments(prof.getIdPerson(), dateExam) < threshold)
                .collect(Collectors.toList());

        if (filteredProfessors.size() < numberOfProfessors) {
            throw new RuntimeException("Not enough available professors for the given time and threshold");
        }

        // Randomly select the required number of professors
        Random random = new Random();
        List<Professor> selectedProfessors = random.ints(0, filteredProfessors.size())
                .distinct()
                .limit(numberOfProfessors)
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

//    @Override
//    public List<Monitoring> getMonitoringByDate(String dateExam) {
//        return null;
//    }

    @Override
    public List<Monitoring> getMonitoringByDate(LocalDateTime dateExam) {
        return monitoringDao.findByDateExam(dateExam);
    }

    public void addProfessorsToMonitoring(Long monitoringId, int numberOfProfessors) {
        Monitoring monitoring = getMonitoringById(monitoringId);
        Set<Professor> professorsAlreadyAssigned = getProfessorsAssignedToMonitoring();
        List<Professor> availableProfessors = getAvailableProfessors(professorsAlreadyAssigned);

        if (numberOfProfessors > availableProfessors.size()) {
            throw new IllegalArgumentException("Not enough available professors for the requested number");
        }

        Set<Professor> professorsToAdd = new HashSet<>();

        Random random = new Random();
        while (professorsToAdd.size() < numberOfProfessors) {
            int randomIndex = random.nextInt(availableProfessors.size());
            Professor professor = availableProfessors.get(randomIndex);
            professorsToAdd.add(professor);
        }
        monitoring.getProfessors().addAll(professorsToAdd);
        monitoringDao.save(monitoring);
    }
    private Set<Professor> getProfessorsAssignedToMonitoring() {
        Set<Professor> professorsAssigned = new HashSet<>();
        List<Monitoring> allMonitorings = monitoringDao.findAll();
        for (Monitoring monitoring : allMonitorings) {
            professorsAssigned.addAll(monitoring.getProfessors());
        }
        return professorsAssigned;
    }
    private List<Professor> getAvailableProfessors(Set<Professor> professorsAssigned) {
        List<Person> allPersons = personDao.findAllByType("Professor");

        // Cast the list of persons to a list of professors directly
        List<Professor> allProfessors = allPersons.stream()
                .map(person -> (Professor) person)
                .collect(Collectors.toList());

        allProfessors.removeAll(professorsAssigned);

        return allProfessors;
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