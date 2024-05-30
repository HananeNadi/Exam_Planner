package com.ensah.core.web.controllers;



import com.ensah.core.bo.Departement;
import com.ensah.core.bo.Level;
import com.ensah.core.bo.Room;
import com.ensah.core.bo.Sector;
import com.ensah.core.dao.IDepartementDao;
import com.ensah.core.dao.ILevelDAO;
import com.ensah.core.dao.ISectorDao;
import com.ensah.core.services.IRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/utils")
public class UtilsController {

    @Autowired
    IDepartementDao departementDao;
    @Autowired
    ISectorDao sectorDao;
    @Autowired
    ILevelDAO levelDAO;


    @GetMapping("/departements")
    public ResponseEntity<List<Departement>> getAllDepartements() {
        return ResponseEntity.ok(departementDao.findAll());
    }

    @GetMapping("/levels")
    public ResponseEntity<List<Level>> getAllLevels() {
        return ResponseEntity.ok(levelDAO.findAll());
    }

    @GetMapping("/sectors")
    public ResponseEntity<List<Sector>> getAllSectors() {
        return ResponseEntity.ok(sectorDao.findAll());
    }

//    @GetMapping("/{RoomnelId}")
//    public ResponseEntity<?> getOneRoomRS(@PathVariable Long RoomnelId) {
//        return ResponseEntity.ok(roomService.getRoomById(RoomnelId));
//
//    }


}



