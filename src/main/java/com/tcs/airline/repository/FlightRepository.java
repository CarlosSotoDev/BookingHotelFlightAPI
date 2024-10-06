package com.tcs.airline.repository;

import com.tcs.airline.model.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flights, Integer> {
    Optional<Flights> findByCityOrigin(String cityOrigin);
    Optional<Flights> findByDestination(String destination);
    //Optional<Flights> findByFlightByDepartureDateAndTime(LocalDateTime departureDateAndData);
    Optional<Flights> findByPrice(BigDecimal price);
}
