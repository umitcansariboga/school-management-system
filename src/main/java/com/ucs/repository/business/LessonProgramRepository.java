package com.ucs.repository.business;

import com.ucs.entity.concretes.business.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LessonProgramRepository extends JpaRepository<LessonProgram,Long> {

    List<LessonProgram> findByUsers_IdNull();

    List<LessonProgram> findByUsers_IdNotNull();

    Set<LessonProgram> findByUsers_Username(String username);

    Set<LessonProgram> findByIdIn(Set<Long> lessonProgramIds);
}
