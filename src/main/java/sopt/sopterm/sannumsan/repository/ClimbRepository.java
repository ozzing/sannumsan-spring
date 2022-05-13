package sopt.sopterm.sannumsan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.sopterm.sannumsan.domain.Climb;

public interface ClimbRepository extends JpaRepository<Climb, Long> {

//    @Query(value = "SELECT M.* FROM mountain as M LEFT OUTER JOIN (SELECT * FROM climb c WHERE user_id=1) as C ON M.id=C.mountain_id", nativeQuery = true)
//    public List<Mountain> findAllMountainAndClimbByUserId(Long id);

    public List<Climb> findAllByUserId(Long id);
}