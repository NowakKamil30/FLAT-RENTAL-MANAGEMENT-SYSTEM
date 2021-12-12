package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.ExtraCostDHO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.model.ExtraCostSum;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.ExtraCostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/extraCost")
public class ExtraCostController {

    private final ModelMapper modelMapper;
    private final ExtraCostService extraCostService;

    @Autowired
    public ExtraCostController(ModelMapper modelMapper, ExtraCostService extraCostService) {
        this.modelMapper = modelMapper;
        this.extraCostService = extraCostService;
    }

    @GetMapping("/tenant/{id}/all")
    public ResponseEntity<List<ExtraCostDHO>> getAllExtraCostForTenant(@PathVariable Long id) {
        return ResponseEntity.ok(extraCostService.getAllByTenantId(id).stream()
                .map(extraCost -> modelMapper.map(extraCost, ExtraCostDHO.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<Page<ExtraCostDHO>> getAllExtraCostForTenant(@PathVariable Long id, @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(extraCostService.getAllByTenantId(id, page)
                .map(extraCost -> modelMapper.map(extraCost, ExtraCostDHO.class)));
    }

    @GetMapping("/tenant/{id}/cost")
    public ResponseEntity<ExtraCostSum> getExtraCostPriceForTenant(@PathVariable Long id) {
        return ResponseEntity.ok(extraCostService.getExtraPriceForTenant(id));
    }
}
