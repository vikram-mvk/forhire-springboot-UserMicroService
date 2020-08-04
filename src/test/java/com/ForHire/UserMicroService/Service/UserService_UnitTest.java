package com.ForHire.UserMicroService.Service;

import com.ForHire.UserMicroService.Entity.User;
import com.ForHire.UserMicroService.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserService_UnitTest {
    @Autowired private UserService userService;
    @MockBean  private UserRepository userRepository;

    @Test
    void findUserById() {
        User u = new User();
        u.setId(Long.parseLong("1"));
        Optional<User> op = Optional.of(u);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(op);
        assertEquals(op.get(),userService.findUserById(Long.parseLong("1")));

    }

    @Test
    void findUserByUserName() {
        User u = new User();
        u.setUserName("test");
        Optional<User> op = Optional.of(u);
        Mockito.when(userRepository.findByUserName(anyString())).thenReturn(op);
        assertEquals(op.get(),userService.findUserByUserName("test"));

    }
}