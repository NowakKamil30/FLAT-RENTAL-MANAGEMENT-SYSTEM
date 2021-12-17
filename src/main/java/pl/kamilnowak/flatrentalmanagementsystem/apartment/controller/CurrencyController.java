package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.CurrencyDHO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Currency;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.CurrencyService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.EntityExistException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/currency")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final ModelMapper modelMapper;

    @Autowired
    public CurrencyController(CurrencyService currencyService, ModelMapper modelMapper) {
        this.currencyService = currencyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<CurrencyDHO>> getCurrencies(@RequestParam(defaultValue = "1") int page) {
        Page<CurrencyDHO> currencyDHOPage = currencyService.getAllObject(page)
                .map(currency -> modelMapper.map(currency, CurrencyDHO.class));
        return ResponseEntity.ok(currencyDHOPage);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyDHO>> getCurrencies() {
        List<CurrencyDHO> currencyDHOList = currencyService.getAllObject().stream()
                .map(currency -> modelMapper.map(currency, CurrencyDHO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(currencyDHOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDHO> getCurrency(@PathVariable Long id) {
        Currency currency = currencyService.getObjectById(id);
        if (currency == null) {
            throw new NotFoundException(id.toString());
        }
        return ResponseEntity.ok(modelMapper.map(currency, CurrencyDHO.class));
    }

    @PostMapping("")
    public ResponseEntity<CurrencyDHO> createCurrency(@RequestBody Currency currency) {
        Currency createdCurrency = null;
        try {
            createdCurrency = currencyService.createObject(currency);
        } catch(EntityExistException e) {
            throw new EntityExistException();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(createdCurrency, CurrencyDHO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        if (currencyService.getObjectById(id) == null) {
            throw new NotFoundException(id.toString());
        }
        currencyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyDHO> updateCurrency(@PathVariable Long id, @RequestBody Currency currency) {
        return ResponseEntity.ok(modelMapper.map(currencyService.updateObject(currency, id), CurrencyDHO.class));
    }
}
