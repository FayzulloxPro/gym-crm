package dev.fayzullokh.dtos;

import dev.fayzullokh.enums.ActionType;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class NewTrainingRequest {

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
