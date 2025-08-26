package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.modal.Reservation;
import com.example.demo.repository.ReservationRepository;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/test")
    public String testAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return "Hello " + auth.getName() + "! You are authenticated.";
        }
        return "You are not authenticated.";
    }

    @PostMapping
    public Reservation bookSeat(@RequestBody Reservation reservation) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User " + auth.getName() + " is booking a seat");
        
        reservation.setStatus("BOOKED");
        return reservationRepository.save(reservation);
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getUserReservations(@PathVariable Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User " + auth.getName() + " is fetching reservations for user " + userId);
        
        return reservationRepository.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public String cancelReservation(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User " + auth.getName() + " is cancelling reservation " + id);
        
        Reservation res = reservationRepository.findById(id).orElseThrow();
        res.setStatus("CANCELLED");
        reservationRepository.save(res);
        return "Reservation cancelled successfully";
    }
}
