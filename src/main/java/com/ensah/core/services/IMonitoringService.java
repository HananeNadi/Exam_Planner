package com.ensah.core.services;
import com.ensah.core.bo.Monitoring;
import java.time.LocalDateTime;
import java.util.List;


public interface IMonitoringService {
    public void addMonitoring(Monitoring monitoring, int nbrMonitors);

    public void updateMonitoring(Long idMonitor,Monitoring monitoring);

    public List<Monitoring> getAllMonitoring();

    public void deleteMonitoring(Long id);
    public Monitoring getMonitoringById(Long id);

    List<Monitoring> getMonitoringByDate(String dateExam);

//    public void addProfessorsToMonitoring(Long monitoringId, int numberOfProfessors);

}
