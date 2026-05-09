package com.ucs.controller.business;

import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.request.business.LessonRequest;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.business.LessonResponse;
import com.ucs.service.business.ILessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final ILessonService lessonService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<LessonResponse>> saveLesson(
            @RequestBody @Valid LessonRequest lessonRequest) {
        return ResponseEntity.ok(ResponseMessage.success(lessonService.saveLesson(lessonRequest),
                SuccessMessageType.LESSON_SAVED));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.deleteLesson(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<LessonResponse>> getLessonByLessonName(
            @RequestParam(name = "name") String lessonName) {
        return ResponseEntity.ok(ResponseMessage.success(lessonService.getLessonByLessonName(lessonName),
                SuccessMessageType.LESSON_FOUND));
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<Page<LessonResponse>> getLessonByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "LessonName") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return ResponseEntity.ok(lessonService.findLessonByPage(page, size, sort, type));
    }

    @GetMapping("/by-ids/page")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<Page<LessonResponse>> getAllLessonByLessonId(
            @RequestParam(name = "lessonIds") Set<Long> ids,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "LessonName") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return ResponseEntity.ok(lessonService.getLessonByLessonIdPage(page, size, sort, type, ids));
    }

    @PutMapping("/{lessonId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<LessonResponse>> updateLessonById(
            @PathVariable Long lessonId,
            @RequestBody @Valid LessonRequest lessonRequest) {

        return ResponseEntity.ok(ResponseMessage.success(lessonService.updateLessonById(lessonId, lessonRequest),
                SuccessMessageType.LESSON_UPDATED));
    }
}
