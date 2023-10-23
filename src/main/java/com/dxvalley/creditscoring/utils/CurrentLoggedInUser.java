package com.dxvalley.creditscoring.utils;

import com.dxvalley.creditscoring.exceptions.customExceptions.BannedUserException;
import com.dxvalley.creditscoring.exceptions.customExceptions.ResourceNotFoundException;
import com.dxvalley.creditscoring.exceptions.customExceptions.UnauthorizedException;
import com.dxvalley.creditscoring.userManager.user.UserRepository;
import com.dxvalley.creditscoring.userManager.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentLoggedInUser {

    private final UserRepository userRepository;

    public Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            throw new UnauthorizedException("Access denied. Please provide a valid authentication token.");

        // If a user changes his or her email address, he or she must log in again.
        Users user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Please login with a new email and try again."));

        if (user.getUserStatus() == Status.BANNED)
            throw new BannedUserException();

        return user;
    }
}