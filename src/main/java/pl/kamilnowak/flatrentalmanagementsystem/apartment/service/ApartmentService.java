package pl.kamilnowak.flatrentalmanagementsystem.apartment.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Apartment;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.repository.ApartmentRepository;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Log4j2
public class ApartmentService implements CRUDOperation<Apartment, Long> {

    private final ApartmentRepository apartmentRepository;
    private final ImageService imageService;
    private final PageableHelper pageableHelper;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository, ImageService imageService, PageableHelper pageableHelper) {
        this.apartmentRepository = apartmentRepository;
        this.imageService = imageService;
        this.pageableHelper = pageableHelper;
    }

    @Override
    public Apartment createObject(Apartment apartment) {
        log.debug("create apartment");
        apartment.getImages()
                .stream()
                .forEach(image -> {
                    image.setApartment(apartment);
                    image.setUploadDate(LocalDate.now());
                });
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
    @Transactional
    public Apartment updateObject(Apartment apartment, Long aLong) {
        log.debug("update apartment id: " + aLong);
        if(apartmentRepository.findById(aLong).isEmpty()) {
            return apartmentRepository.save(apartment);
        }
        imageService.deleteAllByApartmentId(aLong);
        apartment.setId(aLong);
        apartment.getImages()
                .stream()
                .forEach(image -> {
                    image.setApartment(apartment);
                    image.setUploadDate(LocalDate.now());
                });
        return apartmentRepository.save(apartment);
    }

    public Page<Apartment> getApartmentsByUserDataId(Long aLong, int page) {
        log.debug("read apartments by user id: " + aLong);
        return apartmentRepository.getApartmentsByUserData_Id(aLong, pageableHelper.countPageable(page));
    }

    public Apartment getApartmentByLoginUserMailAndId(String mail, Long id) {
        log.debug("get apartment by id: " + id + " login user mail: " + mail);
        return apartmentRepository.getApartmentByUserData_LoginUser_MailAndId(mail, id);
    }
}
