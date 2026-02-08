package org.example.dtos.app_user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.config.Validation;
import org.example.dtos.commute.CommuteDto;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppUserDto {

    @NotBlank(message = "Name is required.")
    @Size(min = 5, max = 30, message = "Name must be 5-30 characters.")
    private String appUserName;

    @NotBlank(message = "Email is required.")
    @Pattern(regexp = Validation.EMAIL_REGEX, message = "Email format is invalid.")
    private String appUserEmail;

    @Pattern(
            regexp = "^$|" + Validation.PASSWORD_REGEX,
            message = "Min 10 characters + 1 uppercase letter + 1 digit + 1 special character."
    )
    private String appUserPassword;

    @Size(min = 1, message = "Select at least one language.")
    private Set<@NotBlank(message = "Language cannot be blank.") String> appUserLanguagesSpoken;

    private String appUserCity;
    private String appUserLiveInDistrict;

    private Set<CommuteDto> appUserCommuteDtos;
}
