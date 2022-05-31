package sopt.sopterm.sannumsan.unit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import sopt.sopterm.sannumsan.config.CommonResponse;
import sopt.sopterm.sannumsan.controller.MountainController;
import sopt.sopterm.sannumsan.domain.Climb;
import sopt.sopterm.sannumsan.domain.Level;
import sopt.sopterm.sannumsan.domain.Mountain;
import sopt.sopterm.sannumsan.domain.User;
import sopt.sopterm.sannumsan.dto.ClimbMainDTO;
import sopt.sopterm.sannumsan.dto.MountainDTO;
import sopt.sopterm.sannumsan.repository.ClimbRepository;
import sopt.sopterm.sannumsan.repository.MountainRepository;

public class MountainControllerTest {

    @Test
    @DisplayName("산 캐러셀 조회 결과가 없을 때, 빈 배열로 나오는지 확인")
    public void testIfCarouselNotExistThenReturnEmptyList() {
        // given
        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.count())
            .thenReturn(15L);
        Long count = mockMountainRepository.count();
        List<Long> idList = new ArrayList<Long>();
        while (idList.size() < 5) {
            Long newId = (long) (Math.random() * count) + 1;
            if (idList.indexOf(newId) == -1) {
                idList.add(newId);
            }
        }

        Mockito.when(mockMountainRepository.count())
            .thenReturn(15L);
        Mockito.when(mockMountainRepository.getIdList(count))
            .thenReturn(idList);
        Mockito.when(mockMountainRepository.findAllByIdIn(idList))
            .thenReturn(new ArrayList<>());

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);

        // when
        MountainController mountainController = new MountainController(
            mockMountainRepository,
            mockClimbRepository
        );
        CommonResponse<List<MountainDTO>> result = mountainController.findFiveMountain();
        // then
        Assertions.assertEquals(CommonResponse.onSuccess(new ArrayList<>()), result);
    }

    @Test
    @DisplayName("산 캐러셀 조회 결과가 있을 때, 배열이 나오는지 확인")
    public void testIfCarouselExistThenReturnList() {
        // given
        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        List<Climb> climbList = new ArrayList<>();

        List<Mountain> mountainList = new ArrayList<Mountain>();
        for (Long i = Long.valueOf(0); i < 15; i++) {
            Mountain mountain = Mountain.builder()
                .id(i)
                .name("북한산")
                .image("image")
                .height(111L)
                .length(222L)
                .timeUp(45L)
                .timeDown(30L)
                .level(level)
                .climbs(climbList)
                .build();
            mountainList.add(mountain);
        }

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.count())
            .thenReturn(15L);
        Long count = mockMountainRepository.count();
        List<Long> idList = new ArrayList<Long>();
        while (idList.size() < 5) {
            Long newId = (long) (Math.random() * count) + 1;
            if (idList.indexOf(newId) == -1) {
                idList.add(newId);
            }
        }
        Mockito.when(mockMountainRepository.getIdList(count))
            .thenReturn(idList);
        Mockito.when(mockMountainRepository.findAllByIdIn(idList))
            .thenReturn(mountainList);

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);

        // when
        MountainController mountainController = new MountainController(
            mockMountainRepository,
            mockClimbRepository
        );
        CommonResponse<List<MountainDTO>> result = mountainController.findFiveMountain();

        // then
        List<MountainDTO> mountainDTOList = mountainList.stream()
            .map(MountainDTO::new)
            .collect(Collectors.toList());
        Assertions.assertEquals(CommonResponse.onSuccess(mountainDTOList), result);
    }

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
        CommonResponse<List<ClimbMainDTO>> result = mountainController.findAllMountainAndClimbByUserId(
            1L);

        // then
        Assertions.assertEquals(CommonResponse.onSuccess(new ArrayList<>()), result);
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
        CommonResponse<List<ClimbMainDTO>> result = mountainController.findAllMountainAndClimbByUserId(
            1L);

        // then
        ClimbMainDTO climbMainDTO = new ClimbMainDTO(mountain, climbList);
        List<ClimbMainDTO> climbMainDTOList = new ArrayList<ClimbMainDTO>();
        climbMainDTOList.add(climbMainDTO);
        Assertions.assertEquals(CommonResponse.onSuccess(climbMainDTOList), result);
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
        CommonResponse<List<ClimbMainDTO>> result = mountainController.findAllMountainAndClimbByUserId(
            1L);

        // then
        ClimbMainDTO climbMainDTO = new ClimbMainDTO(mountain, climbList);
        List<ClimbMainDTO> climbMainDTOList = new ArrayList<ClimbMainDTO>();
        climbMainDTOList.add(climbMainDTO);
        Assertions.assertEquals(CommonResponse.onSuccess(climbMainDTOList), result);
    }

    @Test
    @DisplayName("산 번호로 조회 결과 존재하지 않을 때, NOT FOUND 에러 처리")
    public void testIfMountainNotExistThenThrowNotFoundException() {
        // given
        Mountain mountain = null;
        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L))
            .thenReturn(Optional.ofNullable(mountain));

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);

        // when
        MountainController mountainController = new MountainController(
            mockMountainRepository,
            mockClimbRepository
        );
        CommonResponse result = mountainController.findMountainById(1L);

        // then
        Assertions.assertEquals(CommonResponse.onFailure(HttpStatus.NOT_FOUND, "존재하지 않는 산입니다."),
            result);
    }

    @Test
    @DisplayName("산 번호로 조회 결과 존재할 때, 산 정보 나오는지 확인")
    public void testIfMountainExistThenReturnMountain() {
        // given
        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        Mountain mountain = Mountain.builder()
            .id(1L)
            .name("산")
            .image("url")
            .height(123L)
            .length(456L)
            .timeUp(40L)
            .timeDown(20L)
            .level(level)
            .build();

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L))
            .thenReturn(Optional.ofNullable(mountain));

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);

        // when
        MountainController mountainController = new MountainController(
            mockMountainRepository,
            mockClimbRepository
        );
        CommonResponse result = mountainController.findMountainById(1L);

        // then
        MountainDTO mountainDTO = new MountainDTO(mountain);
        Assertions.assertEquals(CommonResponse.onSuccess(mountainDTO), result);
    }

}
