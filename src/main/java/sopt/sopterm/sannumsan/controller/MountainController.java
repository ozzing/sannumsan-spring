package sopt.sopterm.sannumsan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sopt.sopterm.sannumsan.domain.Climb;
import sopt.sopterm.sannumsan.domain.Mountain;
import sopt.sopterm.sannumsan.dto.ClimbMainDTO;
import sopt.sopterm.sannumsan.dto.MountainDTO;
import sopt.sopterm.sannumsan.repository.ClimbRepository;
import sopt.sopterm.sannumsan.repository.MountainRepository;

@Log
@Controller
@RequestMapping("mountain")
@AllArgsConstructor
public class MountainController {

    MountainRepository repo;
    ClimbRepository cRepo;

    @GetMapping(value = "/carousel", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MountainDTO> findFiveMountain() {
        Long count = repo.count();
        List<Long> idList = repo.getIdList(count);
        List<Mountain> mountainList = repo.findAllByIdIn(idList);
        List<MountainDTO> mountainDTOList = mountainList.stream()
            .map(MountainDTO::new)
            .collect(Collectors.toList());
        return mountainDTOList;
    }

    @GetMapping(value = "/", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<ClimbMainDTO> findAllMountainAndClimbByUserId(@RequestParam("userId") Long userId) {
        List<Mountain> mountainList = repo.findAll();

        List<ClimbMainDTO> resultList = new ArrayList<>();
        mountainList.forEach(m -> {
            List<Climb> climbList = m.getClimbs().stream()
                .filter(c -> c.getUser().getId() == userId)
                .collect(Collectors.toList());
            ClimbMainDTO climbMainDTO = new ClimbMainDTO(m, climbList);
            resultList.add(climbMainDTO);
        });

        return resultList;
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public MountainDTO findMountainById(@PathVariable("id") Long id) {
        Optional<Mountain> mountain = repo.findById(id);
        if (mountain.isEmpty()) {
            throw new RuntimeException();
        }
        MountainDTO mountainDTO = new MountainDTO(mountain.get());
        return mountainDTO;
    }
}
