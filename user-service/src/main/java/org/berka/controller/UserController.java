package org.berka.controller;

import lombok.RequiredArgsConstructor;
import org.berka.constant.EndPoints;
import org.berka.dto.register.LoginRequestDto;
import org.berka.dto.register.UpdateRequestDto;
import org.berka.dto.register.UserRegisterDto;
import org.berka.repository.entity.User;
import org.berka.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.berka.constant.EndPoints.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<User> findUserById(@PathVariable Long id){
        return ResponseEntity.ok(service.findUserById(id));
    }

    @PostMapping(REGISTER)
    public ResponseEntity<String> register(@RequestBody @Valid UserRegisterDto dto){
        return ResponseEntity.ok(service.register(dto));
    }


    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(service.login(dto));
    }


    @PutMapping(UPDATE)
    public ResponseEntity<String> updateUser(@RequestBody @Valid UpdateRequestDto dto){
        return ResponseEntity.ok(service.updateUser(dto));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<Boolean> deleteUser(@RequestParam @Valid String token){
        return ResponseEntity.ok(service.deleteUser(token));
    }
}
