package com.ucs.service.business.impl;

import com.ucs.contactmessage.messages.Messages;
import com.ucs.entity.concretes.business.EducationTerm;
import com.ucs.entity.concretes.business.Lesson;
import com.ucs.entity.concretes.business.LessonProgram;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.exception.ResourceNotFoundException;
import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.mappers.LessonProgramMapper;
import com.ucs.payload.request.business.LessonProgramRequest;
import com.ucs.payload.response.business.LessonProgramResponse;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.business.LessonProgramRepository;
import com.ucs.service.business.IEducationTermService;
import com.ucs.service.business.ILessonService;
import com.ucs.service.business.ILessonProgramService;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import com.ucs.service.validator.DateTimeValidator;
import io.jsonwebtoken.security.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgramServiceImpl implements ILessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final ILessonService lessonService;
    private final IEducationTermService educationTermService;
    private final DateTimeValidator dateTimeValidator;
    private final LessonProgramMapper lessonProgramMapper;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;

    @Transactional
    public LessonProgramResponse saveLessonProgram(LessonProgramRequest lessonProgramRequest) {

        Set<Lesson> lessons = lessonService.getLessonByLessonIdSet(lessonProgramRequest.getLessonIdList());
        EducationTerm educationTerm = methodHelper.getEducationTermById(lessonProgramRequest.getEducationTermId());

        if (lessons.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageType.LESSON_NOT_FOUND_IN_LIST);
        }

        dateTimeValidator.checkTimeWithException(lessonProgramRequest.getStartTime(), lessonProgramRequest.getStopTime());

        LessonProgram lessonProgram = lessonProgramMapper.toLessonProgram(lessonProgramRequest, lessons, educationTerm);

        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);

        return lessonProgramMapper.toLessonProgramResponse(savedLessonProgram);
    }

    public List<LessonProgramResponse> getAllLessonPrograms() {

        return lessonProgramRepository
                .findAll()
                .stream()
                .map(lessonProgramMapper::toLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public LessonProgramResponse getLessonProgramById(Long id) {
        return lessonProgramMapper.toLessonProgramResponse(getLessonProgramEntityById(id));
    }

    public LessonProgram getLessonProgramEntityById(Long id) {
        return lessonProgramRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageType.LESSON_PROGRAM_NOT_FOUND_FIELD, id));
    }

    public Set<LessonProgram> getLessonProgramByIdSet(Set<Long> lessonIdSet) {
        Set<LessonProgram> lessonPrograms = lessonProgramRepository.findByIdIn(lessonIdSet);
        if (lessonPrograms.size() != lessonIdSet.size()) {
            throw new BadRequestException(ErrorMessageType.LESSON_PROGRAM_NOT_FOUND);
        }
        return lessonPrograms;
    }

    public List<LessonProgramResponse> getAllUnassigned() {
        return lessonProgramRepository
                .findByUsers_IdNull()
                .stream()
                .map(lessonProgramMapper::toLessonProgramResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public String deleteById(Long id) {
        LessonProgram lessonProgram = getLessonProgramEntityById(id);
        lessonProgramRepository.delete(lessonProgram);

        return Messages.getMessage(SuccessMessageType.LESSON_PROGRAM_DELETED.getMessage());
    }

    public Set<LessonProgramResponse> getAllLessonProgramByUser(UserResponse authenticatedUser) {
        String username = authenticatedUser.getUsername();
        return lessonProgramRepository.findByUsers_Username(username)
                .stream()
                .map(lessonProgramMapper::toLessonProgramResponse)
                .collect(Collectors.toSet());
    }

    public List<LessonProgramResponse> getAllAssigned() {
        return lessonProgramRepository
                .findByUsers_IdNotNull()
                .stream()
                .map(lessonProgramMapper::toLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public Page<LessonProgramResponse> getAllLessonProgramByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return lessonProgramRepository.findAll(pageable)
                .map(lessonProgramMapper::toLessonProgramResponse);
    }
}
