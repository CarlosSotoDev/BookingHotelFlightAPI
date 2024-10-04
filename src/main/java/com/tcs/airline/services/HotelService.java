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
    public Hotel registeHotel(String hotelName, String city, LocalDate checkinDate, BigDecimal pricePerNight){
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);
        hotel.setCity(city);
        hotel.setCheckinDate(checkinDate);
        hotel.setPricePerNight(pricePerNight);
        return hotelRepository.save(hotel);
    }

    //DeleteHotelById
    public String deleteHotelById(int id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isPresent()){
            hotelRepository.deleteById(id);
            return "Hotel with id" + id + " was deleted";
        }else{
            return "Hotel with id" + id + " not found";
        }
    }

    //Update hotel by Id
    public String updateHotel(int id, String hotelName, String city, LocalDate checkinDate, BigDecimal pricePerNight){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isPresent()){
            Hotel existingHotel = hotel.get();
            existingHotel.setHotelName(hotelName);
            existingHotel.setCity(city);
            existingHotel.setCheckinDate(checkinDate);
            existingHotel.setPricePerNight(pricePerNight);
            hotelRepository.save(existingHotel);
            return "Hotel with id" + id + " was updated";
        }else{
            return "Hotel with id" + id + " not found";
        }
    }

    //Finding Hotel By Id
    public Hotel getHotelById(int id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isPresent()){
            return hotel.get();
        }else{
            throw new RuntimeException("Hotel with id" + id + " not found");
        }
    }

}
