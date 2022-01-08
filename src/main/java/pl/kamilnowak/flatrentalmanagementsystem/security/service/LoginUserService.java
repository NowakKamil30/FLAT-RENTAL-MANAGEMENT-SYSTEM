package pl.kamilnowak.flatrentalmanagementsystem.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.NotFoundException;
import pl.kamilnowak.flatrentalmanagementsystem.exception.type.TokenIsTooOldException;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.VerificationToken;
import pl.kamilnowak.flatrentalmanagementsystem.security.repository.LoginUserRepository;
import pl.kamilnowak.flatrentalmanagementsystem.security.type.TypeAccount;
import pl.kamilnowak.flatrentalmanagementsystem.util.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.util.service.PageableHelper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Service
@Log4j2
public class LoginUserService implements UserDetailsService, CRUDOperation<LoginUser, Long> {

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public LoginUserService(LoginUserRepository loginUserRepository, PasswordEncoder passwordEncoder, PageableHelper pageableHelper, VerificationTokenService verificationTokenService) {
        this.loginUserRepository = loginUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.pageableHelper = pageableHelper;
        this.verificationTokenService = verificationTokenService;
    }

    @Override
    public LoginUser loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername");
        LoginUser loginUser = loginUserRepository.findLoginUserByMail(username);
        if (loginUser == null) {
            throw new UsernameNotFoundException("");
        }
        return loginUser;
    }

    @Transactional
    public LoginUser verifyAccount(String token) throws NotFoundException, TokenIsTooOldException {
        log.debug("verify account " + token);
        VerificationToken verificationToken = verifyVerificationToken(token);
        LoginUser loginUser = verificationToken.getLoginUser();
        verificationTokenService.deleteById(verificationToken.getId());
        loginUser.setEnable(true);
        loginUser.setVerificationToken(null);
        loginUser.getUserData().setActiveAccountData(LocalDateTime.now());
        return loginUserRepository.save(loginUser);
    }

    @Transactional
    public LoginUser changePassword(String token, String password) throws NotFoundException, TokenIsTooOldException {
        log.debug("change password token:" + token);
        VerificationToken verificationToken = verifyVerificationToken(token);
        LoginUser loginUser = verificationToken.getLoginUser();
        verificationTokenService.deleteById(verificationToken.getId());
        loginUser.setPassword(passwordEncoder.encode(password));
        loginUser.setVerificationToken(null);
        return loginUserRepository.save(loginUser);
    }

    @Override
    public LoginUser createObject(LoginUser loginUser) {
        log.debug("create login user");
        loginUser.getUserData().setCreateUserData(LocalDateTime.now());
        loginUser.setPassword(passwordEncoder.encode(loginUser.getPassword()));
        return loginUserRepository.save(loginUser);
    }

    @Override
    public void deleteById(Long aLong) {
        log.debug("delete login user id: " + aLong);
        loginUserRepository.deleteById(aLong);
    }

    @Override
    public LoginUser getObjectById(Long aLong) {
        log.debug("read login user id: " + aLong);
        return loginUserRepository.getById(aLong);
    }

    @Override
    public Page<LoginUser> getAllObject(int page) {
        log.debug("read login users page: " + page);
        return loginUserRepository.findAll(pageableHelper.countPageable(page));
    }

    @Override
    public LoginUser updateObject(LoginUser loginUser, Long aLong) {
        log.debug("update login user id: " + aLong);
        if (loginUserRepository.findById(aLong).isEmpty()) {
            return loginUserRepository.save(loginUser);
        }
        loginUser.setId(aLong);
        return loginUserRepository.save(loginUser);
    }

    private VerificationToken verifyVerificationToken(String token) throws TokenIsTooOldException, NotFoundException {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken == null) {
            throw new NotFoundException("verification token");
        }
        LoginUser loginUser = verificationToken.getLoginUser();
        if (loginUser == null) {
            throw new NotFoundException("login user");
        }
        if(verificationToken.getCreateTime().plusHours(1).isBefore(LocalDateTime.now())) {
            verificationTokenService.deleteById(verificationToken.getId());
            throw new TokenIsTooOldException("verification token");
        }
        return verificationToken;
    }

    public LoginUser setIsEnable(Long id, boolean isEnable) {
        LoginUser loginUser = loginUserRepository.getById(id);
        if (loginUser != null) {
           loginUser.setEnable(isEnable);
           return loginUserRepository.save(loginUser);
        }
        throw new NotFoundException("login user doesn't exist");
    }

    public LoginUser setRole(Long id, TypeAccount typeAccount) {
        LoginUser loginUser = loginUserRepository.getById(id);
        if (loginUser != null) {
            loginUser.setRole(typeAccount);
            return loginUserRepository.save(loginUser);
        }
        throw new NotFoundException("login user doesn't exist");
    }
}
