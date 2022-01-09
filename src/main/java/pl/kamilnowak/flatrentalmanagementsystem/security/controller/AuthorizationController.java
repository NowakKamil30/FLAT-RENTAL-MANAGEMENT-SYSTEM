package pl.kamilnowak.flatrentalmanagementsystem.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.NotFoundException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.TokenIsNotValidException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.TokenIsTooOldException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.UserCannotBeCreatedException;
import pl.kamilnowak.flatrentalmanagementsystem.mail.service.MailActionService;
import pl.kamilnowak.flatrentalmanagementsystem.mail.exception.EmailSendException;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.model.ActiveModel;
import pl.kamilnowak.flatrentalmanagementsystem.security.model.ChangePasswordModel;
import pl.kamilnowak.flatrentalmanagementsystem.security.model.LoginModel;
import pl.kamilnowak.flatrentalmanagementsystem.security.model.MailSendModel;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.util.info.ConfigInfo;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/v1/authorization")
@Log4j2
public class AuthorizationController {

    private final LoginUserService loginUserService;
    private final ConfigInfo configInfo;
    private final MailActionService mailActionService;

    @Autowired
    public AuthorizationController(LoginUserService loginUserService, ConfigInfo configInfo, MailActionService mailActionService) {
        this.loginUserService = loginUserService;
        this.configInfo = configInfo;
        this.mailActionService = mailActionService;
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

        if (loginUser != null && BCrypt.checkpw(loginUserFrom.getPassword(),(loginUser.getPassword())) && loginUser.isEnable()) {
            String jwt = JWT.create()
                    .withClaim("sub", loginUser.getMail())
                    .withClaim("iat", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                    .withClaim("exp", LocalDateTime.now().plusDays(7).toEpochSecond(ZoneOffset.UTC))
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
    @Transactional
    public ResponseEntity<LoginModel> register(@RequestBody LoginUser loginUser) throws UserCannotBeCreatedException, EmailSendException {
        log.debug("register " + loginUser);
        LoginUser loginUserSaved;
        try {
            loginUserSaved = loginUserService.loadUserByUsername(loginUser.getMail());
            if (!loginUserSaved.isEnable()) {
                loginUserService.deleteById(loginUserSaved.getId());
                loginUser.getUserData().setLoginUser(loginUser);
                LoginUser loginUserNewSaved = loginUserService.createObject(loginUser);
                mailActionService.sendActivityAccountEmail(loginUserNewSaved);
                return ResponseEntity.ok().build();
            }
        } catch(UsernameNotFoundException e) {
            loginUser.getUserData().setLoginUser(loginUser);
            LoginUser loginUserNewSaved = loginUserService.createObject(loginUser);
            mailActionService.sendActivityAccountEmail(loginUserNewSaved);
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
    public ResponseEntity<MailSendModel> changePasswordToken(@RequestBody LoginUser loginUser) throws EmailSendException {
        LoginUser loginUserSaved = loginUserService.loadUserByUsername(loginUser.getMail());
        if (loginUserSaved != null && loginUserSaved.isEnable()) {
            mailActionService.sendResetPasswordEmail(loginUserSaved.getMail());
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
