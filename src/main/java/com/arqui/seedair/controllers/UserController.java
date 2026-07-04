package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.TokenDTO;
import com.arqui.seedair.dtos.UserDTO;
import com.arqui.seedair.entities.User;
import com.arqui.seedair.security.JwtUtilService;
import com.arqui.seedair.security.UserSecurity;
import com.arqui.seedair.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair")  // El grupo de peticiones que se van a escuchar con este Controller
// http://localhost:8080/seedair
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilService jwtUtilService;


    @PostMapping("/users/register") // http://localhost:8080/seedair/users/register
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user){
        user=userService.addDTO(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PostMapping("/users/login") // http://localhost:8080/seedair/users/login
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO user){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
        );

        UserSecurity userSecurity = (UserSecurity) userDetailsService.loadUserByUsername(user.getUsername());

        String jwt=jwtUtilService.generateToken(userSecurity);
        Long id = userSecurity.getUser().getId();
        String authorities = userSecurity.getUser().getAuthorities().stream().
                map(authority -> authority.getName())
                .collect(Collectors.joining(";","",""));

        return new ResponseEntity<>(new TokenDTO(jwt, id, authorities), HttpStatus.OK);

    }


}
