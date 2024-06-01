package com.ensah.core.services;

import com.ensah.core.bo.Exam;
import com.ensah.core.bo.Monitoring;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IMonitoringService {
//    public void addMonitoring(Monitoring monitoring);
        public void addMonitoring(Monitoring monitoring,int numberOfProfessors, int maxSeuil);


    public void updateMonitoring(Long idMonitor,Monitoring monitoring);

    public List<Monitoring> getAllMonitoring();

    public void deleteMonitoring(Long id);
    public Monitoring getMonitoringById(Long id);


//    public List<Monitoring> getMonitoringByDate(String dateExam);

    List<Monitoring> getMonitoringByDate(LocalDateTime dateExam);

    public void addProfessorsToMonitoring(Long monitoringId, int numberOfProfessors);

}
