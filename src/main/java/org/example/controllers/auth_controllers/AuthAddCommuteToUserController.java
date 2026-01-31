package org.example.controllers.auth_controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.dtos.commute.AddCommuteDto;
import org.example.dtos.commute.CommuteDto;
import org.example.services.add_and_register_services.AddCommuteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthAddCommuteToUserController {

    private final AddCommuteService addCommuteService;

    @PostMapping("/addCommute")
    public CommuteDto addCommuteToUser(
            @Valid @RequestBody AddCommuteDto addCommuteDto,
            @RequestParam
            @NotBlank(message = "appUserEmail is required")
            @Email(message = "appUserEmail must be a valid email")
            String appUserEmail
    ) {
        return addCommuteService.addCommute(addCommuteDto, appUserEmail);
    }

}
