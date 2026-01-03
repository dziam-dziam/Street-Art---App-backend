package org.example.services.add_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.commute.CommuteDto;
import org.example.dtos.commute.AddCommuteDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommuteService {

    public CommuteDto createCommute(AddCommuteDto addCommuteDto) {
        if (addCommuteDto == null) throw new IllegalArgumentException("AddCommuteDto is null");

        return CommuteDto.builder()
                .commuteDistrictName(addCommuteDto.getCommuteDistrictName())
                .commuteStartHour(addCommuteDto.getCommuteStartHour())
                .commuteStopHour(addCommuteDto.getCommuteStopHour())
                .commuteMeansOfTransport(addCommuteDto.getCommuteMeansOfTransport())
                .commuteTripsPerWeek(addCommuteDto.getCommuteTripsPerWeek())
                .build();
    }
}
