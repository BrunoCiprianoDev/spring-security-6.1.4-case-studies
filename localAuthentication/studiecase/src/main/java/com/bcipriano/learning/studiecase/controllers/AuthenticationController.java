package com.bcipriano.learning.studiecase.controllers;

import com.bcipriano.learning.studiecase.controllers.dto.request.AuthenticationDTO;
import com.bcipriano.learning.studiecase.controllers.dto.request.RegisterDTO;
import com.bcipriano.learning.studiecase.controllers.dto.request.UserUpdateDTO;
import com.bcipriano.learning.studiecase.controllers.dto.response.TokenDTO;
import com.bcipriano.learning.studiecase.domain.User;
import com.bcipriano.learning.studiecase.domain.UserRole;
import com.bcipriano.learning.studiecase.services.TokenService;
import com.bcipriano.learning.studiecase.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        User user = converter(registerDTO);
        userService.createNewUser(user);
        return ResponseEntity.ok().body("User saved.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthenticationDTO authenticationDTO){
        User userAuthenticated = this.validateUser(authenticationDTO.login(), authenticationDTO.password());
        var token = tokenService.createAccessToken(userAuthenticated);
        return ResponseEntity.ok().body(new TokenDTO(token));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        User user = converter(userUpdateDTO);
        User userAuthenticated = validateUser(user.getLogin(), user.getPassword());
        userService.updateUser(userAuthenticated, user);

        return ResponseEntity.ok().body("User updated succesfully.");
    }

    private User validateUser(String login, String password) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(login, password);
        return (User) this.authenticationManager.authenticate(userNamePassword).getPrincipal();
    }

    public User converter(RegisterDTO registerDTO) {
        return User.builder()
                .login(registerDTO.login())
                .firstName(registerDTO.firstName())
                .lastName(registerDTO.lastName())
                .password(registerDTO.password())
                .role(UserRole.valueOf(registerDTO.role()))
                .build();
    }

    public User converter(UserUpdateDTO userUpdateDTO) {
        return User.builder()
                .login(userUpdateDTO.login())
                .password(userUpdateDTO.password())
                .firstName(userUpdateDTO.firstName())
                .lastName(userUpdateDTO.lastName())
                .build();
    }


}