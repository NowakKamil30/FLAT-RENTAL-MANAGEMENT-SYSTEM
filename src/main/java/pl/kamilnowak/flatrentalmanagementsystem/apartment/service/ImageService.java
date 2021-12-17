package pl.kamilnowak.flatrentalmanagementsystem.apartment.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Image;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.repository.ImageRepository;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

import java.util.List;

@Service
@Log4j2
public class ImageService implements CRUDOperation<Image, Long> {

    private final ImageRepository imageRepository;
    private final PageableHelper pageableHelper;

    @Autowired
    public ImageService(ImageRepository imageRepository, PageableHelper pageableHelper) {
        this.imageRepository = imageRepository;
        this.pageableHelper = pageableHelper;
    }

    @Override
    public Image createObject(Image image) {
        log.debug("create image");
        return imageRepository.save(image);
    }

    @Override
    public void deleteById(Long aLong) {
        log.debug("delete image id: " + aLong);
        imageRepository.deleteById(aLong);

    }

    @Override
    public Image getObjectById(Long aLong) {
        log.debug("read image id: " + aLong);
        return imageRepository.getById(aLong);
    }

    @Override
    public Page<Image> getAllObject(int page) {
        log.debug("read images page: " + page);
        return imageRepository.findAll(pageableHelper.countPageable(page));
    }

    @Override
    public Image updateObject(Image image, Long aLong) {
        log.debug("update image id: " + aLong);
        if(imageRepository.findById(aLong).isEmpty()) {
            return imageRepository.save(image);
        }
        image.setId(aLong);
        return imageRepository.save(image);
    }

    public Page<Image> getObjectsByApartmentId(Long aLong, int page) {
        log.debug("gets all images by apartment id: " + aLong);
        return imageRepository.getImagesByApartment_Id(aLong, pageableHelper.countPageable(page));
    }

    public List<Image> getObjectsByApartmentId(Long aLong) {
        log.debug("gets all images by apartment id: " + aLong);
        return imageRepository.getImagesByApartment_Id(aLong);
    }

    public void deleteAllByApartmentId(Long id) {
        log.debug("delete all photo for: " + id);
        imageRepository.deleteAllByApartment_Id(id);
    }

    public Image getImageByLoginUserMailAndId(String mail, Long id) {
        log.debug("get image by login user mail: " + mail + " id: " + id);
        return imageRepository.getImageByApartment_UserData_LoginUser_MailAndId(mail, id);
    }
}
