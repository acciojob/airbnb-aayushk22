package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Repository
public class HotelManagementRepository {

    HashMap<String, Hotel> hotelDb = new HashMap<>();
    HashMap<Integer,User> userDb = new HashMap<>();
    HashMap<Integer,List<Booking>> userBookingsDb = new HashMap<>();

    public String addHotel(Hotel hotel) {
        if (hotel.getHotelName() == null || hotel == null) {
            return "FAILURE";
        }

        if (hotelDb.containsKey(hotel.getHotelName())) {
            return "FAILURE";
        }

        hotelDb.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }


    public Hotel getHotel(String hotelName) {
        if (hotelDb.containsKey(hotelName)) {
            return hotelDb.get(hotelName);
        }

        return null;
    }

    public void addUser(User user) {
        userDb.put(user.getaadharCardNo(), user);
        return;
    }

    public String getHotelWithMostFacilities() {

        int maxFacilities = 0;
        for (String name: hotelDb.keySet()) {
            Hotel hotel = hotelDb.get(name);
            List<Facility> list = hotel.getFacilities();
            maxFacilities = Math.max(maxFacilities, list.size());
        }

        if (maxFacilities == 0) {
            return "";
        }

        List<String> mostFacilityHotels = new ArrayList<>();

        for (String name: hotelDb.keySet()) {
            Hotel hotel = hotelDb.get(name);
            List<Facility> list = hotel.getFacilities();
            if (list.size() == maxFacilities) {
                mostFacilityHotels.add(name);
            }
        }

        Collections.sort(mostFacilityHotels);
        return mostFacilityHotels.get(0);
    }

    public int bookARoom(Booking booking1) {
        String hotelName = booking1.getHotelName();
        Hotel hotel = hotelDb.get(hotelName);

        if (booking1.getNoOfRooms() > hotel.getAvailableRooms()) return -1;

        int amountToBePaid = booking1.getNoOfRooms() * hotel.getPricePerNight();

        booking1.setAmountToBePaid(amountToBePaid);
        int aadharCard = booking1.getBookingAadharCard();

        List<Booking> listOfBookingsByUser = new ArrayList<>();
        if (userBookingsDb.containsKey(aadharCard)) {
            listOfBookingsByUser = userBookingsDb.get(aadharCard);
        }

        listOfBookingsByUser.add(booking1);
        userBookingsDb.put(aadharCard,listOfBookingsByUser);
        return amountToBePaid;
    }

    public int getBookings(Integer aadharCard) {
        if (!userBookingsDb.containsKey(aadharCard)) return 0;

        return userBookingsDb.get(aadharCard).size();
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelDb.get(hotelName);

        List<Facility> currFacilities = hotel.getFacilities();

        for (Facility facility: newFacilities) {
            if (!currFacilities.contains(facility)) {
                currFacilities.add(facility);
            }
        }

        hotel.setFacilities(currFacilities);
        return hotel;
    }
}
