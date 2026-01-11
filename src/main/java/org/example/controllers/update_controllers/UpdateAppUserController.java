package org.example.controllers.update_controllers;


import lombok.RequiredArgsConstructor;
import org.example.dtos.user.AppUserDto;
import org.example.dtos.user.UpdateAppUserDto;
import org.example.services.update_services.UpdateAppUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("updateAppUser")
public class UpdateAppUserController {

    private final UpdateAppUserService updateAppUserService;

    @PutMapping("/user")
    public AppUserDto updateAppUser(@RequestBody UpdateAppUserDto updateAppUserDto, @RequestParam String appUserEmail){
        return updateAppUserService.updateAppUserByEmail(updateAppUserDto, appUserEmail);
    }
}
