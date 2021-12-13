package pl.kamilnowak.flatrentalmanagementsystem.apartment.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Currency;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.ExtraCost;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.repository.TenantRepository;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.exception.EmailSendException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.service.MailActionService;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Log4j2
public class TenantService implements CRUDOperation<Tenant, Long> {

    private final TenantRepository tenantRepository;
    private final PageableHelper pageableHelper;
    private final MailActionService mailActionService;
    private final CurrencyService currencyService;
    private final ExtraCostService extraCostService;
    private final DocumentService documentService;

    @Autowired
    public TenantService(TenantRepository tenantRepository, PageableHelper pageableHelper, MailActionService mailActionService, CurrencyService currencyService, ExtraCostService extraCostService, DocumentService documentService) {
        this.tenantRepository = tenantRepository;
        this.pageableHelper = pageableHelper;
        this.mailActionService = mailActionService;
        this.currencyService = currencyService;
        this.extraCostService = extraCostService;
        this.documentService = documentService;
    }

    @Override
    @Transactional
    public Tenant createObject(Tenant tenant) {
        log.debug("create tenant");
        tenant.setId(null);
        tenant.setActive(true);
        tenant.setPaid(false);
        tenant.getDocuments()
                .stream()
                .forEach(document -> document.setTenant(tenant));
        tenant.getExtraCosts()
                .stream()
                .forEach(extraCost -> {
                    extraCost.setTenant(tenant);
                    extraCost.setExtraCost(extraCost.getExtraCost().setScale(2, RoundingMode.HALF_UP));
                });
        tenant.setFee(tenant.getFee().setScale(2, RoundingMode.HALF_UP));
        Tenant tenantSaved = tenantRepository.save(tenant);
        BigDecimal extraCostSum = BigDecimal.ZERO;
        for (ExtraCost extraCost: tenant.getExtraCosts()) {
            extraCostSum = extraCostSum.add(extraCost.getExtraCost());
        }
        Currency currency = currencyService.getObjectById(tenantSaved.getCurrency().getId());
        try {
            mailActionService.sendMail(
                    tenant.getMail(),
                    "welcome " + tenant.getFirstName() + "!",
                    " Your fee (Basic: " + tenant.getFee() + ") (Extra costs: " + extraCostSum +")" + currency.getName()
            );
        } catch (EmailSendException e) {
            throw new NotFoundException("");
        }
        return tenantSaved;
    }

    @Override
    public void deleteById(Long aLong) {
        log.debug("delete tenant id: " + aLong);
        tenantRepository.deleteById(aLong);
    }

    @Override
    public Tenant getObjectById(Long aLong) {
        log.debug("read tenant id: " + aLong);
        return tenantRepository.getById(aLong);
    }

    @Override
    public Page<Tenant> getAllObject(int page) {
        log.debug("read tenants page: " + page);
        return tenantRepository.findAll(pageableHelper.countPageable(page));
    }

    @Override
    @Transactional
    public Tenant updateObject(Tenant tenant, Long aLong) {
        log.debug("update tenant id: " + aLong);
        if(tenantRepository.findById(aLong).isEmpty()) {
            return createObject(tenant);
        }
        documentService.deleteAllByTenantId(aLong);
        extraCostService.deleteAllByTenantId(aLong);
        tenant.setId(aLong);
        tenant.getDocuments()
                .stream()
                .forEach(document -> document.setTenant(tenant));
        tenant.getExtraCosts()
                .stream()
                .forEach(extraCost -> {
                    extraCost.setTenant(tenant);
                    extraCost.setExtraCost(extraCost.getExtraCost().setScale(2, RoundingMode.HALF_UP));
                });
        tenant.setFee(tenant.getFee().setScale(2, RoundingMode.HALF_UP));
        Tenant tenantSaved = tenantRepository.save(tenant);
        BigDecimal extraCostSum = BigDecimal.ZERO;
        for (ExtraCost extraCost: tenant.getExtraCosts()) {
            extraCostSum = extraCostSum.add(extraCost.getExtraCost());
        }
        Currency currency = currencyService.getObjectById(tenantSaved.getCurrency().getId());
        try {
            mailActionService.sendMail(
                    tenant.getMail(),
                    "welcome(change fee value)" + tenant.getFirstName() + "!",
                    "your new fee (Basic: " + tenant.getFee() + ") (Extra costs: " + extraCostSum +")" + currency.getName()
            );
        } catch (EmailSendException e) {
            throw new NotFoundException("");
        }
        return tenantSaved;
    }

    public Page<Tenant> getObjectsByApartmentId(Long aLong, int page) {
        log.debug("gets all tenants by apartment id: " + aLong);
        return tenantRepository.getTenantsByApartment_Id(aLong, pageableHelper.countPageable(page));
    }
}
