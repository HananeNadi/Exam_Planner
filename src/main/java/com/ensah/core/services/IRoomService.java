package com.ensah.core.services;

import com.ensah.core.bo.Room;

import java.util.List;


public interface IRoomService {

    public void addRoom(Room pRoom);

    public void updateRoom(Long roomId,Room pRoom);

    public List<Room> getAllRooms();

    public void deleteRoom(Long id);

    public Room getRoomById(Long id);

    public Room getPersonByNameRoom(String nameRoom);

}
