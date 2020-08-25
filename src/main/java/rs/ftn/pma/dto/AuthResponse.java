package rs.ftn.pma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    @Getter
    @Setter
    private String accessToken;

}
