package com.ensah.core.services.Impl;

import com.ensah.core.bo.Room;
import com.ensah.core.dao.IRoomDao;
import com.ensah.core.services.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements IRoomService {

    @Autowired
    IRoomDao RoomDao;

    public void addRoom(Room pRoom) {
        RoomDao.save(pRoom);

    }

    public void updateRoom(Long roomId,Room pRoom) {
        RoomDao.save(pRoom);

    }

    public List<Room> getAllRooms() {
        return RoomDao.findAll();

    }

    public void deleteRoom(Long id) {
        if (getRoomById(id) != null){
            RoomDao.deleteById(id);
        }

    }

    public Room getRoomById(Long id) {
        return RoomDao.findById(id).get();

    }

    public Room getPersonByNameRoom(String NameRoom) {
        return RoomDao.getPersonByNameRoom(NameRoom);
    }
}
