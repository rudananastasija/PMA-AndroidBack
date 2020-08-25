package rs.ftn.pma.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ftn.pma.enums.Sex;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double height;

    private double weight;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private boolean waterReminder;

    @OneToOne(mappedBy = "settings", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonIgnore
    private User user;

}
