package com.ucs.controller.business;

import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.request.business.StudentInfoRequest;
import com.ucs.payload.request.business.UpdateStudentInfoRequest;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.business.StudentInfoResponse;
import com.ucs.service.business.IStudentInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student-info")
@RequiredArgsConstructor
public class StudentInfoController {

    private final IStudentInfoService studentInfoService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseEntity<ResponseMessage<StudentInfoResponse>> saveStudentInfo(
            @RequestBody @Valid StudentInfoRequest studentInfoRequest) {

        return ResponseEntity.ok(ResponseMessage.success(studentInfoService.saveStudentInfo(studentInfoRequest),
                SuccessMessageType.STUDENT_INFO_SAVED));
    }

    @DeleteMapping("/{studentInfoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseEntity<String> deleteById(@PathVariable Long studentInfoId) {
        return ResponseEntity.ok(studentInfoService.deleteById(studentInfoId));
    }

    @PutMapping("/{studentInfoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseEntity<ResponseMessage<StudentInfoResponse>> updateById(
            @PathVariable Long studentInfoId,
            @RequestBody @Valid UpdateStudentInfoRequest studentInfoRequest) {
        return ResponseEntity.ok(ResponseMessage.success(studentInfoService.update(studentInfoRequest, studentInfoId),
                SuccessMessageType.STUDENT_INFO_UPDATED));
    }

    @GetMapping("/search-teacher")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForTeacher(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentInfoService.getAllForTeacher(page, size));
    }

    @GetMapping("/search-student")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForStudent(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentInfoService.getAllForStudent(page, size));
    }

    @GetMapping("/search-managers")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<Page<StudentInfoResponse>> getAllStudentInfoByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return ResponseEntity.ok(studentInfoService.getAllStudentInfoByPage(page, size, sort, type));
    }
}
