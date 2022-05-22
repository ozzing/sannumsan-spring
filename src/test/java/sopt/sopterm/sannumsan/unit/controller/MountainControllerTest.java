package sopt.sopterm.sannumsan.unit.controller;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sopt.sopterm.sannumsan.controller.MountainController;
import sopt.sopterm.sannumsan.domain.Climb;
import sopt.sopterm.sannumsan.domain.Level;
import sopt.sopterm.sannumsan.domain.Mountain;
import sopt.sopterm.sannumsan.domain.User;
import sopt.sopterm.sannumsan.dto.ClimbMainDTO;
import sopt.sopterm.sannumsan.repository.ClimbRepository;
import sopt.sopterm.sannumsan.repository.MountainRepository;

public class MountainControllerTest {

    @Test
    @DisplayName("산 리스트 조회 결과가 없을 때, 빈 배열로 나오는지 확인")
    public void testIfListNotExistThenReturnEmptyList() {
        // given
        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findAll())
            .thenReturn(new ArrayList<>());

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);

        // when
        MountainController mountainController = new MountainController(
            mockMountainRepository,
            mockClimbRepository
        );
        List<ClimbMainDTO> result = mountainController.findAllMountainAndClimbByUserId(1L);

        // then
        Assertions.assertEquals(new ArrayList<>(), result);
    }

    @Test
    @DisplayName("산 리스트 조회 결과 인증되지 않은 산 있을 때, 리스트가 나오는지 확인")
    public void testIfListWithoutCertificationExistReturnMountainList() {
        // given
        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        List<Climb> climbList = new ArrayList<>();
        Mountain mountain = Mountain.builder()
            .id(1L)
            .name("북한산")
            .image("image")
            .height(111L)
            .length(222L)
            .timeUp(45L)
            .timeDown(30L)
            .level(level)
            .climbs(climbList)
            .build();
        List<Mountain> mountainList = new ArrayList<Mountain>();
        mountainList.add(mountain);

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findAll())
            .thenReturn(mountainList);

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);

        // when
        MountainController mountainController = new MountainController(
            mockMountainRepository,
            mockClimbRepository
        );
        List<ClimbMainDTO> result = mountainController.findAllMountainAndClimbByUserId(1L);

        // then
        ClimbMainDTO climbMainDTO = new ClimbMainDTO(mountain, climbList);
        List<ClimbMainDTO> climbMainDTOList = new ArrayList<ClimbMainDTO>();
        climbMainDTOList.add(climbMainDTO);
        Assertions.assertEquals(climbMainDTOList, result);
    }

    @Test
    @DisplayName("산 리스트 조회 결과 인증된 산 있을 때, 리스트가 나오는지 확인")
    public void testIfListWithCertificationExistReturnMountainList() {
        // given
        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        User user1 = User.builder()
            .id(1L)
            .username("username")
            .authenticationCode("123456")
            .provider("kakao")
            .totalHeight(10L)
            .totalLength(10L)
            .build();

        List<Climb> climbList = new ArrayList<>();
        Mountain mountain = Mountain.builder()
            .id(1L)
            .name("북한산")
            .image("image")
            .height(111L)
            .length(222L)
            .timeUp(45L)
            .timeDown(30L)
            .level(level)
            .climbs(climbList)
            .build();

        Climb climb = Climb.builder()
            .id(1L)
            .content("정복!!")
            .image("image")
            .user(user1)
            .mountain(mountain)
            .build();
        climbList.add(climb);

        List<Mountain> mountainList = new ArrayList<Mountain>();
        mountainList.add(mountain);

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findAll())
            .thenReturn(mountainList);

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);

        // when
        MountainController mountainController = new MountainController(
            mockMountainRepository,
            mockClimbRepository
        );
        List<ClimbMainDTO> result = mountainController.findAllMountainAndClimbByUserId(1L);

        // then
        ClimbMainDTO climbMainDTO = new ClimbMainDTO(mountain, climbList);
        List<ClimbMainDTO> climbMainDTOList = new ArrayList<ClimbMainDTO>();
        climbMainDTOList.add(climbMainDTO);
        Assertions.assertEquals(climbMainDTOList, result);
    }
}
