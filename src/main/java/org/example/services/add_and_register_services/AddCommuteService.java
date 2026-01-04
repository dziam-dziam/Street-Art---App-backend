package org.example.services.add_and_register_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.commute.CommuteDto;
import org.example.dtos.commute.AddCommuteDto;
import org.example.entities.AppUser;
import org.example.entities.Commute;
import org.example.exceptions.AppUserNotFoundByEmailException;
import org.example.mappers.CommuteMapper;
import org.example.repositories.AppUserRepository;
import org.example.repositories.CommuteRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommuteService {

    private final AppUserRepository appUserRepository;
    private final CommuteMapper commuteMapper;
    private final CommuteRepository commuteRepository;

    public CommuteDto addCommute(AddCommuteDto addCommuteDto, String commutingAppUserEmail) {
        if (addCommuteDto == null) throw new IllegalArgumentException("AddCommuteDto is null");
        AppUser appUserWithCommute = appUserRepository.findByAppUserEmail(commutingAppUserEmail)
                .orElseThrow(() -> new AppUserNotFoundByEmailException(commutingAppUserEmail));

        CommuteDto commuteDto = CommuteDto.builder()
                .commuteThroughDistrictName(addCommuteDto.getCommuteThroughDistrictName())
                .commutingAppUserEmail(commutingAppUserEmail)
                .commuteStartHour(addCommuteDto.getCommuteStartHour())
                .commuteStopHour(addCommuteDto.getCommuteStopHour())
                .commuteMeansOfTransport(addCommuteDto.getCommuteMeansOfTransport())
                .commuteTripsPerWeek(addCommuteDto.getCommuteTripsPerWeek())
                .build();

        Commute commuteEntity = commuteMapper.mapCommuteDtoToCommuteEntities(commuteDto, appUserWithCommute);

        appUserWithCommute.addCommute(commuteEntity);

        Commute saved = commuteRepository.save(commuteEntity);
        return commuteMapper.mapCommuteEntityToCommuteDto(saved);
    }
}
