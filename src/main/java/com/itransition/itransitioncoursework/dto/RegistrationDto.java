package com.itransition.itransitioncoursework.dto;
//Sevinch Abdisattorova 06/15/2022 11:03 AM


import com.itransition.itransitioncoursework.validation.PasswordsEqualConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;


@PasswordsEqualConstraint.List({
        @PasswordsEqualConstraint(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    private UUID id;

    @Size(min = 3, message = "First name should consist of at least 3 letters!")
    private String firstName;

    @Size(min = 3, message = "Last name should consist of at least 3 letters!")
    private String lastName;

    @NotEmpty(message = "Email can't be empty!")
    @Email(message = "Please enter a valid email!")
    private String email;

    @NotBlank(message = "New password is mandatory!")
    private String password;

    @NotBlank(message = "Confirm Password is mandatory!")
    private String confirmPassword;


}
