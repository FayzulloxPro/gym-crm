package dev.fayzullokh.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainee_id") // Adjust the column name based on your database schema
    private Trainee trainee;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "trainer_id") // Adjust the column name based on your database schema
    private Trainer trainer;

    /*@ManyToOne
    @JoinColumn(name = "training_type_id") // Adjust the column name based on your database schema
    private Specialization specialization;*/
    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "duration")
    private int duration;
}
