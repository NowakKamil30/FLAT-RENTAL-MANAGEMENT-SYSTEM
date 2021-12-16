package pl.kamilnowak.flatrentalmanagementsystem.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Currency;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.ExtraCost;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.TenantService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.exception.EmailSendException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.service.MailActionService;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.VerificationTokenService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Configuration
public class ScheduleConfig {
    private final VerificationTokenService verificationTokenService;
    private final TenantService tenantService;
    private final MailActionService mailActionService;

    @Autowired
    public ScheduleConfig(VerificationTokenService verificationTokenService, TenantService tenantService, MailActionService mailActionService) {
        this.verificationTokenService = verificationTokenService;
        this.tenantService = tenantService;
        this.mailActionService = mailActionService;
    }

    @Scheduled(cron = "0 10 5 * * *")
    public void deleteNotValidVerificationToken() {
        verificationTokenService.deleteAllByCreateTimeIsBefore(LocalDateTime.now().minusDays(1));
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void checkIsPaid() {
        tenantService.checkIsPaidAll();
        int numberOfPages = tenantService.getAllObject(1).getTotalPages();
        for (int i = 1; i < numberOfPages; i++) {
            tenantService.getAllObject(i)
                    .forEach(tenant -> {
                        if (!tenant.isPaid() && tenant.isActive()) {
                            BigDecimal extraCostSum = BigDecimal.ZERO;
                            for (ExtraCost extraCost: tenant.getExtraCosts()) {
                                extraCostSum = extraCostSum.add(extraCost.getExtraCost());
                            }
                            try {
                                mailActionService.sendMail(
                                        tenant.getMail(),
                                        "Welcome(change fee value)" + tenant.getFirstName() + "!",
                                        "your new fee (Basic: " + tenant.getFee() + ") (Extra costs: " + extraCostSum +")" + tenant.getCurrency().getName() + "\n"
                                                + "Start date: " + tenant.getStartDate() + "\n"
                                                + "pay up to the day of the month:" + tenant.getDayToPay()
                                );
                            } catch (EmailSendException e) {
                                throw new NotFoundException("");
                            }
                        }
                    });
        }
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void checkEndDate() {
        tenantService.checkIsEndDate();
    }
}
