package com.easyexit.EasyExit.Controllers;

import com.easyexit.EasyExit.Entity.User;
import com.easyexit.EasyExit.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByRollNo(@PathVariable String username){
        return new ResponseEntity<User>(userService.getUser(username), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        if (user.getName() == null || user.getName().trim().isEmpty() ||
                user.getRollNo() == null || user.getRollNo().trim().isEmpty() ||
                user.getPassword() == null ||
                user.getRole() == null) {
            return new ResponseEntity<>("\"Invalid input! Name, Roll No, Password and Role are required.\"",HttpStatus.BAD_REQUEST);
        }
        if( user.getPassword().length() < 6)
            return new ResponseEntity<>("Invalid Password! Password must be min 6 chars in length", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<User>(userService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        if (user.getRollNo() == null || user.getRollNo().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new ResponseEntity<>("Roll number and password are required.", HttpStatus.BAD_REQUEST);
        }

        String verificationResponse = userService.verify(user);
        if (verificationResponse.equals("Invalid credentials")) {
            return new ResponseEntity<>("Invalid roll number or password.",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(verificationResponse,HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(){
        return new ResponseEntity<User>(userService.getProfile(),HttpStatus.OK);
    }
}
