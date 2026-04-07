package com.ucs.payload.mappers;

import com.ucs.entity.concretes.business.EducationTerm;
import com.ucs.entity.concretes.business.Lesson;
import com.ucs.entity.concretes.business.StudentInfo;
import com.ucs.entity.enums.Note;
import com.ucs.payload.request.business.StudentInfoRequest;
import com.ucs.payload.response.business.StudentInfoResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StudentInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "letterGrade", source = "letterGrade")
    @Mapping(target = "examAverage", source = "average")
    @Mapping(target = "lesson", source = "lesson")
    @Mapping(target = "educationTerm", source = "educationTerm")
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "student", ignore = true)
    StudentInfo toStudentInfo(StudentInfoRequest studentInfoRequest,
                              Note letterGrade,
                              Double average,
                              Lesson lesson,
                              EducationTerm educationTerm);

    @Mapping(target = "studentResponse", source = "student")
    @Mapping(target = "educationTerm", expression = "java(studentInfo.getEducationTerm().getTerm()")
    @Mapping(target = "lessonName", expression = "java(studentInfo.getLesson().getLessonName()")
    @Mapping(target = "creditScore", expression = "java(studentInfo.getLesson().creditScore()")
    @Mapping(target = "isCompulsory", expression = "java(studentInfo.getLesson().getIsCompulsory()")
    StudentInfoResponse toStudentInfoResponse(StudentInfo studentInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "letterGrade", source = "letterGrade")
    @Mapping(target = "examAverage", source = "average")
    @Mapping(target = "lesson", source = "lesson")
    @Mapping(target = "educationTerm", source = "educationTerm")
    void updateStudentInfoFromRequest(StudentInfoRequest studentInfoRequest,
                                      Note letterGrade,
                                      Double average,
                                      Lesson lesson,
                                      EducationTerm educationTerm,
                                      @MappingTarget StudentInfo studentInfo);
    
}
