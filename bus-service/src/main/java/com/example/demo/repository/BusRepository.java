package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Bus;

import java.time.LocalDate;
import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findByFromCityIgnoreCaseAndToCityIgnoreCaseAndTravelDate(String fromCity, String toCity, LocalDate travelDate);
}

