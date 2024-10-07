package com.tcs.airline.services;

import com.tcs.airline.model.Flights;
import com.tcs.airline.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class FlightsService {

    @Autowired
    private FlightRepository flightRepository;
//    @Autowired
//    private DatabaseInitializationDependencyConfigurer databaseInitializationDependencyConfigurer;

    //Methods
    // CreateFlight
    public Flights createFlight(String cityOrigin, String destination, LocalDate departureDate, LocalTime departureTime, BigDecimal price) {
        if (cityOrigin == null || cityOrigin == "" || cityOrigin.trim().isEmpty()) {
            throw new IllegalArgumentException("City Origin field cannot be empty");
        }

        if (destination == null || destination == "" || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination field cannot be empty");
        }

        if (departureDate == null) {
            throw new IllegalArgumentException("DepartureDate field cannot be empty");
        }

        if (departureTime == null ) {
            throw new IllegalArgumentException("DepartureTime field cannot be empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price cannot be empty");
        }

        Flights flight = new Flights();
        flight.setCityOrigin(cityOrigin);
        flight.setDestination(destination);
        flight.setDepartureDate(departureDate);
        flight.setDepartureTime(departureTime);
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
    public String updateFlight(int id, String cityOrigin, String destination, LocalDate departureDate, LocalTime departureTime, BigDecimal price) {
        Optional<Flights> flight = flightRepository.findById(id);
        if (flight.isPresent()) {
            if (cityOrigin == null || cityOrigin == "" || cityOrigin.trim().isEmpty()) {
                throw new IllegalArgumentException("City Origin field cannot be empty");
            }

            if (destination == null || destination == "" || destination.trim().isEmpty()) {
                throw new IllegalArgumentException("Destination field cannot be empty");
            }

            if (departureDate == null) {
                throw new IllegalArgumentException("DepartureDate field cannot be empty");
            }

            if (departureTime == null ) {
                throw new IllegalArgumentException("DepartureTime field cannot be empty");
            }

            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("price cannot be empty");
            }
            Flights existingFlight = flight.get();
            existingFlight.setCityOrigin(cityOrigin);
            existingFlight.setDestination(destination);
            existingFlight.setDepartureDate(departureDate);
            existingFlight.setDepartureTime(departureTime);
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

    public Flights getFlightByCityOrigin(String cityOrigin) {
        Optional<Flights> flight = flightRepository.findByCityOrigin(cityOrigin);
        if (flight.isPresent()) {
            return flight.get();
        } else {
            throw new RuntimeException("Flight with City Origin " + cityOrigin + " not found.");
        }
    }

    public Flights getFlightByDestination(String destination) {
        Optional<Flights> flight = flightRepository.findByDestination(destination);
        if (flight.isPresent()) {
            return flight.get();
        } else {
            throw new RuntimeException("Flight with City Origin " + destination + " not found.");
        }
    }

    public Flights getFlightByDepartureDate(LocalDate departureDate) {
        Optional<Flights> flight = flightRepository.findByDepartureDate((departureDate));
        if(flight.isPresent()){
            return flight.get();
        }else{
            throw new RuntimeException("Flight with DepartureDate " + departureDate + " not found.");
        }
    }

    public Flights getFlightByPrice(BigDecimal price) {
        Optional<Flights> flight = flightRepository.findByPrice(price);
        if (flight.isPresent()) {
            return flight.get();
        } else {
            throw new RuntimeException("Flight with City Origin " + price + " not found.");
        }
    }


}
