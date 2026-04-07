package com.ucs.payload.mappers;

import com.ucs.entity.concretes.business.EducationTerm;
import com.ucs.payload.request.business.EducationTermRequest;
import com.ucs.payload.response.business.EducationTermResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EducationTermMapper {

    EducationTermResponse toEducationTermResponse(EducationTerm educationTerm);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lessonPrograms", ignore = true)
    EducationTerm toEducationTerm(EducationTermRequest educationTermRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lessonPrograms", ignore = true)
    void updateEducationTermFromRequest(EducationTermRequest educationTermRequest, @MappingTarget EducationTerm educationTerm);
}
