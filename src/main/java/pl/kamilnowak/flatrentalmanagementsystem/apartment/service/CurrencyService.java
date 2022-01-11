package pl.kamilnowak.flatrentalmanagementsystem.apartment.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Currency;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.repository.CurrencyRepository;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.EntityExistException;
import pl.kamilnowak.flatrentalmanagementsystem.util.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.util.service.PageableHelper;

import java.util.List;


@Service
@Log4j2
public class CurrencyService implements CRUDOperation<Currency, Long> {

    private final CurrencyRepository currencyRepository;
    private final PageableHelper pageableHelper;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, PageableHelper pageableHelper) {
        this.currencyRepository = currencyRepository;
        this.pageableHelper = pageableHelper;
    }

    @Override
    public Currency createObject(Currency currency) {
        log.debug("create currency");
        if (currencyRepository.findCurrencyByName(currency.getName()) != null) {
            throw new EntityExistException();
        }
        return currencyRepository.save(currency);
    }

    @Override
    public Currency getObjectById(Long aLong) {
        log.debug("read currency id: " + aLong);
        return currencyRepository.getById(aLong);
    }

    @Override
    public Page<Currency> getAllObject(int page) {
        log.debug("read currencies page: " + page);
        return currencyRepository.findAll(pageableHelper.countPageable(page));
    }

    public List<Currency> getAllObject() {
        log.debug("read currencies");
        return currencyRepository.findAll();
    }

    @Override
    public void deleteById(Long aLong) {
        log.debug("delete currency id: " + aLong);
        currencyRepository.deleteById(aLong);
    }

    @Override
    public Currency updateObject(Currency currency, Long aLong) {
        log.debug("update currency id: " + aLong);
        if(currencyRepository.findById(aLong).isEmpty()) {
            return currencyRepository.save(currency);
        }
        currency.setId(aLong);
        return currencyRepository.save(currency);
    }
}
