package com.Transaction.transaction.security;

import com.Transaction.transaction.entity.User;
import com.Transaction.transaction.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private  UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Email Not Found"));
        return org.springframework.security.core.userdetails.User.
                withUsername(user.getEmail()).
                password(user.getPassword()).disabled(user.isEnabled()).authorities(user.getAuthorities()).build();
    }
}
