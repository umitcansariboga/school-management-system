package com.ucs.controller.user;

import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.request.updateRequest.TeacherUpdateByManagerRequest;
import com.ucs.payload.request.user.TeacherRequest;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.user.StudentResponse;
import com.ucs.payload.response.user.TeacherResponse;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.service.user.ITeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final ITeacherService teacherService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<TeacherResponse>> saveTeacher(@RequestBody @Valid TeacherRequest teacherRequest) {
        return ResponseEntity.ok(ResponseMessage.success(teacherService.saveTeacher(teacherRequest),
                SuccessMessageType.TEACHER_SAVED));
    }

    @GetMapping("/advisor-students")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseEntity<List<StudentResponse>> getAllStudentByAdvisorUsername() {
        return ResponseEntity.ok(teacherService.getAllStudentByAdvisorUserName());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<TeacherResponse>> updateTeacherForManager(
            @RequestBody @Valid TeacherUpdateByManagerRequest teacherRequest,
            @PathVariable Long userId) {

        return ResponseEntity.ok(ResponseMessage.success(teacherService.updateTeacherForManagers(teacherRequest, userId),
                SuccessMessageType.TEACHER_UPDATED));
    }

    @PatchMapping("/{teacherId}/advisor")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<UserResponse>> saveAdvisorTeacher(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ResponseMessage.success(teacherService.saveAdvisorTeacher(teacherId),
                SuccessMessageType.ADVISOR_TEACHER_SAVED));
    }

    @DeleteMapping("/{id}/advisor")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<UserResponse>> deleteAdvisorTeacherById(
            @PathVariable Long id) {
        return ResponseEntity.ok(ResponseMessage.success(teacherService.deleteAdvisorTeacherById(id),
                SuccessMessageType.ADVISOR_TEACHER_DELETED));
    }

    @GetMapping("/advisors/page")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<Page<UserResponse>> getAllAdvisorTeacher(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return ResponseEntity.ok(teacherService.getAllAdvisorTeacher(page, size, sort, type));
    }


}
