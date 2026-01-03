package org.example.dtos.user;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {
    private String appUserName;

    private String appUserEmail;

    private String appUserPassword;

    private String appUserNationality;

    private Set<String> appUserLanguagesSpoken;

    private String appUserCity;

    private String appUserLiveInDistrict;

    private Set<String> appUserCommuteThroughDistricts;
}
