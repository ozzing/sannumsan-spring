package sopt.sopterm.sannumsan.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Climb")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Climb extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "mountain_id", nullable = false)
    private Mountain mountain;

    @Builder
    public Climb(
        Long id,
        String content,
        String image,
        User user,
        Mountain mountain
    ) {
        if (content == null) {
            throw new RuntimeException("내용은 필수값입니다.");
        }
        if (image == null) {
            throw new RuntimeException("사진은 필수값입니다.");
        }
        if (user == null) {
            throw new RuntimeException("유저는 필수값입니다.");
        }
        if (mountain == null) {
            throw new RuntimeException("산은 필수값입니다.");
        }
        this.id = id;
        this.content = content;
        this.image = image;
        this.user = user;
        this.mountain = mountain;
    }
}
