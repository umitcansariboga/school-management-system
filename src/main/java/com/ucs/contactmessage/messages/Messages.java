package com.ucs.contactmessage.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Messages {
    private static MessageSource messageSource;

    @Autowired
    public Messages(MessageSource messageSource){
        Messages.messageSource=messageSource;
    }

    public static String getMessage(String key,Object... args){
        return messageSource.getMessage(key,args, LocaleContextHolder.getLocale());
    }

    public static final String NOT_FOUND = "contact.message.not.found";
    public static final String DELETED = "contact.message.deleted";
}

//serviste kullanimi // Eskiden: Messages.NOT_FOUND_MESSAGE
/// / Şimdi:
//throw new ResourceNotFoundException(Messages.getMessage(Messages.NOT_FOUND));
