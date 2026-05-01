package com.ucs.service.business.impl;

import com.ucs.entity.concretes.business.Lesson;
import com.ucs.exception.ConflictException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.exception.ResourceNotFoundException;
import com.ucs.payload.mappers.LessonMapper;
import com.ucs.payload.request.business.LessonRequest;
import com.ucs.payload.response.business.LessonResponse;
import com.ucs.repository.business.LessonRepository;
import com.ucs.service.business.ILessonService;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements ILessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;

    @Transactional
    public LessonResponse saveLesson(LessonRequest lessonRequest) {
        methodHelper.checkLessonExistenceByName(lessonRequest.getLessonName());
        Lesson savedLesson = lessonRepository.save(lessonMapper.toLesson(lessonRequest));
        return lessonMapper.toLessonResponse(savedLesson);
    }

    @Transactional
    public void deleteLesson(Long id) {
        Lesson foundLesson = methodHelper.getLessonById(id);
        lessonRepository.delete(foundLesson);
    }

    public LessonResponse getLessonByLessonName(String lessonName) {
        Optional<Lesson> lessonOptional = lessonRepository.findByLessonNameIgnoreCase(lessonName);

        if (lessonOptional.isPresent()) {
            return lessonMapper.toLessonResponse(lessonOptional.get());
        } else {
            throw new ResourceNotFoundException(ErrorMessageType.LESSON_NOT_FOUND_FIELD, lessonName);
        }
    }

    public Page<LessonResponse> findLessonByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return lessonRepository.findAll(pageable)
                .map(lessonMapper::toLessonResponse);
    }

    public Set<Lesson> getLessonByLessonIdSet(Set<Long> idSet) {
        return idSet
                .stream()
                .map(methodHelper::getLessonById)
                .collect(Collectors.toSet());
    }

    @Transactional
    public LessonResponse updateLessonById(Long lessonId, LessonRequest lessonRequest) {
        Lesson lesson = methodHelper.getLessonById(lessonId);

        if (!(lesson.getLessonName().equalsIgnoreCase(lessonRequest.getLessonName())) &&
                lessonRepository.existsByLessonNameEqualsIgnoreCase(lessonRequest.getLessonName())) {
            throw new ConflictException(ErrorMessageType.LESSON_ALREADY_EXISTS_NAME, lessonRequest.getLessonName());
        }
        Lesson updatedLesson = lessonMapper.updatedLessonFromRequest(lessonRequest, lesson);

        updatedLesson.setLessonPrograms(lesson.getLessonPrograms());

        Lesson savedLesson = lessonRepository.save(updatedLesson);

        return lessonMapper.toLessonResponse(savedLesson);
    }
}
