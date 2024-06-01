package com.ensah.core.services;
import com.ensah.core.bo.Monitoring;
import java.time.LocalDateTime;
import java.util.List;


public interface IMonitoringService {
    public void addMonitoring(Monitoring monitoring);

    public void updateMonitoring(Long idMonitor,Monitoring monitoring);

    public List<Monitoring> getAllMonitoring();

    public void deleteMonitoring(Long id);
    public Monitoring getMonitoringById(Long id);

    List<Monitoring> getMonitoringByDate(LocalDateTime dateExam);

//    public void addProfessorsToMonitoring(Long monitoringId, int numberOfProfessors);

}
