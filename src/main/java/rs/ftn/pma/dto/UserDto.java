package rs.ftn.pma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Setter
    private String firstname;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter String password;
}
