package dev.fayzullokh.repositories;

import dev.fayzullokh.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query("SELECT t FROM Training t " +
           "JOIN t.trainee tr " +
           "LEFT JOIN t.trainer tn " +
           "WHERE tr.id = :traineeId " +
           "AND (:periodFrom IS NULL OR t.date >= :periodFrom) " +
           "AND (:periodTo IS NULL OR t.date <= :periodTo) " +
           "AND (:trainerName IS NULL OR tn.user.username = :trainerName) " +
           "AND (:trainingTypeId IS NULL OR t.trainingType.id = :trainingTypeId)")
    List<Training> findTrainingsForTrainee(@Param("traineeId") Long traineeId,
                                           @Param("periodFrom") LocalDate periodFrom,
                                           @Param("periodTo") LocalDate periodTo,
                                           @Param("trainerName") String trainerName,
                                           @Param("trainingTypeId") Long trainingTypeId);
    @Query("SELECT t FROM Training t " +
            "JOIN t.trainer tn " +
            "LEFT JOIN t.trainee tr " +
            "WHERE tn.id = :trainerId " +
            "AND (:periodFrom IS NULL OR t.date >= :periodFrom) " +
            "AND (:periodTo IS NULL OR t.date <= :periodTo) " +
            "AND (:traineeName IS NULL OR tr.user.username = :traineeName)")
    List<Training> findTrainingsForTrainer(@Param("trainerId") Long trainerId,
                                           @Param("periodFrom") LocalDate periodFrom,
                                           @Param("periodTo") LocalDate periodTo,
                                           @Param("traineeName") String traineeName);
}
