package pl.kamilnowak.flatrentalmanagementsystem.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
