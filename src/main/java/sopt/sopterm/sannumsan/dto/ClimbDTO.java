package sopt.sopterm.sannumsan.dto;

import java.sql.Timestamp;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import sopt.sopterm.sannumsan.domain.Climb;

@Getter
@ToString
@EqualsAndHashCode
public class ClimbDTO {

    private Long id;
    private String name;
    private Long height;
    private Long length;
    private Long timeUp;
    private Long timeDown;
    private String level;
    private String image;
    private String content;
    private Timestamp createdAt;
    private Long userId;

    public ClimbDTO(Climb climb) {
        this.id = climb.getId();
        this.image = climb.getImage();
        this.height = climb.getMountain().getHeight();
        this.length = climb.getMountain().getLength();
        this.timeUp = climb.getMountain().getTimeUp();
        this.timeDown = climb.getMountain().getTimeDown();
        this.level = climb.getMountain().getLevel().getName();
        this.content = climb.getContent();
        this.name = climb.getMountain().getName();
        this.createdAt = climb.getCreatedAt();
        this.userId = climb.getUser().getId();
    }

}
