package sopt.sopterm.sannumsan.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import sopt.sopterm.sannumsan.domain.Mountain;

@Getter
@ToString
@EqualsAndHashCode
public class MountainDTO {

    private String name;
    private String image;
    private Long height;
    private Long length;
    private Long timeUp;
    private Long timeDown;
    private String level;

    public MountainDTO(Mountain mountain) {
        this.name = mountain.getName();
        this.image = mountain.getImage();
        this.height = mountain.getHeight();
        this.length = mountain.getLength();
        this.timeUp = mountain.getTimeUp();
        this.timeDown = mountain.getTimeDown();
        this.level = mountain.getLevel().getName();
    }
}
