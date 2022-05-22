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
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "User")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "climbs")
public class User extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String username;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(name = "authentication_code", nullable = false, unique = true)
    private String authenticationCode;

    @Column(nullable = false, length = 10)
    private String provider;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long totalHeight;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long totalLength;

    @OneToMany(mappedBy = "user")
    private List<Climb> climbs;

    @Builder
    public User(
        Long id,
        String username,
        String image,
        String authenticationCode,
        String provider,
        Long totalHeight,
        Long totalLength
    ) {
        if (username == null) {
            throw new RuntimeException("이름은 필수값입니다.");
        }
        if (authenticationCode == null) {
            throw new RuntimeException("인증 코드는 필수값입니다.");
        }
        if (provider == null) {
            throw new RuntimeException("provider는 필수값입니다.");
        }
        if (totalHeight == null) {
            throw new RuntimeException("총 높이는 필수값입니다.");
        }
        if (totalLength == null) {
            throw new RuntimeException("총 길이는 필수값입니다.");
        }
        this.id = id;
        this.username = username;
        this.image = image;
        this.authenticationCode = authenticationCode;
        this.provider = provider;
        this.totalHeight = totalHeight;
        this.totalLength = totalLength;
    }

}
