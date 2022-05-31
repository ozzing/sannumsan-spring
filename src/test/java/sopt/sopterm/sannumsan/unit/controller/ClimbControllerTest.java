package sopt.sopterm.sannumsan.unit.controller;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import sopt.sopterm.sannumsan.config.CommonResponse;
import sopt.sopterm.sannumsan.controller.ClimbController;
import sopt.sopterm.sannumsan.controller.request.ClimbRequest;
import sopt.sopterm.sannumsan.domain.Climb;
import sopt.sopterm.sannumsan.domain.Level;
import sopt.sopterm.sannumsan.domain.Mountain;
import sopt.sopterm.sannumsan.domain.User;
import sopt.sopterm.sannumsan.dto.ClimbDTO;
import sopt.sopterm.sannumsan.repository.ClimbRepository;
import sopt.sopterm.sannumsan.repository.MountainRepository;
import sopt.sopterm.sannumsan.repository.UserRepository;

public class ClimbControllerTest {

    @Test
    @DisplayName("등산 기록 등록 시 이미 존재하는 기록일 때, BAD REQUEST 에러 처리")
    public void testIfClimbExistThenThrowBadRequestException() {
        // given
        ClimbRequest climbRequest = ClimbRequest.builder()
            .mountainId(1L)
            .content("후")
            .image("url")
            .userId(1L)
            .build();

        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        Mountain mountain = Mountain.builder()
            .id(1L)
            .name("북한산")
            .image("image")
            .height(111L)
            .length(222L)
            .timeUp(45L)
            .timeDown(30L)
            .level(level)
            .build();

        User user1 = User.builder()
            .id(1L)
            .username("username")
            .authenticationCode("123456")
            .provider("kakao")
            .totalHeight(10L)
            .totalLength(10L)
            .build();

        Climb climb = Climb.builder()
            .content("등산")
            .image("인증")
            .user(user1)
            .mountain(mountain)
            .build();

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);
        Mockito.when(mockClimbRepository.findByUserIdAndMountainId(1L, 1L))
            .thenReturn(Optional.ofNullable(climb));

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L)).thenReturn(Optional.ofNullable(mountain));

        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        // when
        ClimbController climbController = new ClimbController(
            mockClimbRepository,
            mockMountainRepository,
            mockUserRepository
        );
        CommonResponse<ClimbDTO> result = climbController.postNewClimb(climbRequest);

        // then
        Assertions.assertEquals(
            CommonResponse.onFailure(HttpStatus.BAD_REQUEST, "이미 존재하는 등산 기록입니다."),
            result);
    }

    @Test
    @DisplayName("등산 기록 등록 시 산이 존재하지 않을 때, NOT FOUND 에러 처리")
    public void testIfMountainNotExistThenThrowNotFoundException() {
        // given
        ClimbRequest climbRequest = ClimbRequest.builder()
            .mountainId(1L)
            .content("후")
            .image("url")
            .userId(1L)
            .build();

        User user1 = User.builder()
            .id(1L)
            .username("username")
            .authenticationCode("123456")
            .provider("kakao")
            .totalHeight(10L)
            .totalLength(10L)
            .build();

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);
        Mockito.when(mockClimbRepository.findByUserIdAndMountainId(1L, 1L))
            .thenReturn(Optional.ofNullable(null));

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        // when
        ClimbController climbController = new ClimbController(
            mockClimbRepository,
            mockMountainRepository,
            mockUserRepository
        );
        CommonResponse<ClimbDTO> result = climbController.postNewClimb(climbRequest);

        // then
        Assertions.assertEquals(CommonResponse.onFailure(HttpStatus.NOT_FOUND, "해당 산이 없습니다."),
            result);
    }

    @Test
    @DisplayName("등산 기록 등록 시 유저가 존재하지 않을 때, NOT FOUND 에러 처리")
    public void testIfUserNotExistThenThrowNotFoundException() {
        // given
        ClimbRequest climbRequest = ClimbRequest.builder()
            .mountainId(1L)
            .content("후")
            .image("url")
            .userId(1L)
            .build();

        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        Mountain mountain = Mountain.builder()
            .id(1L)
            .name("북한산")
            .image("image")
            .height(111L)
            .length(222L)
            .timeUp(45L)
            .timeDown(30L)
            .level(level)
            .build();

        User user1 = User.builder()
            .id(1L)
            .username("username")
            .authenticationCode("123456")
            .provider("kakao")
            .totalHeight(10L)
            .totalLength(10L)
            .build();

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);
        Mockito.when(mockClimbRepository.findByUserIdAndMountainId(1L, 1L))
            .thenReturn(Optional.ofNullable(null));

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L)).thenReturn(Optional.ofNullable(mountain));

        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        // when
        ClimbController climbController = new ClimbController(
            mockClimbRepository,
            mockMountainRepository,
            mockUserRepository
        );
        CommonResponse<ClimbDTO> result = climbController.postNewClimb(climbRequest);

        // then
        Assertions.assertEquals(CommonResponse.onFailure(HttpStatus.NOT_FOUND, "해당 유저가 없습니다."),
            result);
    }

    @Test
    @DisplayName("등산 기록 등록 성공 시, 등산 기록 반환하는지 확인")
    public void testIfClimbPostSucceedThenReturnClimb() {
        // given
        ClimbRequest climbRequest = ClimbRequest.builder()
            .mountainId(1L)
            .content("후")
            .image("url")
            .userId(1L)
            .build();

        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        Mountain mountain = Mountain.builder()
            .id(1L)
            .name("북한산")
            .image("image")
            .height(111L)
            .length(222L)
            .timeUp(45L)
            .timeDown(30L)
            .level(level)
            .build();

        User user1 = User.builder()
            .id(1L)
            .username("username")
            .authenticationCode("123456")
            .provider("kakao")
            .totalHeight(0L)
            .totalLength(0L)
            .build();

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);
        Mockito.when(mockClimbRepository.findByUserIdAndMountainId(1L, 1L))
            .thenReturn(Optional.ofNullable(null));

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L)).thenReturn(Optional.ofNullable(mountain));

        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        // when
        ClimbController climbController = new ClimbController(
            mockClimbRepository,
            mockMountainRepository,
            mockUserRepository
        );
        CommonResponse<ClimbDTO> result = climbController.postNewClimb(climbRequest);

        // then
        Climb climb = Climb.builder()
            .content(climbRequest.getContent())
            .image(climbRequest.getImage())
            .user(user1)
            .mountain(mountain)
            .build();

        ArgumentCaptor<Climb> captor = ArgumentCaptor.forClass(Climb.class);
        Mockito.verify(mockClimbRepository, Mockito.times(1)).save(captor.capture());

        ArgumentCaptor<User> captor2 = ArgumentCaptor.forClass(User.class);
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(captor2.capture());

        Assertions.assertEquals(climb, captor.getValue());
        Assertions.assertEquals(user1, captor2.getValue());
        Assertions.assertEquals(CommonResponse.onSuccess(new ClimbDTO(climb)), result);
    }

    @Test
    @DisplayName("등산 기록 조회 시 산이 존재하지 않을 때, NOT FOUND 에러 처리")
    public void testIfMountainNotExistWhenFindClimbThenThrowNotFoundException() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("username")
            .authenticationCode("123456")
            .provider("kakao")
            .totalHeight(10L)
            .totalLength(10L)
            .build();

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);
        Mockito.when(mockClimbRepository.findByUserIdAndMountainId(1L, 1L))
            .thenReturn(Optional.ofNullable(null));

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        // when
        ClimbController climbController = new ClimbController(
            mockClimbRepository,
            mockMountainRepository,
            mockUserRepository
        );
        CommonResponse<ClimbDTO> result = climbController.findClimbByMountainId(1L);

        // then
        Assertions.assertEquals(CommonResponse.onFailure(HttpStatus.NOT_FOUND, "해당 산이 없습니다."),
            result);
    }

    @Test
    @DisplayName("등산 기록 등록 시 유저가 존재하지 않을 때, NOT FOUND 에러 처리")
    public void testIfUserNotExistWhenFindClimbThenThrowNotFoundException() {
        // given
        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        Mountain mountain = Mountain.builder()
            .id(1L)
            .name("북한산")
            .image("image")
            .height(111L)
            .length(222L)
            .timeUp(45L)
            .timeDown(30L)
            .level(level)
            .build();

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);
        Mockito.when(mockClimbRepository.findByUserIdAndMountainId(1L, 1L))
            .thenReturn(Optional.ofNullable(null));

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L)).thenReturn(Optional.ofNullable(mountain));

        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        // when
        ClimbController climbController = new ClimbController(
            mockClimbRepository,
            mockMountainRepository,
            mockUserRepository
        );
        CommonResponse<ClimbDTO> result = climbController.findClimbByMountainId(1L);

        // then
        Assertions.assertEquals(CommonResponse.onFailure(HttpStatus.NOT_FOUND, "해당 유저가 없습니다."),
            result);
    }

    @Test
    @DisplayName("등산 기록 조회 시 기록이 존재하지 않을 때, NOT FOUND 에러 처리")
    public void testIfClimbNotExistWhenFindClimbThenThrowNotFoundException() {
        // given
        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        Mountain mountain = Mountain.builder()
            .id(1L)
            .name("북한산")
            .image("image")
            .height(111L)
            .length(222L)
            .timeUp(45L)
            .timeDown(30L)
            .level(level)
            .build();

        User user1 = User.builder()
            .id(1L)
            .username("username")
            .authenticationCode("123456")
            .provider("kakao")
            .totalHeight(10L)
            .totalLength(10L)
            .build();

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);
        Mockito.when(mockClimbRepository.findByUserIdAndMountainId(1L, 1L))
            .thenReturn(Optional.ofNullable(null));

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L)).thenReturn(Optional.ofNullable(mountain));

        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        // when
        ClimbController climbController = new ClimbController(
            mockClimbRepository,
            mockMountainRepository,
            mockUserRepository
        );
        CommonResponse<ClimbDTO> result = climbController.findClimbByMountainId(1L);

        // then
        Assertions.assertEquals(CommonResponse.onFailure(HttpStatus.NOT_FOUND, "존재하지 않는 등산 기록입니다."),
            result);
    }

    @Test
    @DisplayName("등산 기록 조회 시 기록이 존재할 때, 등산 기록 반환하는지 확인")
    public void testIfClimbExistWhenFindClimbThenReturnClimb() {
        // given
        Level level = Level.builder()
            .id(1L)
            .name("상")
            .build();

        Mountain mountain = Mountain.builder()
            .id(1L)
            .name("북한산")
            .image("image")
            .height(111L)
            .length(222L)
            .timeUp(45L)
            .timeDown(30L)
            .level(level)
            .build();

        User user1 = User.builder()
            .id(1L)
            .username("username")
            .authenticationCode("123456")
            .provider("kakao")
            .totalHeight(10L)
            .totalLength(10L)
            .build();

        Climb climb = Climb.builder()
            .content("content")
            .image("image")
            .user(user1)
            .mountain(mountain)
            .build();

        ClimbRepository mockClimbRepository = Mockito.mock(ClimbRepository.class);
        Mockito.when(mockClimbRepository.findByUserIdAndMountainId(1L, 1L))
            .thenReturn(Optional.ofNullable(climb));

        MountainRepository mockMountainRepository = Mockito.mock(MountainRepository.class);
        Mockito.when(mockMountainRepository.findById(1L)).thenReturn(Optional.ofNullable(mountain));

        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        // when
        ClimbController climbController = new ClimbController(
            mockClimbRepository,
            mockMountainRepository,
            mockUserRepository
        );
        CommonResponse<ClimbDTO> result = climbController.findClimbByMountainId(1L);

        // then
        Assertions.assertEquals(CommonResponse.onSuccess(new ClimbDTO(climb)), result);
    }
}
