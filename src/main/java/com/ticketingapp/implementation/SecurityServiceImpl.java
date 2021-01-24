package com.ticketingapp.implementation;

import com.ticketingapp.entity.User;
import com.ticketingapp.entity.common.UserPrincipal;
import com.ticketingapp.repository.UserRepository;
import com.ticketingapp.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s);
        if(user == null)
            throw new UsernameNotFoundException("User does not exist!");
        return new UserPrincipal(user);
    }
}
