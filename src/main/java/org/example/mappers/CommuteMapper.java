package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.dtos.commute.CommuteDto;
import org.example.entities.AppUser;
import org.example.entities.Commute;
import org.example.entities.District;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
public class CommuteMapper {

    private final DistrictRepository districtRepository;

    public Commute mapCommuteDtoToCommuteEntities(CommuteDto commuteDto, AppUser commutingAppUserEntity) {
        if (commuteDto == null) throw new IllegalArgumentException("CommuteDto is null");
        if (commuteDto.getCommuteThroughDistrictName() == null)
            throw new IllegalArgumentException("CommuteThroughDistrictName id null");

        String commuteThroughDistrictName = commuteDto.getCommuteThroughDistrictName();
        District commuteThroughDistrictEntity = districtRepository.findByDistrictName(commuteThroughDistrictName)
                .orElseThrow(() -> new DistrictNotFoundByNameException(commuteThroughDistrictName));

        return Commute.builder()
                .commuteMeansOfTransport(commuteDto.getCommuteMeansOfTransport())
                .commuteStartHour(commuteDto.getCommuteStartHour())
                .commuteStopHour(commuteDto.getCommuteStopHour())
                .commuteThroughDistrict(commuteThroughDistrictEntity)
                .commuteTripsPerWeek(commuteDto.getCommuteTripsPerWeek())
                .commutingAppUser(commutingAppUserEntity)
                .build();
    }

    public CommuteDto mapCommuteEntityToCommuteDto(Commute commuteEntity) {
        if (commuteEntity == null) throw new IllegalArgumentException("Commute Entity is null");
        if (commuteEntity.getCommuteThroughDistrict() == null)
            throw new IllegalArgumentException("Commute Entity's commute-through district entity is null");

        District commuteEntityCommuteThroughDistrictEntity = commuteEntity.getCommuteThroughDistrict();
        String commuteEntityCommuteThroughDistrictName = commuteEntityCommuteThroughDistrictEntity.getDistrictName();

        AppUser commuteEntityCommutingAppUserEntity = commuteEntity.getCommutingAppUser();
        String commutingAppUserEntityEmail = commuteEntityCommutingAppUserEntity.getAppUserEmail();

        return CommuteDto.builder()
                .commuteThroughDistrictName(commuteEntityCommuteThroughDistrictName)
                .commutingAppUserEmail(commutingAppUserEntityEmail)
                .commuteStartHour(commuteEntity.getCommuteStartHour())
                .commuteStopHour(commuteEntity.getCommuteStopHour())
                .commuteTripsPerWeek(commuteEntity.getCommuteTripsPerWeek())
                .commuteMeansOfTransport(commuteEntity.getCommuteMeansOfTransport())
                .build();
    }
}
