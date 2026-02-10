package org.example.dtos.app_user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProfileDto {
    private String appUserName;
    private String appUserEmail;
    private Set<String> appUserLanguagesSpoken;
}

