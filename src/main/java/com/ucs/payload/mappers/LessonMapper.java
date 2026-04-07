package com.ucs.payload.mappers;

import com.ucs.entity.concretes.business.Lesson;
import com.ucs.payload.request.business.LessonRequest;
import com.ucs.payload.response.business.LessonResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(target = "lessonId", ignore = true)
    @Mapping(target = "lessonPrograms", ignore = true)
    Lesson toLesson(LessonRequest lessonRequest);

    LessonResponse toLessonResponse(Lesson lesson);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lessonId", ignore = true)
    @Mapping(target = "lessonPrograms", ignore = true)
    void updateLessonFromRequest(LessonRequest lessonRequest, @MappingTarget Lesson lesson);

}
