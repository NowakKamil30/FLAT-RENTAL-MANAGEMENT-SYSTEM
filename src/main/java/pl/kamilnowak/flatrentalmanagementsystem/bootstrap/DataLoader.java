package pl.kamilnowak.flatrentalmanagementsystem.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ApartmentService apartmentService;
    private final CurrencyService currencyService;

    @Autowired
    public DataLoader(ApartmentService apartmentService, CurrencyService currencyService) {
        this.apartmentService = apartmentService;
        this.currencyService = currencyService;
    }


    @Override
    public void run(String... args) throws Exception {
        if (currencyService.getAllObject(1).isEmpty()) {
            currencyService.createObject(Currency.builder()
                    .name("PLN")
                    .build());
            currencyService.createObject(Currency.builder()
                    .name("EURO")
                    .build());
            currencyService.createObject(Currency.builder()
                    .name("$")
                    .build());
        }

        Apartment apartment = Apartment.builder()
                .name("Test")
                .description("test desc")
                .latitude(123)
                .longitude(33)
                .street("a")
                .city("b")
                .houseNumber("11")
                .postcode("55-444")
                .build();

        Image image = Image.builder()
                .photo("asdsadsa")
                .title("test title")
                .uploadDate(LocalDate.now())
                .apartment(apartment)
                .build();

        apartment.setImages(List.of(image));

        Tenant tenant = Tenant.builder()
                .description("aaa")
                .apartment(apartment)
                .fee(BigDecimal.ONE)
                .firstName("Name")
                .lastName("last")
                .isPaid(true)
                .isActive(true)
                .mail("aaaaa")
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .paidDate(LocalDate.now())
                .phoneNumber("444555666")
                .build();

        Document document = Document.builder()
                .document("aaaa")
                .tenant(tenant)
                .name("testets")
                .build();

        tenant.setDocuments(List.of(document));

        apartment.setTenants(List.of(tenant));

        apartmentService.createObject(apartment);
    }
}
