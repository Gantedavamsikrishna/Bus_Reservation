package com.example.demo.controller;


import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Bus;
import com.example.demo.service.BusService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/buses")
public class BusController {

    private final BusService service;

    public BusController(BusService service) {
        this.service = service;
    }

    // Create a bus
    @PostMapping
    public Bus create(@Valid @RequestBody Bus bus) {
        return service.create(bus);
    }

    // List all buses
    @GetMapping
    public List<Bus> list() {
        return service.list();
    }

    // Get by id
    @GetMapping("/{id}")
    public Bus get(@PathVariable Long id) {
        return service.get(id);
    }

    // Search by from/to/date
    @GetMapping("/search")
    public List<Bus> search(@RequestParam String from,
                            @RequestParam String to,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate) {
        return service.search(from, to, travelDate);
    }

    // Adjust seats (delta can be -1 for booking, +1 for cancel)
    @PatchMapping("/{id}/seats")
    public Bus adjustSeats(@PathVariable Long id, @RequestParam int delta) {
        return service.adjustSeats(id, delta);
    }
}
