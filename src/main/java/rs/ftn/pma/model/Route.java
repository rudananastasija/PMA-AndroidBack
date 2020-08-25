package rs.ftn.pma.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Column(name="start_time", columnDefinition = "TIMESTAMP")
    @Getter
    @Setter
    private LocalDateTime startTime;

    @Column(name = "end_time", columnDefinition = "TIMESTAMP")
    @Getter
    @Setter
    private LocalDateTime endTime;

    @Getter
    @Setter
    private Long distance;

    @Getter
    @Setter
    private Double calories;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "route")
    @JsonIgnore
    @Getter
    @Setter
    private Set<Point> points = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Getter
    @Setter
    private User user;
}
