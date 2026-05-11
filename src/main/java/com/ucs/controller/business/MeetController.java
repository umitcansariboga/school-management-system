package com.ucs.controller.business;

import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.request.business.MeetRequest;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.business.MeetResponse;
import com.ucs.service.business.IMeetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meets")
@RequiredArgsConstructor
public class MeetController {

    private final IMeetService meetService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseEntity<ResponseMessage<MeetResponse>> saveMeet(
            @RequestBody @Valid MeetRequest meetRequest) {
        return ResponseEntity.ok(ResponseMessage.success(meetService.saveMeet(meetRequest),
                SuccessMessageType.MEET_SAVED));
    }

    @GetMapping("/{meetId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<MeetResponse>> getMeetById(@PathVariable Long meetId) {
        return ResponseEntity.ok(ResponseMessage.success(meetService.getMeetById(meetId),
                SuccessMessageType.MEET_FOUND));
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<MeetResponse>> getAllMeetByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(meetService.getAllMeetByPage(page, size));
    }

    @GetMapping("/me/teacher/page")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public ResponseEntity<Page<MeetResponse>> getAllMeetByTeacher(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(meetService.getAllMeetByTeacher(page, size));
    }

    @GetMapping("/me/student")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<List<MeetResponse>> getAllMeetByStudent() {
        return ResponseEntity.ok(meetService.getAllMeetByStudent());
    }

    @DeleteMapping("/{meetId}")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public ResponseEntity<String> delete(@PathVariable Long meetId) {
        return ResponseEntity.ok(meetService.delete(meetId));
    }

    @PutMapping("/{meetId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Teacher')")
    public ResponseEntity<ResponseMessage<MeetResponse>> updateMeetById(
            @PathVariable Long meetId,
            @RequestBody @Valid MeetRequest meetRequest) {

        return ResponseEntity.ok(ResponseMessage.success(meetService.updatemeet(meetRequest, meetId),
                SuccessMessageType.MEET_UPDATED));
    }
}
