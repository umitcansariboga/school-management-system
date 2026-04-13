package com.ucs.service.validator;

import com.ucs.entity.concretes.user.User;
import com.ucs.exception.ConflictException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.payload.request.abstracts.AbstractUserRequest;
import com.ucs.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkDuplicate(String username, String ssn, String phone, String email) {
        if (username != null && userRepository.existsByUsername(username)) {
            throw new ConflictException(ErrorMessageType.USERNAME_ALREADY_REGISTERED, username);
        }

        if (ssn != null && userRepository.existsBySsn(ssn)) {
            throw new ConflictException(ErrorMessageType.SSN_ALREADY_REGISTERED, ssn);
        }

        if (phone != null && userRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(ErrorMessageType.PHONE_ALREADY_REGISTERED, phone);
        }

        if (email != null && userRepository.existsByEmail(email)) {
            throw new ConflictException(ErrorMessageType.EMAIL_ALREADY_REGISTERED, email);
        }
    }

    public void checkUniqueProperties(User user, AbstractUserRequest request) {

        String upsatedUsername = null;
        String updatedSnn = null;
        String updatedPhone = null;
        String updateEmail = null;

        boolean isChanged = false;

        if (!user.getUsername().equalsIgnoreCase(request.getUsername())) {
            upsatedUsername = request.getUsername();
            isChanged = true;
        }

        if (!user.getSsn().equalsIgnoreCase(request.getSsn())) {
            updatedSnn = request.getSsn();
            isChanged = true;
        }

        if (!user.getPhoneNumber().equalsIgnoreCase(request.getPhoneNumber())) {
            updatedPhone = request.getPhoneNumber();
            isChanged = true;
        }

        if (!user.getEmail().equalsIgnoreCase(request.getEmail())) {
            updateEmail = request.getEmail();
            isChanged = true;
        }

        if (isChanged) {
            checkDuplicate(upsatedUsername, updatedSnn, updatedPhone, updateEmail);
        }
    }
}
