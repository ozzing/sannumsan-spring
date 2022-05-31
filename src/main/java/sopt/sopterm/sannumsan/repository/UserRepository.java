package sopt.sopterm.sannumsan.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.sopterm.sannumsan.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findById(Long id);
}
