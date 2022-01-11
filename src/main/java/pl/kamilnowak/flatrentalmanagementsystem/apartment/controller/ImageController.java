package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.ImageDTO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Apartment;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Image;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.ApartmentService;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.ImageService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.NotAuthorizationException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/image")
public class ImageController {

    private final ImageService imageService;
    private final ModelMapper modelMapper;
    private final ApartmentService apartmentService;

    @Autowired
    public ImageController(ImageService imageService, ModelMapper modelMapper, ApartmentService apartmentService) {
        this.imageService = imageService;
        this.modelMapper = modelMapper;
        this.apartmentService = apartmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        Image image = imageService.getImageByLoginUserMailAndId(principal.getName(), id);
        if (image == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(modelMapper.map(image, ImageDTO.class));
    }

    @PostMapping("")
    public ResponseEntity<ImageDTO> createImage(@RequestBody Image image) {
        Image createdImage = imageService.createObject(image);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(createdImage, ImageDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id, Principal principal) throws NotAuthorizationException  {
        Image image = imageService.getImageByLoginUserMailAndId(principal.getName(), id);
        if (image == null) {
            throw new NotAuthorizationException();
        }
        imageService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody Image image, Principal principal) throws NotAuthorizationException  {
        Image imageToCheck = imageService.getImageByLoginUserMailAndId(principal.getName(), id);
        if (imageToCheck == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(modelMapper.map(imageService.updateObject(image, id), ImageDTO.class));
    }

    @GetMapping("/apartment/{id}")
    public ResponseEntity<Page<ImageDTO>> getImagesByApartment(@PathVariable Long id, @RequestParam(defaultValue = "1") int page, Principal principal) throws NotAuthorizationException  {
        Apartment apartment = apartmentService.getApartmentByLoginUserMailAndId(principal.getName(), id);
        if (apartment == null) {
            throw new NotAuthorizationException();
        }
        return  ResponseEntity.ok(imageService.getObjectsByApartmentId(id, page)
                .map(image -> modelMapper.map(image, ImageDTO.class)));
    }

    @GetMapping("/apartment/{id}/all")
    public ResponseEntity<List<ImageDTO>> getImagesByApartment(@PathVariable Long id, Principal principal) throws NotAuthorizationException  {
        Apartment apartment = apartmentService.getApartmentByLoginUserMailAndId(principal.getName(), id);
        if (apartment == null) {
            throw new NotAuthorizationException();
        }
        return  ResponseEntity.ok(imageService.getObjectsByApartmentId(id).stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .collect(Collectors.toList()));
    }
}
