package pl.kamilnowak.flatrentalmanagementsystem.apartment.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.repository.TenantRepository;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

@Service
@Log4j2
public class TenantService implements CRUDOperation<Tenant, Long> {

    private final TenantRepository tenantRepository;
    private final PageableHelper pageableHelper;

    @Autowired
    public TenantService(TenantRepository tenantRepository, PageableHelper pageableHelper) {
        this.tenantRepository = tenantRepository;
        this.pageableHelper = pageableHelper;
    }

    @Override
    public Tenant createObject(Tenant tenant) {
        log.debug("create tenant");
        return tenantRepository.save(tenant);
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
    public Tenant updateObject(Tenant tenant, Long aLong) {
        log.debug("update tenant id: " + aLong);
        if(tenantRepository.findById(aLong).isEmpty()) {
            return tenantRepository.save(tenant);
        }
        tenant.setId(aLong);
        return tenantRepository.save(tenant);
    }

    public Page<Tenant> getObjectsByApartmentId(Long aLong, int page) {
        log.debug("gets all tenants by apartment id: " + aLong);
        return tenantRepository.getTenantsByApartment_Id(aLong, pageableHelper.countPageable(page));
    }
}
