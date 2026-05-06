package com.ucs.security.service;

import com.ucs.contactmessage.messages.Messages;
import com.ucs.entity.concretes.user.User;
import com.ucs.exception.ErrorMessageType;
import com.ucs.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format(Messages.getMessage(
                        ErrorMessageType.USER_NOT_FOUND_WITH_USERNAME.getMessage()), username)));

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getIsAdvisor(),
                user.getSsn(),
                user.getPassword(),
                user.getUserRole().getRoleType().name(),
                user.isActive(),
                user.getIsLocked(),
                user.getLastPasswordChange()
        );
    }
}
