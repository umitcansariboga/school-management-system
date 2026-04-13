package com.ucs.contactmessage.controller.impl;

import com.ucs.contactmessage.controller.IContactMessageController;
import com.ucs.contactmessage.dto.ContactMessageRequest;
import com.ucs.contactmessage.dto.ContactMessageResponse;
import com.ucs.contactmessage.service.IContactMessageService;
import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.response.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact-messages")
public class ContactMessageControllerImpl implements IContactMessageController {

    private final IContactMessageService contactMessageService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseMessage<ContactMessageResponse>> saveContact(@RequestBody @Valid ContactMessageRequest contactMessageRequest,
                                                                               WebRequest request) {
        ContactMessageResponse response = contactMessageService.save(contactMessageRequest);
        return ResponseEntity.ok(ResponseMessage.success(response,SuccessMessageType.SUCCESS_SAVED,request));
    }

    @Override
    @GetMapping

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<Page<ContactMessageResponse>> getAll(
            @PageableDefault(size = 10, page = 0, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(contactMessageService.getAll(pageable));
    }

    @Override
    @GetMapping("/search/email")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<Page<ContactMessageResponse>> searchByEmail(
            @RequestParam(value = "email") @Valid String email,
            @PageableDefault(size = 10, page = 0, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(contactMessageService.searchByEmail(email, pageable));
    }

    @Override
    @GetMapping("search/subject")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<Page<ContactMessageResponse>> searchBySubject(
            @RequestParam(value = "subject") @Valid String subject,
            @PageableDefault(size = 10, page = 0, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(contactMessageService.searchBySubject(subject, pageable));
    }

    @Override
    @GetMapping("/search/date-between")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<List<ContactMessageResponse>> searchByDateBetween(
            @RequestParam(value = "beginDate") String beginDateString,
            @RequestParam(value = "endDate") String endDateString) {
        return ResponseEntity.ok(contactMessageService.searchByDateBetween(beginDateString, endDateString));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<ContactMessageResponse>> deleteById(@PathVariable(value = "id") Long id,
                                                                              WebRequest request) {
        ContactMessageResponse response=contactMessageService.deleteById(id);

        return ResponseEntity.ok(ResponseMessage.success(response, SuccessMessageType.SUCCESS_DELETED,request));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage<ContactMessageResponse>> updateMessageById(
            @PathVariable(value = "id") Long id, @RequestBody @Valid ContactMessageRequest contactMessageRequest,
            WebRequest request) {

        ContactMessageResponse response = contactMessageService.updateMessageById(id, contactMessageRequest);
        return ResponseEntity.ok(ResponseMessage.success(response,SuccessMessageType.SUCCESS_UPDATED,request));
    }
}
