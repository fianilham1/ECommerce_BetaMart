package com.betamart.service.impl;

import com.betamart.common.util.HashSaltPasswordUtil;
import com.betamart.model.User;
import com.betamart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomAuthenticationProviderServiceImpl implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HashSaltPasswordUtil hashSaltPasswordUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = null;

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByUsername(username);
        try{
            if (username.equals(user.getUsername()) && hashSaltPasswordUtil.validatePassword(password, user.getPassword())){
                Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(user);
                authenticationToken = new UsernamePasswordAuthenticationToken(
                        new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities),password,grantedAuthorities);
            }
        }catch (Exception ex){
            System.out.println("error authenticate : "+ex);
        }
        return authenticationToken;
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user){
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(user.getRole().getName().equals("admin")){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

