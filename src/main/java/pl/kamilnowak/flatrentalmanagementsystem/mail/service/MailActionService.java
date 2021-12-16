package pl.kamilnowak.flatrentalmanagementsystem.mail.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Apartment;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.ExtraCost;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.ApartmentService;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.TenantService;
import pl.kamilnowak.flatrentalmanagementsystem.mail.exception.EmailSendException;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.VerificationToken;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.VerificationTokenService;
import pl.kamilnowak.flatrentalmanagementsystem.security.util.TokenGenerationHelper;
import pl.kamilnowak.flatrentalmanagementsystem.util.ConfigInfo;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Log4j2
public class MailActionService {

    private final MailService mailService;
    private final LoginUserService loginUserService;
    private final VerificationTokenService verificationTokenService;
    private final ApartmentService apartmentService;
    private final TokenGenerationHelper tokenGenerationHelper;
    private final ConfigInfo configInfo;

    @Autowired
    public MailActionService(MailService mailService, LoginUserService loginUserService, VerificationTokenService verificationTokenService, ApartmentService apartmentService, TokenGenerationHelper tokenGenerationHelper, ConfigInfo configInfo) {
        this.mailService = mailService;
        this.loginUserService = loginUserService;
        this.verificationTokenService = verificationTokenService;
        this.apartmentService = apartmentService;
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
        LoginUser loginUser = loginUserService.loadUserByUsername(mail);
        if (loginUser.getVerificationToken() != null) {
            verificationTokenService.deleteById(loginUser.getVerificationToken().getId());
        }
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

    public void sendMail(String mail, String title, String text) throws EmailSendException {
        log.debug("send mail");
        try {
            mailService.sendMail(
                    mail,
                    title,
                    text
            );
        } catch (MessagingException e) {
            throw new EmailSendException("problem with sending email");
        }
    }

    public void sendReminderToAllApartmentTenant(Long id) {
        Apartment apartment = apartmentService.getObjectById(id);
        if (apartment != null) {
            apartment.getTenants().stream()
                    .forEach(tenant -> {
                        BigDecimal extraCostSum = BigDecimal.ZERO;
                        for (ExtraCost extraCost: tenant.getExtraCosts()) {
                            extraCostSum = extraCostSum.add(extraCost.getExtraCost());
                        }
                        if (!tenant.isPaid() && tenant.isActive() && tenant.getStartDate().isBefore(LocalDate.now())) {
                            try {
                                sendMail(
                                        tenant.getMail(),
                                        "Reminder",
                                        "your new fee (Basic: " + tenant.getFee() + ") (Extra costs: " + extraCostSum +")" + tenant.getCurrency().getName() + "\n"
                                                + "Start date: " + tenant.getStartDate() + "\n"
                                                + "pay up to the day of the month:" + tenant.getDayToPay()
                                );
                            } catch (EmailSendException e) {
                                throw new RuntimeException("error when mail send");
                            }
                        }
                    });
        }
    }

}
