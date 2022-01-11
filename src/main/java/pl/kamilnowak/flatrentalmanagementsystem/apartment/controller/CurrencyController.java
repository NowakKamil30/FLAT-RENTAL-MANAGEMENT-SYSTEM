package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.CurrencyDTO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Currency;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.CurrencyService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.EntityExistException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.NotFoundException;

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
    public ResponseEntity<Page<CurrencyDTO>> getCurrencies(@RequestParam(defaultValue = "1") int page) {
        Page<CurrencyDTO> currencyDHOPage = currencyService.getAllObject(page)
                .map(currency -> modelMapper.map(currency, CurrencyDTO.class));
        return ResponseEntity.ok(currencyDHOPage);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyDTO>> getCurrencies() {
        List<CurrencyDTO> currencyDTOList = currencyService.getAllObject().stream()
                .map(currency -> modelMapper.map(currency, CurrencyDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(currencyDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDTO> getCurrency(@PathVariable Long id) {
        Currency currency = currencyService.getObjectById(id);
        if (currency == null) {
            throw new NotFoundException(id.toString());
        }
        return ResponseEntity.ok(modelMapper.map(currency, CurrencyDTO.class));
    }

    @PostMapping("")
    public ResponseEntity<CurrencyDTO> createCurrency(@RequestBody Currency currency) {
        Currency createdCurrency = null;
        try {
            createdCurrency = currencyService.createObject(currency);
        } catch(EntityExistException e) {
            throw new EntityExistException();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(createdCurrency, CurrencyDTO.class));
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
    public ResponseEntity<CurrencyDTO> updateCurrency(@PathVariable Long id, @RequestBody Currency currency) {
        return ResponseEntity.ok(modelMapper.map(currencyService.updateObject(currency, id), CurrencyDTO.class));
    }
}
