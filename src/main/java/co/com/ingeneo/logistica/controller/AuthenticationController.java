package co.com.ingeneo.logistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.com.ingeneo.logistica.common.Constants;
import co.com.ingeneo.logistica.security.AuthResponse;
import co.com.ingeneo.logistica.security.JwtRequest;
import co.com.ingeneo.logistica.security.JwtTokenUtil;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        String username = authenticationRequest.getUsername();

        //Buscar usuario
        AuthResponse response = new AuthResponse();
        if (!(Constants.USUARIO_MOBILE.equals(username) && Constants.USUARIO_PASSWORD.equals(authenticationRequest.getPassword()))) {
            response.setAccess_token("-1");
            response.setUser(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            final String token = jwtTokenUtil.generateToken(username);

            response.setId(1);
            response.setUser(authenticationRequest.getUsername());
            response.setAccess_token(token);

            return ResponseEntity.ok(response);
        }
    }
}