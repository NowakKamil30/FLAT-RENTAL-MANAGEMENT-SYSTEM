package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.ApartmentDTO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Apartment;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.ApartmentService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotAuthorizationException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/v1/apartment")
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final LoginUserService loginUserService;
    private final ModelMapper modelMapper;

    @Autowired
    public ApartmentController(ApartmentService apartmentService, LoginUserService loginUserService, ModelMapper modelMapper) {
        this.apartmentService = apartmentService;
        this.loginUserService = loginUserService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentDTO> getApartment(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        LoginUser loginUser = loginUserService.loadUserByUsername(principal.getName());
        if (loginUser.getUserData().getApartments().stream()
                .filter(apartment -> Objects.equals(apartment.getId(), id))
                .findAny().isEmpty()) {
            throw new NotAuthorizationException();
        }
        try {
            Apartment apartment = apartmentService.getObjectById(id);
            if (apartment == null) {
                throw new NotFoundException(id.toString());
            }
            return ResponseEntity.ok(modelMapper.map(apartment, ApartmentDTO.class));
        } catch (Exception e) {
            throw new NotFoundException(id.toString());
        }
    }

    @PostMapping("")
    public ResponseEntity<ApartmentDTO> createApartment(@RequestBody Apartment apartment) {
        Apartment createdApartment = apartmentService.createObject(apartment);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(createdApartment, ApartmentDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        Apartment apartment = apartmentService.getObjectById(id);
        if (apartment == null) {
            throw new NotFoundException(id.toString());
        }
        if (!apartment.getUserData().getLoginUser().getMail().equals(principal.getName())) {
            throw new NotAuthorizationException();
        }
        apartmentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDTO> updateApartment(@PathVariable Long id, @RequestBody Apartment apartment, Principal principal) throws NotAuthorizationException {
        LoginUser loginUser = loginUserService.loadUserByUsername(principal.getName());
        if (loginUser.getUserData().getApartments().stream()
                .filter(apartmentItem -> Objects.equals(apartmentItem.getId(), id))
                .findAny().isEmpty()) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(modelMapper.map(apartmentService.updateObject(apartment, id), ApartmentDTO.class));
    }

    @GetMapping("/userData/{id}")
    public ResponseEntity<Page<ApartmentDTO>> getApartmentsByUserDataId(@PathVariable Long id, @RequestParam(defaultValue = "1") int page, Principal principal) throws NotAuthorizationException {
        if (!Objects.equals(loginUserService.loadUserByUsername(principal.getName()).getUserData().getId(), id)) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(apartmentService.getApartmentsByUserDataId(id, page)
                .map(apartment -> modelMapper.map(apartment, ApartmentDTO.class)));
    }
}
