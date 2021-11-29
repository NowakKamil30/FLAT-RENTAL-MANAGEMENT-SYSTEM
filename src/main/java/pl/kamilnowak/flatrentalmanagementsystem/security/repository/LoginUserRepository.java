package pl.kamilnowak.flatrentalmanagementsystem.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
}
