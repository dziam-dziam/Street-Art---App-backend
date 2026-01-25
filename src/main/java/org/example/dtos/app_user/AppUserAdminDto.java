package org.example.dtos.app_user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserAdminDto {
    private Long id;

    private String appUserName;

    private String appUserEmail;
}
