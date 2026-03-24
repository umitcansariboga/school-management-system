package com.ucs.contactmessage.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageRequest {

    @NotBlank(message = "Please enter name")
    @Size(min = 2, max = 50, message = "Your name {validatedValue} should be between {min} and {max} chars")
    @Pattern(regexp = "^[^0-9]*$", message = "Name cannot contain numeric characters")
    private String name;

    @NotBlank(message = "Please enter email")
    @Size(min = 5, max = 50, message = "Your email should be between {min} and {max} chars")
    @Email(message = "Please enter valid email")
    private String email;

    @NotBlank(message = "Please enter subject")
    @Size(min = 5, max = 50, message = "Your subject should be between {min} and {max} chars")
    private String subject;

    @NotBlank(message = "please enter message")
    @Size(min = 4, max = 1000, message = "Your message should be between {min} and {max} chars")
    private String message;
}
