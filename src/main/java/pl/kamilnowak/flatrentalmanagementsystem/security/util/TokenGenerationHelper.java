package pl.kamilnowak.flatrentalmanagementsystem.security.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.VerificationToken;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.VerificationTokenService;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Log4j2
public class TokenGenerationHelper {

    private final VerificationTokenService verificationTokenService;

    @Autowired
    public TokenGenerationHelper(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    public VerificationToken generateVerificationToken(LoginUser loginUser) {
        String token = "";
        do {
            token = UUID.randomUUID().toString();
        } while(verificationTokenService.getVerificationToken(token) != null);

        return VerificationToken.builder()
                .token(token)
                .loginUser(loginUser)
                .createTime(LocalDateTime.now())
                .build();
    }
}
