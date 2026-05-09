package com.ucs.controller.business;

import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.request.business.EducationTermRequest;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.business.EducationTermResponse;
import com.ucs.service.business.IEducationTermService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education-terms")
@RequiredArgsConstructor
public class EducationTermController {

    private final IEducationTermService educationTermService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<ResponseMessage<EducationTermResponse>> saveEducationTerm(
            @RequestBody @Valid EducationTermRequest educationTermRequest) {
        return ResponseEntity.ok(ResponseMessage.success(educationTermService.saveEducationTerm(educationTermRequest),
                SuccessMessageType.EDUCATION_TERM_SAVED));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public ResponseEntity<ResponseMessage<EducationTermResponse>> getEducationTermById(
            @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(ResponseMessage.success(educationTermService.getEducationTermById(id),
                SuccessMessageType.EDUCATION_TERM_FOUND));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<List<EducationTermResponse>> getAllEducationTerm() {
        return ResponseEntity.ok(educationTermService.getAllEducationTerms());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public ResponseEntity<Page<EducationTermResponse>> getAllEducationTermsByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "direction", defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(educationTermService.getAllEducationTermsByPage(page, size, sort, direction));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<String> deleteEducatinTerm(
            @PathVariable Long id) {
        return ResponseEntity.ok(educationTermService.deleteEducationTermById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<ResponseMessage<EducationTermResponse>> updateEducationTerm(
            @PathVariable Long id,
            @RequestBody @Valid EducationTermRequest educationTermRequest) {
        return ResponseEntity.ok(ResponseMessage.success(educationTermService.updateEducationTerm(id, educationTermRequest),
                SuccessMessageType.EDUCATION_TERM_UPDATED));
    }
}
