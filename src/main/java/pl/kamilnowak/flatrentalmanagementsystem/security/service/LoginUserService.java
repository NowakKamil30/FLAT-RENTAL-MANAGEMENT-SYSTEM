package pl.kamilnowak.flatrentalmanagementsystem.security.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kamilnowak.flatrentalmanagementsystem.exception.CannotCreateLoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.dho.LoginUserDHO;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.repository.LoginUserRepository;

import java.time.LocalDateTime;

@Service
@RequestMapping("/v1/loginUser")
public class LoginUserService implements UserDetailsService {

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public LoginUserService(LoginUserRepository loginUserRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.loginUserRepository = loginUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @PostMapping()
    public ResponseEntity<LoginUserDHO> createLoginUser(LoginUser loginUser) {
        if (loginUser.getUser() == null) {
            throw new CannotCreateLoginUser("User is null");
        }
        if (loginUser.getPassword().isEmpty()) {
            throw new CannotCreateLoginUser("password is empty");
        }
        loginUser.getUser().setCreateUserData(LocalDateTime.now());
        loginUser.setPassword(passwordEncoder.encode(loginUser.getPassword()));
        return ResponseEntity.ok(modelMapper.map(loginUserRepository.save(loginUser), LoginUserDHO.class));
    }
}
