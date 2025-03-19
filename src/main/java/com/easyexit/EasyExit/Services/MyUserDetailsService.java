package com.easyexit.EasyExit.Services;

import com.easyexit.EasyExit.Entity.User;
import com.easyexit.EasyExit.Entity.UserPrincipal;
import com.easyexit.EasyExit.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByRollNo(s);
        if(user == null){
            throw new UsernameNotFoundException("User with roll number " + s + " not found");
        }
        return new UserPrincipal(user);
    }
}
