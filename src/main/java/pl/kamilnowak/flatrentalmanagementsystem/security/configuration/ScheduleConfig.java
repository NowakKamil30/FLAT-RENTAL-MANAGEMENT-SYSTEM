package pl.kamilnowak.flatrentalmanagementsystem.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.VerificationTokenService;

import java.time.LocalDateTime;

@EnableScheduling
@Configuration
public class ScheduleConfig {
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public ScheduleConfig(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    @Scheduled(cron = "0 10 5 * * *")
    public void deleteNotValidVerificationToken() {
        verificationTokenService.deleteAllByCreateTimeIsBefore(LocalDateTime.now().minusDays(1));
    }
}
