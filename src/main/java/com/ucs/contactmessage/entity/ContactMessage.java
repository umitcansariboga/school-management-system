package com.ucs.contactmessage.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_contact_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessage {

    @Id
    @GeneratedValue(generator = "sequence",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "squence",
            sequenceName = "guest_seq",
            initialValue = 1000,
            allocationSize = 10)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String subject;

    @Column(nullable = false, length = 1000)
    private String message;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateTime;
}
