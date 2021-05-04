package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    /*@Autowired
    UserRepo userRepo;*/
    private final UserRepo userRepo;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.getUserDetails(user);
        /*List<User> users = userRepo.findAll();
        User user = null;
        User currentUser = null;
        Iterator iter = users.iterator();
        while (iter.hasNext()){
             user = (User)iter.next();
            if (user.getEmail().equals(email))
                currentUser= user;
        }

        return  SecurityUser.getUserDetails(currentUser);*/
    }


}
