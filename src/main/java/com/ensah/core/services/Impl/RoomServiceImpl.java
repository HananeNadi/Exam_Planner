package com.ensah.core.services.Impl;

import com.ensah.core.bo.Room;
import com.ensah.core.dao.IRoomDao;
import com.ensah.core.services.IRoomService;
import com.ensah.core.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomServiceImpl implements IRoomService {

    @Autowired
    IRoomDao RoomDao;

    public void addRoom(Room pRoom) {
        RoomDao.save(pRoom);

    }

    public void updateRoom(Long roomId, Room pRoom) {
        Optional<Room> roomOptional = RoomDao.findById(roomId);
        if (roomOptional.isEmpty()) {
            throw new ResourceNotFoundException("Room", "id", roomId);
        }
        Room room = roomOptional.get();

        if (pRoom.getNameRoom() != null) {
            room.setNameRoom(pRoom.getNameRoom());
        }

        if (pRoom.getSize() != null) {
            room.setSize(pRoom.getSize());
        }

        if (pRoom.getType() != null) {
            room.setType(pRoom.getType());
        }

        if (pRoom.getPlace() != null) {
            room.setPlace(pRoom.getPlace());
        }

        RoomDao.save(room);
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

    public Room getRoomByNameRoom(String NameRoom) {
        return RoomDao.getRoomByNameRoom(NameRoom);
    }
}
