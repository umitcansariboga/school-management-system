package com.ucs.contactmessage.controller;

import com.ucs.contactmessage.dto.ContactMessageRequest;
import com.ucs.contactmessage.dto.ContactMessageResponse;
import com.ucs.payload.response.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IContactMessageController {
    ResponseEntity<ResponseMessage<ContactMessageResponse>> saveContact(ContactMessageRequest contactMessageRequest);
    ResponseEntity<Page<ContactMessageResponse>>  getAll(Pageable pageable);
    ResponseEntity<Page<ContactMessageResponse>> searchByEmail(String email, Pageable pageable);
    ResponseEntity<Page<ContactMessageResponse>> searchBySubject(String subject,Pageable pageable);
    ResponseEntity<List<ContactMessageResponse>> searchByDateBetween(String beginDateString, String endDateString);
    ResponseEntity<String> deleteById(Long id);
    ResponseEntity<ResponseMessage<ContactMessageResponse>> updateMessageById(Long id, ContactMessageRequest contactMessageRequest);
}
