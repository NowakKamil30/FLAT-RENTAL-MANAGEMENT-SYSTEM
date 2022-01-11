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

import java.time.LocalDateTime;

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
        if (loginUserService.getAllObject(1).isEmpty()) {
            UserData userData = UserData.builder()
                .firstName("Admin")
                .lastName("Admin")
                .activeAccountData(LocalDateTime.now())
                .build();

            LoginUser loginUser = LoginUser.builder()
                .userData(userData)
                .mail("98897@g.elearn.uz.zgora.pl")
                .password("TestTest1")
                .isEnable(true)
                .role(TypeAccount.ADMIN)
                .build();

        userData.setLoginUser(loginUser);

        loginUserService.createObject(loginUser);

        }
    }
}
