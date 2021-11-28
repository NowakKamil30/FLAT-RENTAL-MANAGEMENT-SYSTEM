package pl.kamilnowak.flatrentalmanagementsystem.apartmet.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto.TenantDHO;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.service.TenantService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;

@RestController
@RequestMapping("/v1/tenant")
public class TenantController {

    private final TenantService tenantService;
    private final ModelMapper modelMapper;

    @Autowired
    public TenantController(TenantService tenantService, ModelMapper modelMapper) {
        this.tenantService = tenantService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<TenantDHO>> getTenants(@RequestParam(defaultValue = "1") int page) {
        Page<TenantDHO> tenantDHOPage = tenantService.getAllObject(page)
                .map(tenant -> modelMapper.map(tenant, TenantDHO.class));
        return ResponseEntity.ok(tenantDHOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDHO> getTenant(@PathVariable Long id) {
        Tenant tenant = tenantService.getObjectById(id);
        if (tenant == null) {
            throw new NotFoundException(id.toString());
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
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        if (tenantService.getObjectById(id) == null) {
            throw new NotFoundException(id.toString());
        }
        tenantService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDHO> updateTenant(@PathVariable Long id, @RequestBody Tenant tenant) {
        return ResponseEntity.ok(modelMapper.map(tenantService.updateObject(tenant, id), TenantDHO.class));
    }

    @GetMapping("/apartment/{id}")
    public ResponseEntity<Page<TenantDHO>> getTenantsByApartment(@PathVariable Long id, @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(tenantService.getObjectsByApartmentId(id, page)
                .map(tenant -> modelMapper.map(tenant, TenantDHO.class)));
    }
}
