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
import lombok.Builder;
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

    //
    @Builder
    public Mountain(
        Long id,
        String name,
        String image,
        Long height,
        Long length,
        Long timeUp,
        Long timeDown,
        Level level,
        List<Climb> climbs
    ) {
        if (name == null) {
            throw new RuntimeException("이름은 필수값입니다.");
        }
        if (image == null) {
            throw new RuntimeException("사진은 필수값입니다.");
        }
        if (height == null) {
            throw new RuntimeException("높이는 필수값입니다.");
        }
        if (length == null) {
            throw new RuntimeException("길이는 필수값입니다.");
        }
        if (timeUp == null) {
            throw new RuntimeException("등반 소요시간은 필수값입니다.");
        }
        if (timeDown == null) {
            throw new RuntimeException("하산 소요시간은 필수값입니다.");
        }
        this.id = id;
        this.name = name;
        this.image = image;
        this.height = height;
        this.length = length;
        this.timeUp = timeUp;
        this.timeDown = timeDown;
        this.level = level;
        this.climbs = climbs;
    }
}
