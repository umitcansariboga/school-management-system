package com.ucs.contactmessage.repository;

import com.ucs.contactmessage.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    Page<ContactMessage> findbyEmail(String email, Pageable pageable);

    Page<ContactMessage> findBySubject(String subject, Pageable pageable);

    List<ContactMessage> findByDateTimeBetween(LocalDateTime beginDateTime, LocalDateTime endDateTime);

}
