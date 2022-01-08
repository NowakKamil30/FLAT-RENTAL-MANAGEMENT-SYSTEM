package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.TenantDHO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Apartment;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.ApartmentService;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.TenantService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.NotAuthorizationException;

import java.security.Principal;

@RestController
@RequestMapping("/v1/tenant")
public class TenantController {

    private final TenantService tenantService;
    private final ApartmentService apartmentService;
    private final ModelMapper modelMapper;

    @Autowired
    public TenantController(TenantService tenantService, ApartmentService apartmentService, ModelMapper modelMapper) {
        this.tenantService = tenantService;
        this.apartmentService = apartmentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDHO> getTenant(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        Tenant tenant = tenantService.getTenantByLoginUserMailAndId(principal.getName(), id);
        if (tenant == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(modelMapper.map(tenant, TenantDHO.class));
    }

    @PostMapping("")
    public ResponseEntity<TenantDHO> createTenant(@RequestBody Tenant tenant) {
        Tenant createdTenant = tenantService.createObject(tenant);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(createdTenant, TenantDHO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id, Principal principal) throws NotAuthorizationException  {
        Tenant tenant = tenantService.getTenantByLoginUserMailAndId(principal.getName(), id);
        if (tenant == null) {
            throw new NotAuthorizationException();
        }
        tenantService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDHO> updateTenant(@PathVariable Long id, @RequestBody Tenant tenant, Principal principal) throws NotAuthorizationException  {
        Tenant tenantSaved = tenantService.getTenantByLoginUserMailAndId(principal.getName(), id);
        if (tenantSaved == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(modelMapper.map(tenantService.updateObject(tenant, id), TenantDHO.class));
    }

    @GetMapping("/apartment/{id}")
    public ResponseEntity<Page<TenantDHO>> getTenantsByApartment(@PathVariable Long id, @RequestParam(defaultValue = "1") int page, Principal principal) throws NotAuthorizationException {
        Apartment apartment = apartmentService.getObjectById(id);
        if (!apartment.getUserData().getLoginUser().getMail().equals(principal.getName())) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(tenantService.getObjectsByApartmentId(id, page)
                .map(tenant -> modelMapper.map(tenant, TenantDHO.class)));
    }
}
