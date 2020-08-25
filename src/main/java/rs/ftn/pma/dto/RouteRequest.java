package rs.ftn.pma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RouteRequest {
    private String startTime;

    private String endTime;

    private HashMap<String, List<Double>> points;

    private Double distance;

    private Double calories;

}
