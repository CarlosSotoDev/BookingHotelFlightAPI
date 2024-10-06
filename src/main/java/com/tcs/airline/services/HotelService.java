package com.tcs.airline.services;

import com.tcs.airline.model.Hotel;
import com.tcs.airline.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;

    //Methods
    //CreateHotel
    public Hotel registeHotel(String hotelName, String city, LocalDate checkinDate, BigDecimal pricePerNight) {
        //Check for null or empty values in the fields
        if (hotelName == null || hotelName == "" || hotelName.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be empty");
        }
        if (city == null || city == "" || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City field cannot be empty");
        }
        if (checkinDate == null) {
            throw new IllegalArgumentException("checkinDate field cannot be empty");
        }
        if (pricePerNight == null || pricePerNight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("PricePerNight field cannot be negative");
        }
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);
        hotel.setCity(city);
        hotel.setCheckinDate(checkinDate);
        hotel.setPricePerNight(pricePerNight);
        return hotelRepository.save(hotel);
    }

    //DeleteHotelById
    public String deleteHotelById(int id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            hotelRepository.deleteById(id);
            return "Hotel with id" + id + " was deleted";
        } else {
            return "Hotel with id" + id + " not found";
        }
    }

    //Update hotel by Id
    public String updateHotel(int id, String hotelName, String city, LocalDate checkinDate, BigDecimal pricePerNight) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            //Check for null or empty values in the fields
            if (hotelName == null || hotelName == "" || hotelName.trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel name cannot be empty");
            }
            if (city == null || city == "" || city.trim().isEmpty()) {
                throw new IllegalArgumentException("City field cannot be empty");
            }
            if (checkinDate == null) {
                throw new IllegalArgumentException("checkinDate field cannot be empty");
            }
            if (pricePerNight == null || pricePerNight.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("PricePerNight field cannot be negative");
            }
            Hotel existingHotel = hotel.get();
            existingHotel.setHotelName(hotelName);
            existingHotel.setCity(city);
            existingHotel.setCheckinDate(checkinDate);
            existingHotel.setPricePerNight(pricePerNight);
            hotelRepository.save(existingHotel);
            return "Hotel with id" + id + " was updated";
        } else {
            return "Hotel with id" + id + " not found";
        }
    }

    //Finding Hotel By Id
    public Hotel getHotelById(int id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            return hotel.get();
        } else {
            throw new RuntimeException("Hotel with id" + id + " not found");
        }
    }

}
