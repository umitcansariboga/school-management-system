package com.ucs.controller.user;

import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.request.business.ChooseLessonProgramWithId;
import com.ucs.payload.request.updateRequest.StudentUpdateByManagersRequest;
import com.ucs.payload.request.updateRequest.StudentUpdateRequest;
import com.ucs.payload.request.user.StudentRequest;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.user.StudentResponse;
import com.ucs.service.user.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final IStudentService studentService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<StudentResponse>> saveStudent(
            @RequestBody @Valid StudentRequest studentRequest) {
        return ResponseEntity.ok(ResponseMessage.success(studentService.saveStudent(studentRequest),
                SuccessMessageType.STUDENT_SAVED));
    }

    @PatchMapping("/me")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<String> updateStudent(
            @RequestBody @Valid StudentUpdateRequest studentUpdateRequest
    ) {
        return ResponseEntity.ok(studentService.updateStudent(studentUpdateRequest));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<StudentResponse>> updateStudentForManagers(
            StudentUpdateByManagersRequest studentRequest,
            @PathVariable Long userId) {
        return ResponseEntity.ok(ResponseMessage.success(studentService.updateStudentForManagers(userId, studentRequest),
                SuccessMessageType.STUDENT_UPDATED));
    }

    @PostMapping("/choose-lesson")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<ResponseMessage<StudentResponse>> addLessonProgram(
            @RequestBody @Valid ChooseLessonProgramWithId chooseLessonProgramWithId
    ) {
        return ResponseEntity.ok(ResponseMessage.success(studentService.addLessonProgramToStudent(chooseLessonProgramWithId),
                SuccessMessageType.LESSON_PROGRAM_ADDED));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<Void>> changeStatusOfStudent(
            @PathVariable Long id,
            @RequestParam boolean status) {
        studentService.changeStatusOfStudent(id, status);
        String statusWord = status ? "active" : "passive";
        return ResponseEntity.ok(ResponseMessage.success(null,
                SuccessMessageType.STUDENT_STATUS_CHANGED, statusWord));
    }
}
