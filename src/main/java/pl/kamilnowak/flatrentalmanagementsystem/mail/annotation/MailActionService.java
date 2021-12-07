package pl.kamilnowak.flatrentalmanagementsystem.mail.annotation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.mail.exception.EmailSendException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.service.MailService;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.VerificationToken;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.VerificationTokenService;
import pl.kamilnowak.flatrentalmanagementsystem.security.util.TokenGenerationHelper;
import pl.kamilnowak.flatrentalmanagementsystem.util.ConfigInfo;

import javax.mail.MessagingException;

@Service
@Log4j2
public class MailActionService {

    private final MailService mailService;
    private final LoginUserService loginUserService;
    private final VerificationTokenService verificationTokenService;
    private final TokenGenerationHelper tokenGenerationHelper;
    private final ConfigInfo configInfo;

    @Autowired
    public MailActionService(MailService mailService, LoginUserService loginUserService, VerificationTokenService verificationTokenService, TokenGenerationHelper tokenGenerationHelper, ConfigInfo configInfo) {
        this.mailService = mailService;
        this.loginUserService = loginUserService;
        this.verificationTokenService = verificationTokenService;
        this.tokenGenerationHelper = tokenGenerationHelper;
        this.configInfo = configInfo;
    }

    public void sendActivityAccountEmail(LoginUser loginUser) throws EmailSendException {
        log.debug("sendActivityAccountEmail");
        if (loginUser == null) {
            throw new EmailSendException("loginUser is null");
        }
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

    public void sendResetPasswordEmail(String mail) throws EmailSendException {
        log.debug("sendResetPasswordEmail");
        LoginUser loginUser = (LoginUser) loginUserService.loadUserByUsername(mail);
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
