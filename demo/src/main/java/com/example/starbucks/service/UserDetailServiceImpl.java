package com.example.starbucks.service;

import com.example.starbucks.model.UserCustom;
import com.example.starbucks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

//UserDetailsService는 spring이 제공하는 함수임
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCustom userCustom = userRepository.findByUserId(username);

        //username이 없다면
        if(userCustom ==null) throw new UsernameNotFoundException("there is no User");
        return new User(userCustom.getUserId(),userCustom.getPassword(),Collections.emptyList());
    }
}
