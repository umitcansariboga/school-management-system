package com.ucs.entity.concretes.business;

import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.Note;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer absentee;
    private Double midtermExam;
    private Double finalExam;
    private Double examAverage;

    private String infoNote;

    @Enumerated(EnumType.STRING)
    private Note letterGrade;

    @ManyToOne
    private User teacher;

    @ManyToOne
    private User student;

    @ManyToOne
    private Lesson lesson;

    @ManyToOne
    private EducationTerm educationTerm;
}
