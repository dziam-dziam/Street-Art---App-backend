package org.example.dtos.commute;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotBlank(message = "Commute district is required")
    private String commuteThroughDistrictName;

    @Min(value = 1, message = "Trips per week must be at least 1")
    @Max(value = 99, message = "Trips per week must be less than 100")
    private int commuteTripsPerWeek;

    @Min(value = 0, message = "Start hour must be between 0 and 23")
    @Max(value = 23, message = "Start hour must be between 0 and 23")
    private int commuteStartHour;

    @Min(value = 0, message = "Stop hour must be between 0 and 23")
    @Max(value = 23, message = "Stop hour must be between 0 and 23")
    private int commuteStopHour;

    @NotEmpty(message = "Select at least one means of transport")
    private Set<MeansOfTransport> commuteMeansOfTransport;
}
