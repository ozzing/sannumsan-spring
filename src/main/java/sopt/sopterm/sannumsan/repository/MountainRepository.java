package sopt.sopterm.sannumsan.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.sopterm.sannumsan.domain.Mountain;

public interface MountainRepository extends JpaRepository<Mountain, Long>,
    MountainCustomRepository {

    public List<Mountain> findAll();

    public List<Mountain> findAllByIdIn(List<Long> idList);

    public Optional<Mountain> findById(Long id);

}