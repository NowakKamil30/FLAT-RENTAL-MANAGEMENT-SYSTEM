package pl.kamilnowak.flatrentalmanagementsystem.apartmet.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.Image;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.repository.ImageRepository;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

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
}
