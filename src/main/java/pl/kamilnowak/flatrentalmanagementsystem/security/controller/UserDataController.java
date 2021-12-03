package pl.kamilnowak.flatrentalmanagementsystem.security.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.security.dho.UserDataDHO;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.UserData;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.UserDataService;

@RestController
@RequestMapping("/v1/userData")
public class UserDataController {

    private final ModelMapper modelMapper;
    private final UserDataService userDataService;

    public UserDataController(ModelMapper modelMapper, UserDataService userDataService) {
        this.modelMapper = modelMapper;
        this.userDataService = userDataService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataDHO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(modelMapper.map(userDataService.getObjectById(id), UserDataDHO.class));
    }

    @GetMapping()
    public ResponseEntity<Page<UserDataDHO>> getUsers (@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(userDataService.getAllObject(page)
                .map(userData -> modelMapper.map(userData, UserDataDHO.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDataDHO> updateUserData(@PathVariable Long id, @RequestBody UserData userData) {
        return ResponseEntity.ok(modelMapper.map(userDataService.updateObject(userData, id), UserDataDHO.class));
    }
}
