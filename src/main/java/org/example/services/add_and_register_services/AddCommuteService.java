package org.example.services.add_and_register_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.commute.CommuteDto;
import org.example.dtos.commute.AddCommuteDto;
import org.example.entities.AppUser;
import org.example.exceptions.AppUserNotFoundException;
import org.example.repositories.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommuteService {

    private final AppUserRepository appUserRepository;

    public CommuteDto createCommute(AddCommuteDto addCommuteDto, String appUserEmail) {
        if (addCommuteDto == null) throw new IllegalArgumentException("AddCommuteDto is null");
        AppUser appUserWithCommute = appUserRepository.findByAppUserEmail(appUserEmail)
                .orElseThrow(() -> new AppUserNotFoundException(appUserEmail));


        return CommuteDto.builder()
                .commuteDistrictName(addCommuteDto.getCommuteDistrictName())
                .commuteStartHour(addCommuteDto.getCommuteStartHour())
                .commuteStopHour(addCommuteDto.getCommuteStopHour())
                .commuteMeansOfTransport(addCommuteDto.getCommuteMeansOfTransport())
                .commuteTripsPerWeek(addCommuteDto.getCommuteTripsPerWeek())
                .build();
    }
}
