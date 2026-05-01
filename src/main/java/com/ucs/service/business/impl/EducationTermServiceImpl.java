package com.ucs.service.business.impl;

import com.ucs.entity.concretes.business.EducationTerm;
import com.ucs.payload.mappers.EducationTermMapper;
import com.ucs.payload.request.business.EducationTermRequest;
import com.ucs.payload.response.business.EducationTermResponse;
import com.ucs.repository.business.EducationTermRepository;
import com.ucs.service.business.IEducationTermService;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTermServiceImpl implements IEducationTermService {

    private final EducationTermRepository educationTermRepository;
    private final EducationTermMapper educationTermMapper;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;

    @Transactional
    public EducationTermResponse saveEducationTerm(EducationTermRequest educationTermRequest) {

        methodHelper.validateEducationTermDates(educationTermRequest);

        EducationTerm savedEducationTerm = educationTermRepository.save(
                educationTermMapper.toEducationTerm(educationTermRequest));

        return educationTermMapper.toEducationTermResponse(savedEducationTerm);
    }

    public EducationTermResponse getEducationTermResponseById(Long id) {

        EducationTerm term = methodHelper.getEducationTermById(id);

        return educationTermMapper.toEducationTermResponse(term);
    }

    public List<EducationTermResponse> getAllEducationTerms() {
        return educationTermRepository.findAll()
                .stream()
                .map(educationTermMapper::toEducationTermResponse)
                .collect(Collectors.toList());
    }

    public Page<EducationTermResponse> getAllEducationTermsByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return educationTermRepository.findAll(pageable)
                .map(educationTermMapper::toEducationTermResponse);
    }

    @Transactional
    public void deleteEducationTermById(Long id) {
        EducationTerm educationTerm = methodHelper.getEducationTermById(id);
        educationTermRepository.delete(educationTerm);
    }

    @Transactional
    public EducationTermResponse updateEducationTerm(Long id, EducationTermRequest request) {
        EducationTerm educationTerm = methodHelper.getEducationTermById(id);
        methodHelper.validateEducationTermDates(request);
        educationTermMapper.updateEducationTermFromRequest(request, educationTerm);
        EducationTerm savedTerm = educationTermRepository.save(educationTerm);
        return educationTermMapper.toEducationTermResponse(savedTerm);
    }
}
