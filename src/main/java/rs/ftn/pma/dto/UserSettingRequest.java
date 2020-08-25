package rs.ftn.pma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingRequest {

    private String height;

    private String weight;

    private String sex;

    private boolean waterReminder;

    private Long userId;
}
