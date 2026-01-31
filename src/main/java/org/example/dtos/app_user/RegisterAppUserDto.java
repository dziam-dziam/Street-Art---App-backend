package org.example.dtos.app_user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAppUserDto {

    @NotBlank(message = "Name is required")
    @Size(max = 30, message = "Name cannot exceed 30 characters")
    private String appUserName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String appUserEmail;

    @NotBlank(message = "Password is required")
    @Size(min = 10, message = "Password must be at least 10 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{10,}$",
            message = "Password must contain: 1 uppercase, 1 number, 1 special character, min length 10"
    )
    private String appUserPassword;

    @NotBlank(message = "Nationality is required")
    private String appUserNationality;

    @NotEmpty(message = "Languages spoken are required")
    private Set<@NotBlank(message = "Language cannot be blank") String> appUserLanguagesSpoken;

    @NotBlank(message = "City is required")
    private String appUserCity;

    @NotBlank(message = "Live-in district is required")
    private String appUserLiveInDistrict;
}
