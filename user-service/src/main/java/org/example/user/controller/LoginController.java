package org.example.user.controller;

import com.netflix.discovery.util.StringUtil;
import org.example.user.model.Token;
import org.example.user.model.User;
import org.example.user.model.UserDto;
import org.example.user.repository.UserRepository;
import org.example.user.service.TokenGenerator;
import org.example.user.service.TokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class LoginController {
    private final String BEARER_TOKEN = "Bearer ";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenVerifier tokenVerifier;

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public Token login(@RequestBody UserDto userDto) {
        return tokenGenerator.generateToken(userDto);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public User signUp(@RequestBody UserDto userDto) {
        User user  = new User()
                .setEmail(userDto.getEmail())
                .setPassword(passwordEncoder.encode(userDto.getPassword()))
                .setGrantedAuthorities(Set.of("USER"));
        return userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/verify")
    @ResponseStatus(HttpStatus.OK)
    public Boolean verify(@RequestParam String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_TOKEN)) {
            tokenVerifier.verify(token.substring(BEARER_TOKEN.length()));
            return true;
        }
        return false;
    }
}
