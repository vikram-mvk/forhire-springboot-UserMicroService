package com.ForHire.UserMicroService.Service;

import com.ForHire.UserMicroService.Entity.User;
import com.ForHire.UserMicroService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserById(Long id){
        Optional<User> this_user = userRepository.findById(id);
        if(this_user.isPresent()==false) return null;
        return this_user.get();

    }
    public User findUserByUserName(String username){
        Optional<User> this_user = userRepository.findByUserName(username);
        if(this_user.isPresent()==false) return null;
        return this_user.get();
    }

}
