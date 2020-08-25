package rs.ftn.pma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalRequest {

    private String dateTime;

    private String goalKey;

    private double goalValue;

    private Long userId;

    private double percentage;

    private Double currentValue;

    private int notified;
}
