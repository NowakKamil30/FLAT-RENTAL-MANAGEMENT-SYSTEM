package pl.kamilnowak.flatrentalmanagementsystem.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Apartment;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.ExtraCost;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.ApartmentService;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.TenantService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotAuthorizationException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.exception.EmailSendException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.service.MailActionService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;


@RestController
@RequestMapping("/v1/mail")
public class MailController {

    private final MailActionService mailActionService;
    private final TenantService tenantService;
    private final ApartmentService apartmentService;

    @Autowired
    public MailController(MailActionService mailActionService, TenantService tenantService, ApartmentService apartmentService) {
        this.mailActionService = mailActionService;
        this.tenantService = tenantService;
        this.apartmentService = apartmentService;
    }

    @GetMapping("/apartment/{id}")
    public ResponseEntity<Void> sendReminderToAllApartmentTenant(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        Apartment apartment = apartmentService.getApartmentByLoginUserMailAndId(principal.getName(), id);
        if (apartment == null) {
            throw new NotAuthorizationException();
        }
        mailActionService.sendReminderToAllApartmentTenant(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<Void> sendReminderToTenant(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        Tenant tenant = tenantService.getTenantByLoginUserMailAndId(principal.getName(), id);
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
        } else {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok().build();
    }

}
