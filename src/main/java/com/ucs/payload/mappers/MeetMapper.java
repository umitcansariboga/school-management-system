package com.ucs.payload.mappers;

import com.ucs.entity.concretes.business.Meet;
import com.ucs.entity.concretes.user.User;
import com.ucs.payload.request.business.MeetRequest;
import com.ucs.payload.response.business.MeetResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface MeetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "advisoryTeacher", ignore = true)
    @Mapping(target = "studentList", ignore = true)
    Meet toMeet(MeetRequest meetRequest);

    @Mapping(target = "teacherName", expression = "java(meet.getAdvisoryTeacher().getName() + \" \" + meet.getAdvisoryTeacher().getSurname())")
    @Mapping(target = "ssn", source = "advisoryTeacher.ssn")
    @Mapping(target = "username", source = "advisoryTeacher.username")
    @Mapping(target = "advisorTeacherId", source = "advisoryTeacher.id")
    @Mapping(target = "studentsIds", source = "studentList")
    MeetResponse toMeetResponse(Meet meet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "advisoryTeacher", ignore = true)
    @Mapping(target = "studentList", ignore = true)
    void updatetoMeetFromRequest(MeetRequest meetRequest, @MappingTarget Meet meet);

    default List<Long> mapUserListToIds(Set<User> value) {
        if (value == null) return null;
        return value.stream().map(User::getId).toList();
    }
}
