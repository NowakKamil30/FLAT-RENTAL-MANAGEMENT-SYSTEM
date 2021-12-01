package pl.kamilnowak.flatrentalmanagementsystem.mail;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.VerificationTokenService;

@Aspect
@Component
@Log4j2
public class MailAspect {

    private final MailService mailService;
    private final LoginUserService loginUserService;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public MailAspect(MailService mailService, LoginUserService loginUserService, VerificationTokenService verificationTokenService) {
        this.mailService = mailService;
        this.loginUserService = loginUserService;
        this.verificationTokenService = verificationTokenService;
    }
}
