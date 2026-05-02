package com.ucs.service.business;

import com.ucs.entity.concretes.business.Lesson;
import com.ucs.payload.request.business.LessonRequest;
import com.ucs.payload.response.business.LessonResponse;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ILessonService {

    LessonResponse saveLesson(LessonRequest lessonRequest);
    String deleteLesson(Long id);
    LessonResponse getLessonByLessonName(String lessonName);
    Page<LessonResponse> findLessonByPage(int page, int size, String sort, String type);
    Set<Lesson> getLessonByLessonIdSet(Set<Long> idSet);
    LessonResponse updateLessonById(Long lessonId, LessonRequest lessonRequest);
}
