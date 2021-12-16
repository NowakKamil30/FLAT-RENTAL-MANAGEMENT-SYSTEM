package pl.kamilnowak.flatrentalmanagementsystem.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.ExtraCost;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.TenantService;
import pl.kamilnowak.flatrentalmanagementsystem.mail.exception.EmailSendException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.service.MailActionService;

import java.math.BigDecimal;
import java.time.LocalDate;


@RestController
@RequestMapping("/v1/mail")
public class MailController {

    private final MailActionService mailActionService;
    private final TenantService tenantService;

    @Autowired
    public MailController(MailActionService mailActionService, TenantService tenantService) {
        this.mailActionService = mailActionService;
        this.tenantService = tenantService;
    }

    @GetMapping("/apartment/{id}")
    public ResponseEntity<Void> sendReminderToAllApartmentTenant(@PathVariable Long id) {
        mailActionService.sendReminderToAllApartmentTenant(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<Void> sendReminderToTenant(@PathVariable Long id) {
        Tenant tenant = tenantService.getObjectById(id);
        if (tenant != null) {
            BigDecimal extraCostSum = BigDecimal.ZERO;
            for (ExtraCost extraCost : tenant.getExtraCosts()) {
                extraCostSum = extraCostSum.add(extraCost.getExtraCost());
            }
            if (!tenant.isPaid() && tenant.isActive() && tenant.getStartDate().isBefore(LocalDate.now())) {
                try {
                    mailActionService.sendMail(
                            tenant.getMail(),
                            "Reminder",
                            "your new fee (Basic: " + tenant.getFee() + ") (Extra costs: " + extraCostSum + ")" + tenant.getCurrency().getName() + "\n"
                                    + "Start date: " + tenant.getStartDate() + "\n"
                                    + "pay up to the day of the month:" + tenant.getDayToPay()
                    );
                } catch (EmailSendException e) {
                    throw new RuntimeException("error when mail send");
                }
            }
        }
        return ResponseEntity.ok().build();
    }

}
