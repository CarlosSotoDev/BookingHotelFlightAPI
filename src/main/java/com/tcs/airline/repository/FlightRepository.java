package com.tcs.airline.repository;

import com.tcs.airline.model.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
//You Can use JpaSpecificationExecutor to specify an class with specification to advanced searches on this case we use CriteriaBuilder
//public interface FlightRepository extends JpaRepository<Flights, Integer> , JpaSpecificationExecutor<Flights> {
public interface FlightRepository extends JpaRepository<Flights, Integer>  {
    Optional<Flights> findByCityOrigin(String cityOrigin);
    Optional<Flights> findByDestination(String destination);
    Optional<Flights> findByDepartureDate(LocalDate departureDate);
    Optional<Flights> findByDepartureTime(LocalTime departureTime);
    Optional<Flights> findByPrice(BigDecimal price);

}
