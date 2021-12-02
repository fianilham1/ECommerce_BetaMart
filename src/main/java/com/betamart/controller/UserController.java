package com.betamart.controller;

import com.betamart.common.constant.CommonMessage;
import com.betamart.common.util.HashSaltPasswordUtil;
import com.betamart.common.util.MapperUtil;
import com.betamart.common.util.RegexUtil;
import com.betamart.model.Role;
import com.betamart.model.User;
import com.betamart.model.payloadResponse.RoleResponse;
import com.betamart.model.payloadResponse.AuthResponse;
import com.betamart.common.payload.response.BaseResponse;
import com.betamart.model.payloadResponse.ForgotPassTokenResponse;
import com.betamart.repository.RoleRepository;
import com.betamart.repository.UserRepository;
import com.betamart.service.UserService;
import com.betamart.common.util.JwtUtil;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HashSaltPasswordUtil hashSaltPasswordUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to BetaMart";
    }

    @PostMapping("/login")
    public Object generateToken(@RequestBody User userRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.getUsername(),
                            userRequest.getPassword())
            );
        } catch (Exception ex) {
            System.out.println("error "+ex);
            return new BaseResponse<>(CommonMessage.AUTH_ERROR);
        }
        System.out.println("token created!");
        String token = jwtUtil.generateToken(userRequest.getUsername());
        User user = userRepository.findByUsername(userRequest.getUsername());
        RoleResponse roleResponse = MapperUtil.parse(user.getRole(),RoleResponse.class, MatchingStrategies.STRICT);

        AuthResponse authResponse = new AuthResponse(user.getUsername(), user.getPassword(), roleResponse, "Bearer " + token);
        return new BaseResponse<>(CommonMessage.OK, authResponse);
    }

    @PostMapping("/register")
    public Object register(@RequestBody User user) {
        try {
            boolean emailTrue = RegexUtil.isValidEmail(user.getUsername());
            boolean passwordTrue = RegexUtil.isValidPassword(user.getPassword());

            if (emailTrue && passwordTrue) {
                User userDB = userRepository.findByUsername(user.getUsername());
                if (userDB == null){
                    String securedPassword = hashSaltPasswordUtil.generateStrongPasswordHash(user.getPassword());
                    Role role = roleRepository.findById(user.getRole().getId()).get();
                    User newUser = new User(user.getUsername(), securedPassword, role);
                    newUser.setCreatedBy(user.getUsername());
                    newUser.setCreatedDate(new Date());
                    userRepository.save(newUser);
                }else{
                    return new BaseResponse<>(CommonMessage.USERNAME_UNAVAILABLE);
                }
            } else {
                return new BaseResponse<>(CommonMessage.AUTH_INVALID_FORMAT);
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return new BaseResponse<>(CommonMessage.OK, user.getUsername());
    }

    @PostMapping("/forgot-password")
    public Object forgotPassword(@RequestParam String username) throws IOException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new BaseResponse<>(CommonMessage.USERNAME_NOT_FOUND);
        }

        System.out.println("token created!");
        String token = jwtUtil.generateToken(user.getUsername());
        ForgotPassTokenResponse forgotPassTokenResponse = new ForgotPassTokenResponse("Bearer "+token);
        return new BaseResponse<>(CommonMessage.OK, forgotPassTokenResponse);
    }

    @PutMapping("/reset-password")
    public Object resetPassword(@RequestBody User updatedUser, @RequestHeader("Authorization") String token) throws IOException {
        String username;
        boolean passwordTrue = RegexUtil.isValidPassword(updatedUser.getPassword());
        if(!passwordTrue){
            return new BaseResponse<>(CommonMessage.AUTH_INVALID_FORMAT);
        }
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
            User user = userRepository.findByUsername(username);
            user.setPassword(updatedUser.getPassword());
            user.setUpdatedBy(username);
            user.setUpdatedDate(new Date());
            userRepository.save(user);
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }

        return new BaseResponse<>(CommonMessage.PASSWORD_UPDATED,"Success");
    }


}
