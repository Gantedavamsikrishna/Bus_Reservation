package com.example.demo.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Bus;
import com.example.demo.repository.BusRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BusService {

    private final BusRepository repo;

    public BusService(BusRepository repo) {
        this.repo = repo;
    }

    public Bus create(Bus bus) {
        if (bus.getAvailableSeats() == 0) {
            bus.setAvailableSeats(bus.getTotalSeats());
        }
        return repo.save(bus);
    }

    public List<Bus> list() {
        return repo.findAll();
    }

    public Bus get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Bus not found: " + id));
    }

    public List<Bus> search(String from, String to, LocalDate date) {
        return repo.findByFromCityIgnoreCaseAndToCityIgnoreCaseAndTravelDate(from, to, date);
    }

    public Bus adjustSeats(Long id, int delta) {
        Bus b = get(id);
        int newAvail = b.getAvailableSeats() + delta;
        if (newAvail < 0 || newAvail > b.getTotalSeats()) {
            throw new IllegalStateException("Invalid seat update");
        }
        b.setAvailableSeats(newAvail);
        return repo.save(b);
    }
}
