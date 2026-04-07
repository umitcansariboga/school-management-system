package com.ucs.payload.mappers;

import com.ucs.entity.concretes.business.EducationTerm;
import com.ucs.entity.concretes.business.Lesson;
import com.ucs.entity.concretes.business.LessonProgram;
import com.ucs.payload.request.business.LessonProgramRequest;
import com.ucs.payload.response.business.LessonProgramResponse;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring",uses = {LessonMapper.class})
public interface LessonProgramMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "lessons", source = "lessonSet")
    @Mapping(target = "educationTerm",source = "educationTerm")
    @Mapping(target = "users",ignore = true)
    LessonProgram toLessonProgram(LessonProgramRequest lessonProgramRequest,
                                  Set<Lesson> lessonSet,
                                  EducationTerm educationTerm);

    LessonProgramResponse toLessonProgramResponse(LessonProgram lessonProgram);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "lessons", source = "lessonSet")
    @Mapping(target = "educationTerm",source = "educationTerm")
    @Mapping(target = "users",ignore = true)
    void updateLessonProgramFromRequest(LessonProgramRequest lessonProgramRequest, @MappingTarget LessonProgram lessonProgram,
                                        Set<Lesson> lessonSet,
                                        EducationTerm educationTerm);
}
