package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.ImageDHO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Image;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.ImageService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/image")
public class ImageController {

    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageController(ImageService imageService, ModelMapper modelMapper) {
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<ImageDHO>> getImages(@RequestParam(defaultValue = "1") int page) {
        Page<ImageDHO> imageDHOPage = imageService.getAllObject(page)
                .map(image -> modelMapper.map(image, ImageDHO.class));
        return ResponseEntity.ok(imageDHOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDHO> getImage(@PathVariable Long id) {
        Image image = imageService.getObjectById(id);
        if (image == null) {
            throw new NotFoundException(id.toString());
        }
        return ResponseEntity.ok(modelMapper.map(image, ImageDHO.class));
    }

    @PostMapping("")
    public ResponseEntity<ImageDHO> createImage(@RequestBody Image image) {
        Image createdImage = imageService.createObject(image);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(createdImage, ImageDHO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        if (imageService.getObjectById(id) == null) {
            throw new NotFoundException(id.toString());
        }
        imageService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDHO> updateImage(@PathVariable Long id, @RequestBody Image image) {
        return ResponseEntity.ok(modelMapper.map(imageService.updateObject(image, id), ImageDHO.class));
    }

    @GetMapping("/apartment/{id}")
    public ResponseEntity<Page<ImageDHO>> getImagesByApartment(@PathVariable Long id, @RequestParam(defaultValue = "1") int page) {
        return  ResponseEntity.ok(imageService.getObjectsByApartmentId(id, page)
                .map(image -> modelMapper.map(image, ImageDHO.class)));
    }

    @GetMapping("/apartment/{id}/all")
    public ResponseEntity<List<ImageDHO>> getImagesByApartment(@PathVariable Long id) {
        return  ResponseEntity.ok(imageService.getObjectsByApartmentId(id).stream()
                .map(image -> modelMapper.map(image, ImageDHO.class))
                .collect(Collectors.toList()));
    }
}
