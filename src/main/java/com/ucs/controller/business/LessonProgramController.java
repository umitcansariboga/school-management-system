package com.ucs.controller.business;

import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.request.business.LessonProgramRequest;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.business.LessonProgramResponse;
import com.ucs.service.business.ILessonProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/lesson-programs")
@RequiredArgsConstructor
public class LessonProgramController {

    private final ILessonProgramService lessonProgramService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<LessonProgramResponse>> saveLessonProgram(
            @RequestBody @Valid LessonProgramRequest lessonProgramRequest) {

        return ResponseEntity.ok(ResponseMessage.success(lessonProgramService.saveLessonProgram(lessonProgramRequest),
                SuccessMessageType.LESSON_PROGRAM_SAVED));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<List<LessonProgramResponse>> gelAllLessonPrograms() {
        return ResponseEntity.ok(lessonProgramService.getAllLessonPrograms());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<LessonProgramResponse>> getLessonProgramById(
            @PathVariable Long id) {
        return ResponseEntity.ok(ResponseMessage.success(lessonProgramService.getLessonProgramById(id),
                SuccessMessageType.LESSON_PROGRAM_FOUND));
    }

    @GetMapping("/unassigned")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<List<LessonProgramResponse>> getAllUnassigned() {

        return ResponseEntity.ok(lessonProgramService.getAllUnassigned());
    }

    @GetMapping("/assigned")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<List<LessonProgramResponse>> getAllassigned() {

        return ResponseEntity.ok(lessonProgramService.getAllAssigned());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonProgramService.deleteById(id));
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<Page<LessonProgramResponse>> getAllLessonProgramByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return ResponseEntity.ok(lessonProgramService.getAllLessonProgramByPage(page, size, sort, type));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('TEACHER','STUDENT')")
    public ResponseEntity<Set<LessonProgramResponse>> getAllLessonProgramByUser() {
        return ResponseEntity.ok(lessonProgramService.getAllLessonProgramByUser());
    }
}
