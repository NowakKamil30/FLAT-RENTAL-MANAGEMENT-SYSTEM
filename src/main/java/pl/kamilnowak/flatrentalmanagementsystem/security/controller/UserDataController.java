package pl.kamilnowak.flatrentalmanagementsystem.security.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotAuthorizationException;
import pl.kamilnowak.flatrentalmanagementsystem.security.dto.UserDataDTO;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.UserData;
import pl.kamilnowak.flatrentalmanagementsystem.security.model.UserInfoModel;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.UserDataService;

import java.security.Principal;

@RestController
@RequestMapping("/v1/userData")
public class UserDataController {

    private final ModelMapper modelMapper;
    private final UserDataService userDataService;
    private final LoginUserService loginUserService;

    public UserDataController(ModelMapper modelMapper, UserDataService userDataService, LoginUserService loginUserService) {
        this.modelMapper = modelMapper;
        this.userDataService = userDataService;
        this.loginUserService = loginUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataDTO> getUserById(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        UserData userData = userDataService.getObjectById(id);
        if (!userData.getLoginUser().getMail().equals(principal.getName())) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok().body(modelMapper.map(userData, UserDataDTO.class));
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserDataDTO>> getUsers (@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(userDataService.getAllObject(page)
                .map(userData -> modelMapper.map(userData, UserDataDTO.class)));
    }

    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserInfoModel>> getUserInfos (@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(userDataService.getAllObject(page)
                .map(userData -> UserInfoModel
                        .builder()
                        .firstName(userData.getFirstName())
                        .lastName(userData.getLastName())
                        .loginUserId(userData.getLoginUser().getId())
                        .mail(userData.getLoginUser().getMail())
                        .build()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDataDTO> updateUserData(@PathVariable Long id, @RequestBody UserData userData, Principal principal) throws NotAuthorizationException {
        UserData userDataToCheck = userDataService.getObjectById(id);
        if (!userDataToCheck.getLoginUser().getMail().equals(principal.getName())) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(modelMapper.map(userDataService.updateObject(userData, id), UserDataDTO.class));
    }
}
