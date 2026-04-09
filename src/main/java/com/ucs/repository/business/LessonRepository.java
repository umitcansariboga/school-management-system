package com.ucs.repository.business;

import com.ucs.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    boolean existsByLessonNameEqualsIgnoreCase(String lessonName);

    Optional<Lesson> findByLessonNameIgnoreCase(String lessonName);
}
