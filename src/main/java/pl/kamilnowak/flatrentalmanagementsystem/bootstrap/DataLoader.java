package pl.kamilnowak.flatrentalmanagementsystem.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.*;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.UserData;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.security.type.TypeAccount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final LoginUserService loginUserService;
    private final CurrencyService currencyService;

    @Autowired
    public DataLoader(LoginUserService loginUserService, CurrencyService currencyService) {
        this.loginUserService = loginUserService;
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

        List<Apartment> apartments = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Apartment apartment = Apartment.builder()
                    .name("Test")
                    .description("test desc")
                    .street("a")
                    .city("b")
                    .country("Poland")
                    .houseNumber("11")
                    .postcode("55-444")
                    .build();
            Currency currency = currencyService.getAllObject().get(0);
            List<Tenant> tenants = new ArrayList();
            for (int j = 0; j < 22; j++) {
                Tenant tenant = Tenant.builder()
                        .description("aaa")
                        .apartment(apartment)
                        .fee(BigDecimal.ONE)
                        .firstName("Name")
                        .lastName("last")
                        .isPaid(true)
                        .isActive(true)
                        .mail("aaaaa")
                        .currency(currency)
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

                ExtraCost extraCost = ExtraCost.builder()
                        .extraCost(new BigDecimal(10))
                        .name("test")
                        .tenant(tenant)
                        .build();

                tenant.setExtraCosts(List.of(extraCost));
                tenant.setDocuments(List.of(document));
                tenants.add(tenant);
            }

            apartment.setTenants(tenants);

            apartments.add(apartment);
        }

        UserData userData = UserData.builder()
                .firstName("Admin")
                .lastName("Admin")
                .activeAccountData(LocalDateTime.now())
                .apartments(apartments)
                .build();

        apartments.stream().forEach(apartment -> apartment.setUserData(userData));

        LoginUser loginUser = LoginUser.builder()
                .userData(userData)
                .mail("kamilnowakx98@gmail.com")
                .password("TestTest1")
                .isEnable(true)
                .role(TypeAccount.USER)
                .build();

        userData.setLoginUser(loginUser);

        loginUserService.createObject(loginUser);
    }
}
