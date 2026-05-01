package com.ucs.service.business;

import com.ucs.entity.concretes.business.LessonProgram;
import com.ucs.payload.request.business.LessonProgramRequest;
import com.ucs.payload.response.business.LessonProgramResponse;
import com.ucs.payload.response.user.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ILessonProgramService {

    LessonProgramResponse saveLessonProgram(LessonProgramRequest lessonProgramRequest);

    List<LessonProgramResponse> getAllLessonPrograms();

    LessonProgramResponse getLessonProgramById(Long id);

    LessonProgram getLessonProgramEntityById(Long id);

    Set<LessonProgram> getLessonProgramByIdSet(Set<Long> lessonIdSet);

    List<LessonProgramResponse> getAllUnassigned();

    void deleteById(Long id);

    Set<LessonProgramResponse> getAllLessonProgramByUser(UserResponse authenticatedUser);

    List<LessonProgramResponse> getAllAssigned();

    Page<LessonProgramResponse> getAllLessonProgramByPage(int page, int size, String sort, String type);
}
