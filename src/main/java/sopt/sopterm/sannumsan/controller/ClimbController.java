package sopt.sopterm.sannumsan.controller;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sopt.sopterm.sannumsan.config.CommonResponse;
import sopt.sopterm.sannumsan.controller.request.ClimbRequest;
import sopt.sopterm.sannumsan.domain.Climb;
import sopt.sopterm.sannumsan.domain.Mountain;
import sopt.sopterm.sannumsan.domain.User;
import sopt.sopterm.sannumsan.dto.ClimbDTO;
import sopt.sopterm.sannumsan.repository.ClimbRepository;
import sopt.sopterm.sannumsan.repository.MountainRepository;
import sopt.sopterm.sannumsan.repository.UserRepository;

@Log
@Controller
@RequestMapping("climb")
@AllArgsConstructor
public class ClimbController {

    ClimbRepository repo;
    MountainRepository mRepo;
    UserRepository uRepo;

    @PostMapping(value = "", produces = "application/json; charset=utf-8")
    @ResponseBody
    public CommonResponse postNewClimb(@RequestBody ClimbRequest climbRequest) {
        Optional<Climb> checkClimb = repo.findByUserIdAndMountainId(climbRequest.getUserId(),
            climbRequest.getMountainId());
        if (checkClimb.isPresent()) {
            return CommonResponse.onFailure(HttpStatus.BAD_REQUEST, "이미 존재하는 등산 기록입니다.");
        }
        Optional<Mountain> mountain = mRepo.findById(climbRequest.getMountainId());
        if (mountain.isEmpty()) {
            return CommonResponse.onFailure(HttpStatus.NOT_FOUND, "해당 산이 없습니다.");
        }
        Optional<User> user = uRepo.findById(climbRequest.getUserId());
        if (user.isEmpty()) {
            return CommonResponse.onFailure(HttpStatus.NOT_FOUND, "해당 유저가 없습니다.");
        }

        Climb climb = Climb.builder()
            .content(climbRequest.getContent())
            .image(climbRequest.getImage())
            .user(user.get())
            .mountain(mountain.get())
            .build();
        repo.save(climb);
        user.ifPresent(origin -> {
            origin.setTotalHeight(origin.getTotalHeight() + mountain.get().getHeight());
            origin.setTotalLength(origin.getTotalLength() + mountain.get().getLength());
            uRepo.save(origin);
        });

        ClimbDTO climbDTO = new ClimbDTO(climb);
        return CommonResponse.onSuccess(climbDTO);
    }
}
