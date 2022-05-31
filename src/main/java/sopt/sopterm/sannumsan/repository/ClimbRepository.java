package sopt.sopterm.sannumsan.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.sopterm.sannumsan.domain.Climb;

public interface ClimbRepository extends JpaRepository<Climb, Long> {

    public List<Climb> findAllByUserId(Long id);

    public Optional<Climb> findByUserIdAndMountainId(Long userId, Long mountainId);
}