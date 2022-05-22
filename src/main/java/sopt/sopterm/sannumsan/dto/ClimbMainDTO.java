package sopt.sopterm.sannumsan.dto;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import sopt.sopterm.sannumsan.domain.Climb;
import sopt.sopterm.sannumsan.domain.Mountain;

@Getter
@ToString
@EqualsAndHashCode
public class ClimbMainDTO {

    private String image;
    private String name;
    private String user;

    public ClimbMainDTO(Mountain mountain, List<Climb> climbList) {
        this.name = mountain.getName();
        if (climbList.isEmpty()) {
            this.image = null;
            this.user = null;
        } else {
            this.image = climbList.stream().findFirst().get().getImage();
            this.user = climbList.stream().findFirst().get().getUser().getUsername();
        }
    }
}
