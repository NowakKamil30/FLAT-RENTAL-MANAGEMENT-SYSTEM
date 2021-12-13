package pl.kamilnowak.flatrentalmanagementsystem.apartment.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.ExtraCost;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.model.ExtraCostSum;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.repository.ExtraCostRepository;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
public class ExtraCostService implements CRUDOperation<ExtraCost, Long> {

    private final ExtraCostRepository extraCostRepository;
    private final PageableHelper pageableHelper;

    @Autowired
    public ExtraCostService(ExtraCostRepository extraCostRepository, PageableHelper pageableHelper) {
        this.extraCostRepository = extraCostRepository;
        this.pageableHelper = pageableHelper;
    }

    @Override
    public ExtraCost createObject(ExtraCost extraCost) {
        log.debug("create extraCost");
        return extraCostRepository.save(extraCost);
    }

    @Override
    public void deleteById(Long aLong) {
        log.debug("delete extraCost id: " + aLong);
        extraCostRepository.deleteById(aLong);
    }

    @Override
    public ExtraCost getObjectById(Long aLong) {
        log.debug("read extraCost id:" + aLong);
        return extraCostRepository.getById(aLong);
    }

    @Override
    public Page<ExtraCost> getAllObject(int page) {
        log.debug("read ExtraCost page: " + page);
        return extraCostRepository.findAll(pageableHelper.countPageable(page));
    }

    @Override
    public ExtraCost updateObject(ExtraCost extraCost, Long aLong) {
        log.debug("update extraCost id: " + aLong);
        if(extraCostRepository.findById(aLong).isEmpty()) {
            return extraCostRepository.save(extraCost);
        }
        extraCost.setId(aLong);
        return extraCostRepository.save(extraCost);
    }

    public List<ExtraCost> getAllByTenantId(Long aLong) {
        log.debug("read all extraCosts for tenant id: " + aLong);
        return extraCostRepository.getAllByTenant_Id(aLong);
    }

    public Page<ExtraCost> getAllByTenantId(Long aLong, int page) {
        log.debug("read all extraCosts for tenant id: " + aLong);
        return extraCostRepository.getAllByTenant_Id(aLong, pageableHelper.countPageable(page));
    }

    public ExtraCostSum getExtraPriceForTenant(Long aLong) {
        log.debug("read extraCost sum for tenant id: " + aLong);
        BigDecimal extraPriceSum = new BigDecimal(0);
        List<ExtraCost> extraCostList = extraCostRepository.getAllByTenant_Id(aLong);
        for (ExtraCost extraCost: extraCostList) {
            extraPriceSum = extraPriceSum.add(extraCost.getExtraCost());
        }
        return ExtraCostSum.builder()
                .price(extraPriceSum)
                .build();
    }

    public void deleteAllByTenantId(Long aLong) {
        log.debug("delete all extra costs by tenant: " + aLong);
        extraCostRepository.deleteAllByTenant_Id(aLong);
    }
}
