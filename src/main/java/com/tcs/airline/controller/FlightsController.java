package com.tcs.airline.controller;

import com.tcs.airline.model.Flights;
import com.tcs.airline.services.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightsController {

    @Autowired
    private FlightsService flightsService;

    // Endpoint to create a new flight
    @PostMapping
    public ResponseEntity<Flights> createFlight(
            @RequestParam String cityOrigin,
            @RequestParam String destination,
            @RequestParam LocalDate departureDate,
            @RequestParam LocalTime departureTime,
            @RequestParam BigDecimal price) {
        try {
            // Call the service to create the flight
            Flights createdFlight = flightsService.createFlight(cityOrigin, destination, departureDate, departureTime, price);
            return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
        } catch (Exception e) {
            // Return an error response in case of failure
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to delete a flight by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlightById(@PathVariable int id) {
        try {
            // Call the service to delete the flight
            String result = flightsService.deleteFlightById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            // Return an error response if deletion fails
            return new ResponseEntity<>("Error deleting flight.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to update an existing flight
    @PutMapping("/{id}")
    public ResponseEntity<String> updateFlight(
            @PathVariable int id,
            @RequestParam String cityOrigin,
            @RequestParam String destination,
            @RequestParam LocalDate departureDate,
            @RequestParam LocalTime departureTime,
            @RequestParam BigDecimal price) {
        try {
            // Call the service to update the flight
            String result = flightsService
                    .updateFlight(id, cityOrigin, destination, departureDate,departureTime, price);
            if (result.contains("successfully updated")) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Return an error response in case of failure
            return new ResponseEntity<>("Error updating flight.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to retrieve a flight by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Flights> getFlightById(@PathVariable int id) {
        try {
            // Call the service to retrieve the flight
            Flights flight = flightsService.getFlightById(id);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } catch (Exception e) {
            // Return a not found response if flight does not exist
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to retrieve a flight by its cityOrigin
    @GetMapping("/city/{cityOrigin}")
    public ResponseEntity<Flights> getFlightByCityOrigin(@PathVariable String cityOrigin) {
        try {
            // Call the service to retrieve the flight
            Flights flight = flightsService.getFlightByCityOrigin(cityOrigin);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } catch (Exception e) {
            // Return a not found response if flight does not exist
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to retrieve a flight by its ID
    @GetMapping("/destination/{destination}")
    public ResponseEntity<Flights> getFlightByDestination(@PathVariable String destination) {
        try {
            // Call the service to retrieve the flight
            Flights flight = flightsService.getFlightByDestination(destination);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } catch (Exception e) {
            // Return a not found response if flight does not exist
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to retrieve a flight by its Price
    @GetMapping("/price/{price}")
    public ResponseEntity<Flights> getFlightByPrice (@PathVariable BigDecimal price){
        try {
            // Call the service to retrieve the flight
            Flights flight = flightsService.getFlightByPrice(price);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } catch (Exception e) {
            // Return a not found response if flight does not exist
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
