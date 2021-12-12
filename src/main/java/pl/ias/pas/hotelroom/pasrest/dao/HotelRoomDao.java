package pl.ias.pas.hotelroom.pasrest.dao;


import pl.ias.pas.hotelroom.pasrest.exceptions.ApplicationDaoException;
import pl.ias.pas.hotelroom.pasrest.model.HotelRoom;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class HotelRoomDao {

    private List<HotelRoom> roomsRepository = Collections.synchronizedList(new ArrayList<>());

    public UUID addHotelRoom(HotelRoom room) {
        roomsRepository.add(room);
        return room.getId();
    }

    public void updateHotelRoom(HotelRoom oldRoom, HotelRoom room) {
        if(room.getRoomNumber() <= 0) {
            oldRoom.setRoomNumber(room.getRoomNumber());
        }
        if(room.getPrice() <= 0) {
            oldRoom.setPrice(room.getPrice());
        }
        if(room.getCapacity() <= 0) {
            oldRoom.setCapacity(room.getCapacity());
        }
        if(room.getDescription() != null) {
            oldRoom.setDescription(room.getDescription());
        }
    }

    public void removeRoom(HotelRoom room) {
        roomsRepository.remove(room);
    }

    public HotelRoom getRoomById(UUID id) throws ApplicationDaoException {
        for (HotelRoom room : roomsRepository) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        throw new ApplicationDaoException("500", "Room doesn't exist");
    }

    public HotelRoom getRoomByNumber(int number) throws ApplicationDaoException {
        for (HotelRoom room : roomsRepository) {
            if (room.getRoomNumber() == number) {
                return room;
            }
        }
        throw new ApplicationDaoException("500", "Room doesn't exist");
    }

    public List<HotelRoom> getAllRooms() {
        return roomsRepository;
    }
}
