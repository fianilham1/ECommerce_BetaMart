package com.betamart.module.user.controller;

import com.betamart.base.constant.CommonMessage;
import com.betamart.base.util.HashSaltPasswordUtil;
import com.betamart.base.util.MapperUtil;
import com.betamart.base.util.RegexUtil;
import com.betamart.model.Role;
import com.betamart.model.User;
import com.betamart.module.role.payload.response.RoleResponse;
import com.betamart.module.user.payload.request.AuthRequest;
import com.betamart.module.user.payload.request.NewPassRequest;
import com.betamart.module.user.payload.response.AuthResponse;
import com.betamart.base.payload.response.BaseResponse;
import com.betamart.module.user.payload.response.ForgotPassTokenResponse;
import com.betamart.repository.RoleRepository;
import com.betamart.repository.UserRepository;
import com.betamart.service.UserService;
import com.betamart.base.util.JwtUtil;
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
    public Object generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword())
            );
        } catch (Exception ex) {
            System.out.println("error "+ex);
            return new BaseResponse<>(CommonMessage.AUTH_ERROR);
        }
        System.out.println("token created!");
        String token = jwtUtil.generateToken(authRequest.getUsername());
        User user = userRepository.findByUsername(authRequest.getUsername());
        RoleResponse roleResponse = MapperUtil.parse(user.getRole(),RoleResponse.class, MatchingStrategies.STRICT);

        AuthResponse authResponse = new AuthResponse(user.getUsername(), user.getPassword(), roleResponse, "Bearer " + token);
        return new BaseResponse<>(CommonMessage.OK, authResponse);
    }

    @PostMapping("/register")
    public Object register(@RequestBody AuthRequest user) {
        try {
            boolean emailTrue = RegexUtil.isValidEmail(user.getUsername());
            boolean passwordTrue = RegexUtil.isValidPassword(user.getPassword());

            if (emailTrue && passwordTrue) {
                User userDB = userRepository.findByUsername(user.getUsername());
                if (userDB == null){
                    String securedPassword = hashSaltPasswordUtil.generateStrongPasswordHash(user.getPassword());
                    Role role = roleRepository.findById(user.getRoleId()).get();
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
    public Object resetPassword(@RequestBody NewPassRequest updatedUser, @RequestHeader("Authorization") String token) throws IOException {
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
