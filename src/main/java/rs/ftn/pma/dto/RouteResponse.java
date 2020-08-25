package rs.ftn.pma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {
    private Long id;

    private String startTime;

    private String endTime;

    private HashMap<String, List<Double>> points;

    private Double distance;

    private Double calories;

}
