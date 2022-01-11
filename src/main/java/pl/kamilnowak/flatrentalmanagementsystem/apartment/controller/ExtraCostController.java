package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.ExtraCostDTO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.model.ExtraCostSum;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.ExtraCostService;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.TenantService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotAuthorizationException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/extraCost")
public class ExtraCostController {

    private final ModelMapper modelMapper;
    private final ExtraCostService extraCostService;
    private final TenantService tenantService;

    @Autowired
    public ExtraCostController(ModelMapper modelMapper, ExtraCostService extraCostService, TenantService tenantService) {
        this.modelMapper = modelMapper;
        this.extraCostService = extraCostService;
        this.tenantService = tenantService;
    }

    @GetMapping("/tenant/{id}/all")
    public ResponseEntity<List<ExtraCostDTO>> getAllExtraCostForTenant(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        Tenant tenantSaved = tenantService.getTenantByLoginUserMailAndId(principal.getName(), id);
        if (tenantSaved == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(extraCostService.getAllByTenantId(id).stream()
                .map(extraCost -> modelMapper.map(extraCost, ExtraCostDTO.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<Page<ExtraCostDTO>> getAllExtraCostForTenant(@PathVariable Long id, @RequestParam(defaultValue = "1") int page, Principal principal) throws NotAuthorizationException  {
        Tenant tenantSaved = tenantService.getTenantByLoginUserMailAndId(principal.getName(), id);
        if (tenantSaved == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(extraCostService.getAllByTenantId(id, page)
                .map(extraCost -> modelMapper.map(extraCost, ExtraCostDTO.class)));
    }

    @GetMapping("/tenant/{id}/cost")
    public ResponseEntity<ExtraCostSum> getExtraCostPriceForTenant(@PathVariable Long id, Principal principal) throws NotAuthorizationException  {
        Tenant tenantSaved = tenantService.getTenantByLoginUserMailAndId(principal.getName(), id);
        if (tenantSaved == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(extraCostService.getExtraPriceForTenant(id));
    }
}
