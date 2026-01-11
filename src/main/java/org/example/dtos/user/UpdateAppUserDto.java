package org.example.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dtos.commute.CommuteDto;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppUserDto {
    private String appUserName;

    private String appUserEmail;

    private String appUserPassword;

    private Set<String> appUserLanguagesSpoken;

    private String appUserCity;

    private String appUserLiveInDistrict;

    private Set<CommuteDto> appUserCommuteDtos;
}
