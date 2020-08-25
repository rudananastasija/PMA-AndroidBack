package rs.ftn.pma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ftn.pma.enums.GOAL_KEYS;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goals {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="local_date_time", columnDefinition = "TIMESTAMP")
    private LocalDate localDateTime;

    @Enumerated(value = EnumType.STRING)
    private GOAL_KEYS goalKey;

    private double goalValue;

    private int notified;

    @Column(nullable = true)
    private Double currentValue;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private User user;



}
