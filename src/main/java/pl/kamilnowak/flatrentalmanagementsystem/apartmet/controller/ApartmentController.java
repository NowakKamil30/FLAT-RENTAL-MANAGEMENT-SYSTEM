package pl.kamilnowak.flatrentalmanagementsystem.apartmet.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto.ApartmentDHO;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.Apartment;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.service.ApartmentService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;

@RestController
@RequestMapping("/v1/apartment")
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final ModelMapper modelMapper;

    @Autowired
    public ApartmentController(ApartmentService apartmentService, ModelMapper modelMapper) {
        this.apartmentService = apartmentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<ApartmentDHO>> getApartments(@RequestParam(defaultValue = "1") int page) {
        Page<ApartmentDHO> apartmentDHOPage = apartmentService.getAllObject(page)
                .map(apartment -> modelMapper.map(apartment, ApartmentDHO.class));
        return ResponseEntity.ok(apartmentDHOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentDHO> getApartment(@PathVariable Long id) {
        Apartment apartment = apartmentService.getObjectById(id);
        if (apartment == null) {
            throw new NotFoundException(id.toString());
        }
        return ResponseEntity.ok(modelMapper.map(apartment, ApartmentDHO.class));
    }

    @PostMapping("")
    public ResponseEntity<ApartmentDHO> createApartment(@RequestBody Apartment apartment) {
        Apartment createdApartment = apartmentService.createObject(apartment);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(createdApartment, ApartmentDHO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        if (apartmentService.getObjectById(id) == null) {
            throw new NotFoundException(id.toString());
        }
        apartmentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDHO> updateApartment(@PathVariable Long id, @RequestBody Apartment apartment) {
        return ResponseEntity.ok(modelMapper.map(apartmentService.updateObject(apartment, id), ApartmentDHO.class));
    }
}
