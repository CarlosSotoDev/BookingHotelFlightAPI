package com.tcs.airline.services;

import com.tcs.airline.model.Flights;
import com.tcs.airline.repository.FlightRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.data.util.Predicates;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightsService {

    @Autowired
    private FlightRepository flightRepository;

    //Allows inyect an EntityManager that allow control the life cycle of a transaction like a search
    @PersistenceContext
    private EntityManager entityManager;


    //Methods
    //SearchFilterMethod
    public List<Flights> searchFlights(String cityOrigin, String destination, LocalDate startDate, LocalDate endDate, LocalTime departureTime, BigDecimal minPrice, BigDecimal maxPrice) {
        // Inicializar CriteriaBuilder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flights> query = cb.createQuery(Flights.class);
        // Query Root
        Root<Flights> flight = query.from(Flights.class);

        // Lista de Predicados para los filtros
        List<Predicate> predicates = new ArrayList<>();

        // Predicados opcionales
        if (cityOrigin != null && !cityOrigin.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(flight.get("cityOrigin")), cityOrigin.toLowerCase() + "%"));
        }
        if (destination != null && !destination.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(flight.get("destination")), destination.toLowerCase() + "%"));
        }

        if (startDate != null && endDate != null) {
            predicates.add(cb.between(flight.get("departureDate"), startDate, endDate));
        } else if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(flight.get("departureDate"), startDate));
        } else if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(flight.get("departureDate"), endDate));
        }

        if (departureTime != null) {
            predicates.add(cb.equal(flight.get("departureTime"), departureTime));
        }

        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(flight.get("price"), minPrice));
        }

        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(flight.get("price"), maxPrice));
        }

        // Construir la consulta
        query.select(flight).where(cb.and(predicates.toArray(new Predicate[0])));

        // Construir y ejecutar la TypedQuery
        TypedQuery<Flights> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }





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

        if (departureTime == null) {
            throw new IllegalArgumentException("DepartureTime field cannot be empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price cannot be empty or negative");
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
        Optional<Flights> flightOptional = flightRepository.findById(id);

        if (flightOptional.isPresent()) {
            if (cityOrigin == null || cityOrigin == "" || cityOrigin.trim().isEmpty()) {
                throw new IllegalArgumentException("City Origin field cannot be empty");
            }

            if (destination == null || destination == "" || destination.trim().isEmpty()) {
                throw new IllegalArgumentException("Destination field cannot be empty");
            }

            if (departureDate == null) {
                throw new IllegalArgumentException("DepartureDate field cannot be empty");
            }

            if (departureTime == null) {
                throw new IllegalArgumentException("DepartureTime field cannot be empty");
            }

            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("price cannot be empty or negative");
            }
            Flights flight = flightOptional.get();
            flight.setCityOrigin(cityOrigin);
            flight.setDestination(destination);
            flight.setDepartureDate(departureDate);
            flight.setDepartureTime(departureTime);
            flight.setPrice(price);

            flightRepository.save(flight);
            return "Flight successfully updated";
        } else {
            return "Flight not found";
        }
    }

    //GetAllData
    public List<Flights> getAllFlights() {
        return flightRepository.findAll();
    }

    //Look for flight by especifica param
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
        if (flight.isPresent()) {
            return flight.get();
        } else {
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
