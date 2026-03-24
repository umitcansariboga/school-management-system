package com.ucs.contactmessage.service;

import com.ucs.contactmessage.dto.ContactMessageRequest;
import com.ucs.contactmessage.dto.ContactMessageResponse;
import com.ucs.contactmessage.mapper.ContactMessageMapper;
import com.ucs.contactmessage.repository.ContactMessageRepository;
import com.ucs.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;
    private final MessageSource messageSource;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest){
        return null;
    }
}
