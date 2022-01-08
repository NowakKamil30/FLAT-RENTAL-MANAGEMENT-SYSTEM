package pl.kamilnowak.flatrentalmanagementsystem.util.info;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ConfigInfo {
    @Value("${config.page.url}")
    private String pageURL;
    @Value("${config.page.verification.account}")
    private String verificationAccount;
    @Value("${config.page.change.password}")
    private String changePassword;
    @Value("${config.secret.key}")
    private String secretKey;
}
