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
import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightsController {

    @Autowired
    private FlightsService flightsService;

    // Endpoint to create a new flight
    @PostMapping
    public ResponseEntity<Flights> createFlight(@RequestBody Flights flight) {
        try {
            // Llamar al servicio para crear el vuelo
            Flights createdFlight = flightsService.createFlight(
                    flight.getCityOrigin(),
                    flight.getDestination(),
                    flight.getDepartureDate(),
                    flight.getDepartureTime(),
                    flight.getPrice()
            );
            return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
        } catch (Exception e) {
            // Retornar un error en caso de fallo
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
            @RequestBody Flights flightRequest) {
        try {
            // Call the service to update the flight
            String result = flightsService
                    .updateFlight(id, flightRequest.getCityOrigin(), flightRequest.getDestination(),
                            flightRequest.getDepartureDate(), flightRequest.getDepartureTime(),
                            flightRequest.getPrice());
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

    //Endpoint to shows al registers
    @GetMapping
    public List<Flights> getAllFlights() {
        return flightsService.getAllFlights();
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
    public ResponseEntity<Flights> getFlightByPrice(@PathVariable BigDecimal price) {
        try {
            // Call the service to retrieve the flight
            Flights flight = flightsService.getFlightByPrice(price);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } catch (Exception e) {
            // Return a not found response if flight does not exist
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //SearchForm
    @GetMapping("/search")
    public ResponseEntity<List<Flights>> searchFlights(
            @RequestParam(name = "cityOrigin", required = false) String cityOrigin,
            @RequestParam(name = "destination", required = false) String destination,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "departureTime", required = false) String departureTime,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice) {

        try {
            // Convertir Strings a LocalDate y LocalTime si no son nulos o vacíos
            LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : null;
            LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : null;
            LocalTime time = (departureTime != null && !departureTime.isEmpty()) ? LocalTime.parse(departureTime) : null;

            // Llamar al método del servicio para obtener los vuelos
            List<Flights> flights = flightsService.searchFlights(cityOrigin, destination, start, end, time, minPrice, maxPrice);

            // Devolver la lista de vuelos en un ResponseEntity con estado 200 OK
            return new ResponseEntity<>(flights, HttpStatus.OK);

        } catch (Exception e) {
            // Si ocurre un error, devolver un ResponseEntity con un estado 500 y el mensaje de error
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
