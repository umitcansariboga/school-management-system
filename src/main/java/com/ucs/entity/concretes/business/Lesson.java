package com.ucs.entity.concretes.business;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    @Column(nullable = false)
    private String lessonName;

    private Integer creditScore;

    private Boolean isCompulsory;

    @ManyToMany(mappedBy = "lessons")
    private Set<LessonProgram> lessonPrograms;

}
