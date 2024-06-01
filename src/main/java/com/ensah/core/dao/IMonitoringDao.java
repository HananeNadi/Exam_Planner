package com.ensah.core.dao;

import com.ensah.core.bo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface IMonitoringDao extends JpaRepository<Monitoring, Long> {


    @Query("SELECT m FROM Monitoring m WHERE m.exam.startTime = :dateExam")
    List<Monitoring> findByDateExam(@Param("dateExam") LocalDateTime dateExam);

//     available administrator for the control
//    @Query("SELECT admin FROM Administrator admin " +
//            "WHERE admin NOT IN " +
//            "(SELECT m.administrator FROM Monitoring m WHERE " +
//            "m.dateExam = :dateExam AND m.exam.startTime = :startTime)")
//    List<Administrator> findAvailableAdministrators(@Param("dateExam") String dateExam, @Param("startTime") String startTime);


    // available professors for the monitoring
//    @Query("SELECT prof FROM Professor prof " +
//            "WHERE prof NOT IN " +
//            "(SELECT m.professors FROM Monitoring m WHERE " +
//            "m.dateExam = :dateExam AND m.exam.startTime = :startTime)")
//    List<Professor> findAvailableProfessors(@Param("dateExam") String dateExam, @Param("startTime") String startTime);
//
//    int countByProfessorsAndDateExam(Professor professor, String dateExam);


    @Query("SELECT a FROM Administrator a WHERE a.idPerson NOT IN " +
            "(SELECT m.administrator.idPerson FROM Monitoring m WHERE m.dateExam = :dateExam AND m.exam.startTime = :startTime)")
    List<Administrator> findAvailableAdministrators(@Param("dateExam") String dateExam, @Param("startTime") String startTime);

    @Query("SELECT COUNT(m) FROM Monitoring m WHERE m.administrator.idPerson = :adminId AND m.dateExam = :dateExam")
    int countAdminAssignments(@Param("adminId") Long adminId, @Param("dateExam") String dateExam);

    @Query("SELECT COUNT(m) FROM Monitoring m WHERE m.room.idRoom = :roomId AND m.dateExam = :dateExam AND m.exam.startTime = :startTime")
    int countAdminsInRoom(@Param("roomId") Long roomId, @Param("dateExam") String dateExam, @Param("startTime") String startTime);

    @Query("SELECT p FROM Professor p WHERE p.idPerson NOT IN " +
            "(SELECT p.idPerson FROM Monitoring m JOIN m.professors p WHERE m.dateExam = :dateExam AND m.exam.startTime = :startTime)")
    List<Professor> findAvailableProfessors(@Param("dateExam") String dateExam, @Param("startTime") String startTime);
    @Query("SELECT COUNT(m) FROM Monitoring m JOIN m.professors p WHERE p.idPerson = :professorId AND m.dateExam = :dateExam")
    int countProfessorAssignments(@Param("professorId") Long professorId, @Param("dateExam") String dateExam);

    @Query("SELECT COUNT(m) FROM Monitoring m JOIN m.professors p WHERE m.room.idRoom = :roomId AND m.dateExam = :dateExam AND m.exam.startTime = :startTime")
    int countProfessorsInRoom(@Param("roomId") Long roomId, @Param("dateExam") String dateExam, @Param("startTime") String startTime);
}



