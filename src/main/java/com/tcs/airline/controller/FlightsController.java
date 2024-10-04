package com.tcs.airline.controller;

import com.tcs.airline.model.Flights;
import com.tcs.airline.services.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
            @RequestParam LocalDateTime departureDateAndTime,
            @RequestParam BigDecimal price) {
        try {
            // Call the service to create the flight
            Flights createdFlight = flightsService.createFlight(cityOrigin, destination, departureDateAndTime, price);
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
            @RequestParam LocalDateTime departureDateAndTime,
            @RequestParam BigDecimal price) {
        try {
            // Call the service to update the flight
            String result = flightsService.updateFlight(id, cityOrigin, destination, departureDateAndTime, price);
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
}
