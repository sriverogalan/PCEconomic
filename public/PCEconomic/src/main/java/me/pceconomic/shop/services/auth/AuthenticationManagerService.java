package me.pceconomic.shop.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationManagerService implements AuthenticationManager {

    @Autowired
    private AuthenticationProviderService authenticationProviderService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authenticationProviderService.authenticate(authentication);
    }
}
