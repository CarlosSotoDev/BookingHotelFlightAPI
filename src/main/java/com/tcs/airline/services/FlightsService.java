package com.tcs.airline.services;

import com.tcs.airline.model.Flights;
import com.tcs.airline.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FlightsService {

    @Autowired
    private FlightRepository flightRepository;

    //Methods
    // CreateFlight
    public Flights createFlight(String cityOrigin, String destination, LocalDateTime departureDateAndTime, BigDecimal price) {
        if (cityOrigin == null || cityOrigin == "" || cityOrigin.trim().isEmpty()) {
            throw new IllegalArgumentException("City Origin field cannot be empty");
        }

        if (destination == null || destination == "" || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination field cannot be empty");
        }

        if (departureDateAndTime == null) {
            throw new IllegalArgumentException("DepartureDateAndTime field cannot be empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price cannot be empty");
        }

        Flights flight = new Flights();
        flight.setCityOrigin(cityOrigin);
        flight.setDestination(destination);
        flight.setDepartureDateAndTime(departureDateAndTime);
        flight.setPrice(price);
        return flightRepository.save(flight);

    }

    //DeleteFlight
    // Method to delete a flight by its ID
    //String 'cause only we need a return of message
    public String deleteFlightById(int id) {
        // Optional is used to handle the possibility that the flight might not exist.
        // The findById() method from JpaRepository returns an Optional, which may contain the flight or be empty.
        Optional<Flights> flight = flightRepository.findById(id);

        // We check if the flight is present inside the Optional.
        if (flight.isPresent()) {
            // If the flight is present, we proceed to delete it using deleteById().
            flightRepository.deleteById(id);
            return "Flight with ID " + id + " successfully deleted.";
        } else {
            // If the flight is not present, we return a message indicating that the flight was not found.
            return "Flight with ID " + id + " not found.";
        }
    }

    //Update Flight
    public String updateFlight(int id, String cityOrigin, String destination, LocalDateTime departureDateAndTime, BigDecimal price) {
        Optional<Flights> flight = flightRepository.findById(id);
        if (flight.isPresent()) {
            if (cityOrigin == null || cityOrigin == "" || cityOrigin.trim().isEmpty()) {
                throw new IllegalArgumentException("City Origin field cannot be empty");
            }

            if (destination == null || destination == "" || destination.trim().isEmpty()) {
                throw new IllegalArgumentException("Destination field cannot be empty");
            }

            if (departureDateAndTime == null) {
                throw new IllegalArgumentException("DepartureDateAndTime field cannot be empty");
            }

            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("price cannot be empty");
            }
            Flights existingFlight = flight.get();
            existingFlight.setCityOrigin(cityOrigin);
            existingFlight.setDestination(destination);
            existingFlight.setDepartureDateAndTime(departureDateAndTime);
            existingFlight.setPrice(price);
            flightRepository.save(existingFlight);
            return "Flight with ID " + id + " successfully updated.";
        } else {
            return "Flight with ID " + id + " not found.";
        }


    }

    //Look for flight by id
    public Flights getFlightById(int id) {
        Optional<Flights> flight = flightRepository.findById(id);
        if (flight.isPresent()) {
            return flight.get();
        } else {
            throw new RuntimeException("Flight with ID " + id + " not found.");
        }

    }


}
