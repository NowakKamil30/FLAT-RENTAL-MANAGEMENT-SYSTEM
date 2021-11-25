package pl.kamilnowak.flatrentalmanagementsystem.apartmet.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.Apartment;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.repository.ApartmentRepository;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

@Service
@Log4j2
public class ApartmentService implements CRUDOperation<Apartment, Long> {

    private final ApartmentRepository apartmentRepository;
    private final PageableHelper pageableHelper;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository, PageableHelper pageableHelper) {
        this.apartmentRepository = apartmentRepository;
        this.pageableHelper = pageableHelper;
    }

    @Override
    public Apartment createObject(Apartment apartment) {
        log.debug("create apartment");
        return apartmentRepository.save(apartment);
    }

    @Override
    public void deleteById(Long aLong) {
        log.debug("delete apartment id: " + aLong);
        apartmentRepository.deleteById(aLong);
    }

    @Override
    public Apartment getObjectById(Long aLong) {
        log.debug("read apartment id: " + aLong);
        return apartmentRepository.getById(aLong);
    }

    @Override
    public Page<Apartment> getAllObject(int page) {
        log.debug("read apartments page: " + page);
        return apartmentRepository.findAll(pageableHelper.countPageable(page));
    }

    @Override
    public Apartment updateObject(Apartment apartment, Long aLong) {
        log.debug("update apartment id: " + aLong);
        if(apartmentRepository.findById(aLong).isEmpty()) {
            return apartmentRepository.save(apartment);
        }
        apartment.setId(aLong);
        return apartmentRepository.save(apartment);
    }
}
