package pl.kamilnowak.flatrentalmanagementsystem.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilnowak.flatrentalmanagementsystem.security.type.TypeAccount;

@RestController
@RequestMapping("/v1/role")
public class RoleController {

    @GetMapping
    public TypeAccount[] getRoles() {
        return TypeAccount.values();
    }
}
