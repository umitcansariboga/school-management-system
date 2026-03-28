package com.ucs.contactmessage.service;

import com.ucs.contactmessage.dto.ContactMessageRequest;
import com.ucs.contactmessage.dto.ContactMessageResponse;
import com.ucs.payload.response.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContactMessageService {

    ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest);
    Page<ContactMessageResponse> getAll(Pageable pageable);
    Page<ContactMessageResponse> searchByEmail(String email, Pageable pageable);
    Page<ContactMessageResponse> searchBySubject(String subject,Pageable pageable);
}
