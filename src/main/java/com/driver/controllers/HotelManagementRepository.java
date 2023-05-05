package com.driver.controllers;


import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Repository
public class HotelManagementRepository {

    HashMap<String, Hotel> hotelDB = new HashMap<>();
    HashMap<Integer, User> userMap = new HashMap<>();

    HashMap<String, Booking> bookingDB = new HashMap<>();

    HashMap<Integer, Integer> userbookingDB = new HashMap<>();

    public String addHotel(Hotel hotel)
    {
        if(hotel==null || hotel.getHotelName()==null)
            return "FAILURE";

        if(hotelDB.containsKey(hotel.getHotelName())) //checking if hotel already exist in database
            return "HOTEL ALREADY ADDED";

        hotelDB.put(hotel.getHotelName(), hotel);
        return "HOTEL ADDED SUCCESSFULLY";
    }

    public Integer addUser(User user)
    {
        if(userMap.containsKey(user.getaadharCardNo())) //checking if user already exist in database
            return 0;

        userMap.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public List<Hotel> getAllHotels()
    {
        return hotelDB.values().stream().toList();
    }

    public int bookARoom(Booking booking)
    {
        Hotel hotel = hotelDB.get(booking.getHotelName());

        if(hotel.getAvailableRooms() < booking.getNoOfRooms()) return -1;

        String bookingID = UUID.randomUUID().toString();

        int amount = booking.getNoOfRooms() * hotel.getPricePerNight();

        booking.setAmountToBePaid(amount);

        booking.setBookingId(bookingID);

        bookingDB.put(bookingID, booking);

        userbookingDB.put(booking.getBookingAadharCard(), userbookingDB.getOrDefault(booking.getBookingAadharCard(), 0) + 1);

        return amount;
    }

    public int getBookings(Integer aadharCard)
    {
        int bookings = 0;
        for(String bookingId : bookingDB.keySet()){
            Booking booking = bookingDB.get(bookingId);
            if(booking.getBookingAadharCard() == aadharCard){
                bookings++;
            }
        }
        return bookings;

    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName)
    {
        Hotel hotel = hotelDB.get(hotelName);

        List<Facility> existingfacilities = hotel.getFacilities();

        for(Facility facility: newFacilities)
        {
          if(existingfacilities.contains(facility))
          {
              continue;
          }

          else
          {
              existingfacilities.add(facility);
          }
        }
        hotel.setFacilities(existingfacilities);
        hotelDB.put(hotelName, hotel);
        return hotel;
    }
}
