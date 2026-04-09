package com.ucs.contactmessage.service;

import com.ucs.contactmessage.dto.ContactMessageRequest;
import com.ucs.contactmessage.dto.ContactMessageResponse;
import com.ucs.contactmessage.entity.ContactMessage;
import com.ucs.payload.response.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

public interface IContactMessageService {

    ContactMessageResponse save(ContactMessageRequest contactMessageRequest);
    Page<ContactMessageResponse> getAll(Pageable pageable);
    Page<ContactMessageResponse> searchByEmail(String email, Pageable pageable);
    Page<ContactMessageResponse> searchBySubject(String subject,Pageable pageable);
    List<ContactMessageResponse> searchByDateBetween(String beginDateString, String endDateString);
    ContactMessageResponse deleteById(Long id);
    ContactMessageResponse updateMessageById(Long id, ContactMessageRequest contactMessageRequest);
    ContactMessage getContactMessageById(Long id);
    ContactMessageResponse getContactMessageResponseById(Long id);
}
