package pl.kamilnowak.flatrentalmanagementsystem.mail.annotation;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.kamilnowak.flatrentalmanagementsystem.mail.exception.EmailSendException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.service.MailService;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.VerificationToken;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.VerificationTokenService;
import pl.kamilnowak.flatrentalmanagementsystem.security.util.TokenGenerationHelper;
import pl.kamilnowak.flatrentalmanagementsystem.util.ConfigInfo;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@Log4j2
public class MailAspect {

    private final MailService mailService;
    private final LoginUserService loginUserService;
    private final VerificationTokenService verificationTokenService;
    private final TokenGenerationHelper tokenGenerationHelper;
    private final ConfigInfo configInfo;

    @Autowired
    public MailAspect(MailService mailService, LoginUserService loginUserService, VerificationTokenService verificationTokenService, TokenGenerationHelper tokenGenerationHelper, ConfigInfo configInfo) {
        this.mailService = mailService;
        this.loginUserService = loginUserService;
        this.verificationTokenService = verificationTokenService;
        this.tokenGenerationHelper = tokenGenerationHelper;
        this.configInfo = configInfo;
    }

    @AfterReturning(pointcut = "@annotation(SendActiveAccountMail)", returning = "result")
    public void sendActivityAccountEmail(JoinPoint joinPoint, ResponseEntity<Void> result) {
        log.debug("sendActivityAccountEmail");
        if (joinPoint.getArgs()[0] instanceof LoginUser && result.getStatusCode() == HttpStatus.OK) {
            LoginUser loginUser = (LoginUser) joinPoint.getArgs()[0];
            VerificationToken verificationToken = tokenGenerationHelper.generateVerificationToken(loginUser);
            verificationToken = verificationTokenService.createObject(verificationToken);
            try {
                mailService.sendMail(
                        loginUser.getMail(),
                        "Verification token",
                        configInfo.getPageURL() + "/" + configInfo.getVerificationAccount() + "?token=" + verificationToken.getToken());
            } catch (MessagingException e) {
                loginUserService.deleteById(loginUser.getId());
                verificationTokenService.deleteById(verificationTokenService
                        .getVerificationToken(verificationToken.getToken()).getId());
                throw new EmailSendException("problem with sending email");
            }
        }
    }

    @AfterReturning(pointcut = "@annotation(SendResetPasswordMail)", returning = "result")
    public void sendResetPasswordEmail(JoinPoint joinPoint, ResponseEntity<Map<String,Boolean>> result) {
        log.debug("sendResetPasswordEmail");
        if (joinPoint.getArgs()[0] instanceof Map && Objects.requireNonNull(result.getBody()).get("isSendEmail")) {
            Map<String, String> args = (Map) joinPoint.getArgs()[0];
            String email = args.get("email");
            LoginUser loginUser = (LoginUser) loginUserService.loadUserByUsername(email);
            verificationTokenService.deleteById(loginUser.getId());
            VerificationToken verificationToken = tokenGenerationHelper.generateVerificationToken(loginUser);
            verificationToken = verificationTokenService.createObject(verificationToken);
            try {
                mailService.sendMail(
                        loginUser.getMail(),
                        "change password",
                        configInfo.getPageURL() + "/" + configInfo.getChangePassword() + "?token=" + verificationToken.getToken());
            } catch (MessagingException e) {
                verificationTokenService.deleteById(verificationToken.getId());
                throw new EmailSendException("problem with sending email");
            }
        }
    }
}
