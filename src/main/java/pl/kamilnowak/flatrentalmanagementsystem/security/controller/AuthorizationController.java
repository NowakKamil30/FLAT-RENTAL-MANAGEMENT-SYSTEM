package pl.kamilnowak.flatrentalmanagementsystem.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.TokenIsNotValidException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.TokenIsTooOldException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.UserCannotBeCreatedException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.annotation.SendActiveAccountMail;
import pl.kamilnowak.flatrentalmanagementsystem.mail.annotation.SendResetPasswordMail;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.model.ActiveModel;
import pl.kamilnowak.flatrentalmanagementsystem.security.model.ChangePasswordModel;
import pl.kamilnowak.flatrentalmanagementsystem.security.model.LoginModel;
import pl.kamilnowak.flatrentalmanagementsystem.security.model.MailSendModel;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.util.ConfigInfo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/v1/authorization")
@Log4j2
public class AuthorizationController {

    private final LoginUserService loginUserService;
    private final PasswordEncoder passwordEncoder;
    private final ConfigInfo configInfo;

    @Autowired
    public AuthorizationController(LoginUserService loginUserService, PasswordEncoder passwordEncoder, ConfigInfo configInfo) {
        this.loginUserService = loginUserService;
        this.passwordEncoder = passwordEncoder;
        this.configInfo = configInfo;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginModel> login(@RequestBody LoginUser loginUserFrom) {
        log.debug("sign in: " + loginUserFrom);
        LoginUser loginUser;
        try {
            loginUser = loginUserService.loadUserByUsername(loginUserFrom.getMail());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        if (loginUser != null && passwordEncoder.encode(loginUserFrom.getPassword()).equals(loginUser.getPassword()) && loginUser.isEnable()) {
            String jwt = JWT.create()
                    .withClaim("mail", loginUser.getMail())
                    .withClaim("createDate", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                    .withClaim("validTo", LocalDateTime.now().plusDays(7).toEpochSecond(ZoneOffset.UTC))
                    .withClaim("role", "ROLE_" + loginUser.getRole().toString())
                    .sign(Algorithm.HMAC512(configInfo.getSecretKey()));

            return ResponseEntity.ok(LoginModel.builder()
                    .token(jwt)
                    .id(loginUser.getUserData().getId())
                    .role(loginUser.getRole())
                    .mail(loginUser.getMail())
                    .build());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    @SendActiveAccountMail
    public ResponseEntity<LoginModel> register(@RequestBody LoginUser loginUser) throws UserCannotBeCreatedException {
        LoginUser loginUserSaved = loginUserService.loadUserByUsername(loginUser.getMail());
        if (loginUserSaved != null && !loginUserSaved.isEnable()) {
            loginUserService.deleteById(loginUserSaved.getId());
            loginUser.getUserData().setLoginUser(loginUser);
            return ResponseEntity.ok().build();
        } else if (loginUserSaved == null) {
            loginUser.getUserData().setLoginUser(loginUser);
            loginUserService.createObject(loginUser);
            return ResponseEntity.ok().build();
        }

        throw new UserCannotBeCreatedException("");
    }

    @GetMapping("/verifyToken")
    public ResponseEntity<ActiveModel> verifyAccount(@RequestParam String token) throws TokenIsNotValidException {
        try {
            loginUserService.verifyAccount(token);
        } catch (TokenIsTooOldException | NotFoundException e) {
            throw new TokenIsNotValidException();
        }
        return ResponseEntity.ok(ActiveModel.builder()
                .isActive(true)
                .build());
    }

    @PostMapping("/changePassword")
    @SendResetPasswordMail
    public ResponseEntity<MailSendModel> changePasswordToken(@RequestBody LoginUser loginUser) {
        LoginUser loginUserSaved = loginUserService.loadUserByUsername(loginUser.getMail());
        if (loginUserSaved != null && loginUserSaved.isEnable()) {
            return ResponseEntity.ok(MailSendModel.builder()
                    .isSend(true)
                    .build());
        }

        throw new UsernameNotFoundException(loginUser.getUsername());
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ChangePasswordModel> changePassword(@RequestParam String token, @RequestBody LoginUser loginUser) throws TokenIsTooOldException {
        loginUserService.changePassword(token, loginUser.getPassword());
        return ResponseEntity.ok(ChangePasswordModel.builder()
                .isPasswordChange(true)
                .build());
    }
}
