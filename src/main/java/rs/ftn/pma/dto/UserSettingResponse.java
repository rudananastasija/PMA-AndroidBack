package rs.ftn.pma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingResponse {
    private Long id;

    private String height;

    private String weight;

    private String sex;

    private boolean waterReminder;

    private Long userId;
}
