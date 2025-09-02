package com.easyexit.EasyExit.Services;

import com.easyexit.EasyExit.Entity.User;
import com.easyexit.EasyExit.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUser(String username) {
        return userRepo.findByRollNo(username);
    }

    public User register(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        user.setPassword("");
        return user;
    }

    public String verify(User user) {
        try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getRollNo(), user.getPassword()));
            if (authentication.isAuthenticated())
                return jwtService.generateToken(user.getRollNo(), user.getRole());
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid roll number or password.");
        }
        throw new BadCredentialsException("Authentication failed.");
    }

    public User getProfile() {
        UserDetails user = authService.getCurrentUser();

        if (user == null) {
            throw new IllegalStateException("User is not authenticated");
        }

        User user1 = userRepo.findByRollNo(user.getUsername());
        if (user1 == null) {
            throw new IllegalStateException("User profile not found.");
        }
        user1.setPassword(null);
        return user1;
    }
}
