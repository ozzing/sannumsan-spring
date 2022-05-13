package sopt.sopterm.sannumsan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

//    @GetMapping(produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public List<MountainDTO> findAllMountain() {
//        List<Mountain> mountainList = repo.findAll();
//        List<MountainDTO> mountainDTOList = mountainList.stream()
//            .map(MountainDTO::new)
//            .collect(Collectors.toList());
//        return mountainDTOList;
//    }

    @GetMapping(value = "/carousel", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MountainDTO> findFiveMountain() {
        Long count = repo.count();
        List<Long> idList = new ArrayList<Long>();
        while (idList.size() < 5) {
            Long newId = (long) (Math.random() * count) + 1;
            if (idList.indexOf(newId) == -1) {
                idList.add(newId);
            }
        }
        List<Mountain> mountainList = repo.findAllByIdIn(idList);
        List<MountainDTO> mountainDTOList = mountainList.stream()
            .map(MountainDTO::new)
            .collect(Collectors.toList());
        return mountainDTOList;
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<ClimbMainDTO> findAllMountainAndClimbByUserId(@PathVariable("id") Long id) {
        List<Mountain> mountainList = repo.findAll();
//        List<Climb> climbList = cRepo.findAllByUserId(id);

        List<ClimbMainDTO> resultList = new ArrayList<>();
        mountainList.forEach(m -> {
            List<Climb> climbList = m.getClimbs().stream()
                .filter(c -> c.getUser().getId() == id)
                .collect(Collectors.toList());
            ClimbMainDTO climbMainDTO = new ClimbMainDTO(m, climbList);
            resultList.add(climbMainDTO);
        });

        return resultList;
    }
}
