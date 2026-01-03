package org.example.dtos.commute;

import lombok.*;
import org.example.enums.MeansOfTransport;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommuteDto {
    private String commuteDistrictName;

    private int commuteTripsPerWeek;

    private int commuteStartHour;

    private int commuteStopHour;

    private Set<MeansOfTransport> commuteMeansOfTransport;
}
