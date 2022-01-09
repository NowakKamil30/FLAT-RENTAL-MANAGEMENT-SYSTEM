package pl.kamilnowak.flatrentalmanagementsystem.security.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.security.dto.LoginUserDTO;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.security.type.TypeAccount;

import java.util.Map;

@RestController
@RequestMapping("/v1/loginUser")
public class LoginUserController {

    private final LoginUserService loginUserService;
    private final ModelMapper modelMapper;

    @Autowired
    public LoginUserController(LoginUserService loginUserService, ModelMapper modelMapper) {
        this.loginUserService = loginUserService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoginUserDTO> getLoginUser(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(loginUserService.getObjectById(id), LoginUserDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoginUser(@PathVariable Long id) {
        loginUserService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<Page<LoginUserDTO>> getLoginUsers(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok( loginUserService.getAllObject(page)
                .map(loginUser -> modelMapper.map(loginUser, LoginUserDTO.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoginUserDTO> updateLoginUser(@PathVariable Long id, @RequestBody LoginUser loginUser) {
        return ResponseEntity.ok(modelMapper.map(loginUserService.updateObject(loginUser, id), LoginUserDTO.class));
    }

    @PostMapping("/isEnable/{id}")
    public ResponseEntity<LoginUserDTO> setEnable(@PathVariable Long id, @RequestBody Map<String, Boolean> argMap) {
        Boolean isEnable = argMap.get("isEnable");
        return ResponseEntity.ok(modelMapper.map(loginUserService.setIsEnable(id, isEnable), LoginUserDTO.class));
    }

    @PostMapping("/role/{id}")
    public ResponseEntity<LoginUserDTO> setRole(@PathVariable Long id, @RequestBody Map<String, String> argMap) {
        return ResponseEntity.ok(modelMapper.map(loginUserService.setRole(id, TypeAccount.valueOf(argMap.get("role"))), LoginUserDTO.class));
    }

}
