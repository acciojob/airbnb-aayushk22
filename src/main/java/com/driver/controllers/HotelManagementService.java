package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelManagementService {

//    @Autowired
    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel) {
        return hotelManagementRepository.addHotel(hotel);
    }

    public Hotel getHotel(String hotelName) {
        return hotelManagementRepository.getHotel(hotelName);
    }

    public Integer addUser(User user) {
        hotelManagementRepository.addUser(user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        String response = hotelManagementRepository.getHotelWithMostFacilities();

        return response;
    }

    public int bookARoom(Booking booking) {
        String bookingId = String.valueOf(UUID.randomUUID());
        Booking booking1 = new Booking(bookingId,booking.getBookingAadharCard(),booking.getNoOfRooms(),booking.getBookingPersonName(),booking.getHotelName());
        return hotelManagementRepository.bookARoom(booking1);
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagementRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepository.updateFacilities(newFacilities,hotelName);
    }
}
