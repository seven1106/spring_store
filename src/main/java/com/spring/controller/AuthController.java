package com.spring.controller;


import com.spring.config.JwtProvider;
import com.spring.model.Cart;
import com.spring.model.USER_ROLE;
import com.spring.model.User;
import com.spring.repository.CartRepository;
import com.spring.repository.UserRepository;
import com.spring.request.LoginRequest;
import com.spring.response.AuthResponse;
import com.spring.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;

    @RequestMapping("/register")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Email is already used by another account");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setName(user.getName());
        User savedUser = userRepository.save(newUser);
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("User registered successfully");
        authResponse.setRole(savedUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @RequestMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) {
        String email = req.getEmail();
        String password = req.getPassword();
        Authentication authentication = authentication(email, password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("User logged in successfully");
        authResponse.setRole(userRepository.findByEmail(email).getRole());
//        authResponse.setRole(USER_ROLE.valueOf(roles));
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    private Authentication authentication(String email, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        if(userDetails == null) {
            throw new RuntimeException("User not found");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
