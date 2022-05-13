package sopt.sopterm.sannumsan.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "Mountain")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "climbs")
public class Mountain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false)
    private Long height;

    @Column(nullable = false)
    private Long length;

    @Column(nullable = false)
    private Long timeUp;

    @Column(nullable = false)
    private Long timeDown;

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @OneToMany(mappedBy = "mountain")
    private List<Climb> climbs;

}
