package pl.kamilnowak.flatrentalmanagementsystem.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.VerificationToken;

import java.time.LocalDateTime;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findVerificationTokenByToken(String token);
    void deleteAllByCreateTimeIsBefore(LocalDateTime localDateTime);
}
