package com.ucs.contactmessage.service.impl;

import com.ucs.contactmessage.dto.ContactMessageRequest;
import com.ucs.contactmessage.dto.ContactMessageResponse;
import com.ucs.contactmessage.entity.ContactMessage;
import com.ucs.contactmessage.mapper.ContactMessageMapper;
import com.ucs.contactmessage.repository.ContactMessageRepository;
import com.ucs.contactmessage.service.IContactMessageService;
import com.ucs.exception.ConflictException;
import com.ucs.exception.MessageType;
import com.ucs.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactMessageServiceImpl implements IContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;

    @Override
    public ContactMessageResponse save(ContactMessageRequest contactMessageRequest) {
        final ContactMessage contactMessage = contactMessageMapper.toContactMessage(contactMessageRequest);
        final ContactMessage savedMessage = contactMessageRepository.save(contactMessage);
        return contactMessageMapper.toContactMessageResponse(savedMessage);
    }

    @Override
    public Page<ContactMessageResponse> getAll(Pageable pageable) {
        return contactMessageRepository.findAll(pageable).map(contactMessageMapper::toContactMessageResponse);
    }

    @Override
    public Page<ContactMessageResponse> searchByEmail(String email, Pageable pageable) {
        return contactMessageRepository.findbyEmail(email, pageable).map(contactMessageMapper::toContactMessageResponse);
    }

    @Override
    public Page<ContactMessageResponse> searchBySubject(String subject, Pageable pageable) {
        return contactMessageRepository.findBySubject(subject, pageable).map(contactMessageMapper::toContactMessageResponse);
    }

    public List<ContactMessageResponse> searchByDateBetween(String beginDateString, String endDateString) {
        try {
            LocalDate beginDate = LocalDate.parse(beginDateString);
            LocalDate endDate = LocalDate.parse(endDateString);

            LocalDateTime startDateTime = beginDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

            List<ContactMessage> rawMessages = contactMessageRepository.findByDateTimeBetween(startDateTime, endDateTime);

            return rawMessages
                    .stream()
                    .map(contactMessageMapper::toContactMessageResponse)
                    .collect(Collectors.toList());
        } catch (DateTimeParseException e) {
            throw new ConflictException(MessageType.WRONG_DATE_FORMAT, beginDateString);
        }
    }

    @Transactional
    public ContactMessageResponse deleteById(Long id) {
       ContactMessage contactMessage=getContactMessageById(id);
         contactMessageRepository.delete(contactMessage);
        return contactMessageMapper.toContactMessageResponse(contactMessage);
    }

    public ContactMessageResponse updateMessageById(Long id, ContactMessageRequest contactMessageRequest) {
        ContactMessage contactMessage = getContactMessageById(id);

        contactMessageMapper.updateContactMessageFromDto(contactMessageRequest,contactMessage);
        ContactMessage savedContactMessage = contactMessageRepository.save(contactMessage);
        return contactMessageMapper.toContactMessageResponse(savedContactMessage);
    }

    public ContactMessage getContactMessageById(Long id) {
        return contactMessageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(MessageType.CONTACT_MESSAGE_NOT_FOUND,id));
    }

    public ContactMessageResponse getContactMessageResponseById(Long id) {
        ContactMessage contactMessage = getContactMessageById(id);
        return contactMessageMapper.toContactMessageResponse(contactMessage);
    }
}
