package sopt.sopterm.sannumsan.controller.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class ClimbRequest {

    Long mountainId;
    String content;
    String image;
    Long userId;

    @Builder
    public ClimbRequest(
        Long mountainId,
        String content,
        String image,
        Long userId
    ) {
        if (mountainId == null) {
            throw new RuntimeException("산 아이디는 필수값입니다.");
        }
        if (image == null) {
            throw new RuntimeException("사진은 필수값입니다.");
        }
        if (content == null) {
            throw new RuntimeException("내용은 필수값입니다.");
        }
        if (userId == null) {
            throw new RuntimeException("유저 아이디는 필수값입니다.");
        }
        this.mountainId = mountainId;
        this.image = image;
        this.content = content;
        this.userId = userId;
    }
}
