package com.ucs.contactmessage.mapper;

import com.ucs.contactmessage.dto.ContactMessageRequest;
import com.ucs.contactmessage.dto.ContactMessageResponse;
import com.ucs.contactmessage.entity.ContactMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateTime", ignore = true)
    ContactMessage toContactMessage(ContactMessageRequest contactMessageRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateTime", ignore = true)
    void updateContactMessageFromDto(ContactMessageRequest contactMessageRequest, @MappingTarget ContactMessage contactMessage);

    ContactMessageResponse toContactMessageResponse(ContactMessage contactMessage);

    List<ContactMessageResponse> toContactMessageResponseList(List<ContactMessage> contactMessageList);

}
