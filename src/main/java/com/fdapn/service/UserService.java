package com.fdapn.service;

import com.fdapn.auth.AuthenticationService;
import com.fdapn.dao.UserRepository;
import com.fdapn.exception.InvalidPasswordException;
import com.fdapn.model.ChangePasswordRequest;
import com.fdapn.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final AuthenticationService authenticationService;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        boolean isPasswordValidate = authenticationService.passwordValid(request.getNewPassword());
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Wrong password");
        }
        if (!isPasswordValidate) {
            throw new InvalidPasswordException("Please enter valid password.");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new InvalidPasswordException("Passwords do not match");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }


}
