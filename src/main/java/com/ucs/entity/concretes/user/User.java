package com.ucs.entity.concretes.user;

import com.ucs.entity.concretes.business.LessonProgram;
import com.ucs.entity.concretes.business.Meet;
import com.ucs.entity.concretes.business.StudentInfo;
import com.ucs.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String ssn;

    private String name;
    private String surname;
    private LocalDate birthDay;
    private String birthPlace;
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private Boolean builtIn;

    private String motherName;
    private String fatherName;

    private Integer studentNumber;

    private Boolean isActive;

    private Long advisorTeacherId;

    private Boolean isAdvisor;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    private UserRole userRole;

    @OneToMany(mappedBy = "teacher")
    private List<StudentInfo> studentInfos;

    @ManyToMany
    @JoinTable(
            name = "user_lesson_program",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id")
    )
    private Set<LessonProgram> lessonProgramSet;

    @ManyToMany(mappedBy = "studentList")
    private Set<Meet> meetList;
}
