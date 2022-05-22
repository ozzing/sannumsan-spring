package sopt.sopterm.sannumsan.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Level")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "mountains")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "level")
    private List<Mountain> mountains;

    @Builder
    public Level(
        Long id,
        String name
    ) {
        if (name == null) {
            throw new RuntimeException("이름은 필수값입니다.");
        }
        this.id = id;
        this.name = name;
    }
}
