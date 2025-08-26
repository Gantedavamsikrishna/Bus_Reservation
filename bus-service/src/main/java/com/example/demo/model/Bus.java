package com.example.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "buses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String operatorName;    // e.g., "City Travels"

    @NotBlank
    private String fromCity;        // e.g., "Hyderabad"

    @NotBlank
    private String toCity;          // e.g., "Bengaluru"

    @NotNull
    private LocalDate travelDate;   // date of journey

    @NotBlank
    private String departureTime;   // "08:30"

    @NotBlank
    private String arrivalTime;     // "15:00"

    @Min(1)
    private int totalSeats;

    @Min(0)
    private int availableSeats;

    @DecimalMin(value = "0.0", inclusive = false)
    private double fare;            // per seat
}
