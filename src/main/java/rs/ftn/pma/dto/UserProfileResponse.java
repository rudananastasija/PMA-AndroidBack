package rs.ftn.pma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ftn.pma.enums.Sex;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private double height;
    private double weight;
    @Enumerated(EnumType.STRING)
    private Sex gender;
    private boolean waterReminder;

}
