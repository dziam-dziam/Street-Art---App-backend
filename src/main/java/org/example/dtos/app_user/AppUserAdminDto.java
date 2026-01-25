package org.example.dtos.app_user;

import lombok.*;
import org.example.dtos.commute.CommuteDto;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserAdminDto {
    private Long id;

    private String appUserName;

    private String appUserEmail;
}
