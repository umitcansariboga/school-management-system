package com.ucs.entity.concretes.business;

import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.Day;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Day day;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime stopTime;

    @ManyToMany
    @JoinTable(
            name = "lesson_program_lesson",
            joinColumns = @JoinColumn(name = "lessonprogram_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson> lessons;

    @ManyToOne
    private EducationTerm educationTerm;

    @ManyToMany(mappedBy = "lessonProgramList")
    private Set<User> users;

    @PreRemove
    private void removeLessonProgramFromUser() {
        users.forEach(user -> user.getLessonProgramList().remove(this));
    }
}
