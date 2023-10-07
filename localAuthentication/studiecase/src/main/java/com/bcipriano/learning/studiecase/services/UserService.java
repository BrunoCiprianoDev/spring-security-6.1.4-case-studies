package com.bcipriano.learning.studiecase.services;

import com.bcipriano.learning.studiecase.domain.User;
import com.bcipriano.learning.studiecase.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }

    public void createNewUser(User user) {
        if(this.userRepository.findByLogin(user.getLogin()) != null){
            throw new RuntimeException("User with this login already exists.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    public void updateUser(User userAuthenticated, User newUser) {
        userAuthenticated.setFirstName(newUser.getFirstName());
        userAuthenticated.setLastName(newUser.getLastName());
        userRepository.save(userAuthenticated);
    }

}