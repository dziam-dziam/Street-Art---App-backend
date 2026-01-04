package org.example.controllers.auth;

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
    public CommuteDto addCommuteToUser(@RequestBody AddCommuteDto addCommuteDto, @RequestParam String appUserEmail) {
        return addCommuteService.addCommute(addCommuteDto, appUserEmail);
    }

}
