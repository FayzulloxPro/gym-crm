package dev.fayzullokh.repositories;

import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByUser_Username(String username);

    @Query("SELECT t FROM Trainer t WHERE t.user.isActive = true AND t NOT IN :trainersAssignedToTrainee")
    List<Trainer> findUnassignedTrainers(@Param("trainersAssignedToTrainee") List<Trainer> trainersAssignedToTrainee);

    List<Trainer> findByUser_UsernameIn(List<String> trainerUsernames);
}
