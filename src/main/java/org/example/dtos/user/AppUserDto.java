package org.example.dtos.user;

import lombok.*;
import org.example.dtos.commute.CommuteDto;

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

    private Set<CommuteDto> appUserCommuteDtos;
}
