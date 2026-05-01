package com.ucs.service.business;

import com.ucs.payload.request.business.EducationTermRequest;
import com.ucs.payload.response.business.EducationTermResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEducationTermService {

    EducationTermResponse saveEducationTerm(EducationTermRequest educationTermRequest);
    EducationTermResponse getEducationTermResponseById(Long id);
    List<EducationTermResponse> getAllEducationTerms();
    Page<EducationTermResponse> getAllEducationTermsByPage(int page, int size, String sort, String type);
    void deleteEducationTermById(Long id);
    EducationTermResponse updateEducationTerm(Long id, EducationTermRequest request);
}
