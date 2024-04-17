package dev.fayzullokh.dtos;

import dev.fayzullokh.enums.ActionType;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;

public class TrainingCalculatorDTO {
    private Integer id;
    private Long trainerId;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isActive;
    @Transient
    private int duration;
    @Transient
    private LocalDateTime startDate;

    @Transient
    private ActionType actionType;
}
