package org.example.dtos.commute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.MeansOfTransport;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommuteDto {

    private String commuteDistrictName;

    private int commuteTripsPerWeek;

    private int commuteStartHour;

    private int commuteStopHour;

    private Set<MeansOfTransport> commuteMeansOfTransport;
}
