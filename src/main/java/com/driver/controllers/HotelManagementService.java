package com.driver.controllers;


import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.controllers.HotelManagementRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelManagementService {

    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel)
    {
          return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user)
    {
        return hotelManagementRepository.addUser(user);
    }

    public String hotelWithMostFacilities()
    {
        List<Hotel> hotelList = hotelManagementRepository.getAllHotels();
        List<String> hotelNames = new ArrayList<>(); //this will store the hotel name
        int max = 1;

        if(hotelList.isEmpty()) return "";

        for(Hotel hotel : hotelList)
        {
            if(hotel.getFacilities().size() >= max)
                max = hotel.getFacilities().size();
        }

        for(Hotel hotel : hotelList)
        {
            if(hotel.getFacilities().size()==max)
                hotelNames.add(hotel.getHotelName());
        }

        if(hotelNames.size()==0) return "";

        if(hotelNames.size()==1) return hotelNames.get(0);

        for(int i = 0; i < hotelNames.size()-1; i++)
        {
            for(int j = i+1; j < hotelNames.size(); j++)
            {
                if(hotelNames.get(i).compareTo(hotelNames.get(j)) > 0)
                {
                    String temp = hotelNames.get(i);
                    hotelNames.add(i, hotelNames.get(j));
                    hotelNames.add(j, temp);
                }
            }
        }

        return hotelNames.get(0);
    }

    public int bookARoom(Booking booking)
    {
       return hotelManagementRepository.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard)
    {
        return hotelManagementRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName)
    {
        return hotelManagementRepository.updateFacilities(newFacilities, hotelName);
    }
}
