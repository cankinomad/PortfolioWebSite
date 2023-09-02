package org.berka.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @NotBlank(message = "FirstName cannot be null")
    String firstName;
    @NotBlank(message = "LastName cannot be null")
    String lastName;
    @Email(message = "your email doesn't seem like real ^_~")
    @NotBlank
    String email;
    @Size(min = 5,max = 32, message = "Password must contain min 5 and max 32 character")
    String password;
}
