package com.easyexit.EasyExit.Services;

import com.easyexit.EasyExit.Entity.User;
import com.easyexit.EasyExit.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User register(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
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
}
