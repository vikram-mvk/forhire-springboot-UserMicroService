package com.ForHire.UserMicroService.Controller;

import com.ForHire.UserMicroService.DTO.JwtResponseDTO;
import com.ForHire.UserMicroService.DTO.LoginDTO;
import com.ForHire.UserMicroService.Entity.User;
import com.ForHire.UserMicroService.Repository.UserRepository;
import com.ForHire.UserMicroService.Security.JWTUtils.JwtUtils;
import com.ForHire.UserMicroService.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserAPI_IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired private RestTemplate restTemplate;

    @Test
    void getUserInRequest() {
    try{
        JwtResponseDTO tester = getTester(restTemplate);
        RequestBuilder request = MockMvcRequestBuilders.post("/user/api/getUserInRequest").header("Authorization", "Bearer " + tester.getToken());
        User testUser = new User();
        testUser.setId(tester.getId());
        Mockito.when(userService.findUserByUserName(anyString())).thenReturn(testUser);
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        assertEquals(tester.getId().toString(), result.getResponse().getContentAsString());
        System.out.println("Response from /user/api/getUserInRequest  " + result.getResponse().getContentAsString());

    } catch (Exception e) {
        e.printStackTrace();
        fail(e.getCause());
    }
    }

    @Test
    void getUserFromId() {
        try{
            JwtResponseDTO tester = getTester(restTemplate);
            RequestBuilder request = MockMvcRequestBuilders.post("/user/api/getUserNameFromId").param("id",Long.toString(tester.getId())).header("Authorization", "Bearer " + tester.getToken());
            User testUser = new User();
            testUser.setUserName(tester.getUsername());
            Mockito.when(userService.findUserById(anyLong())).thenReturn(testUser);
            MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            assertEquals(tester.getUsername(), result.getResponse().getContentAsString());
            System.out.println("Response from /user/api/getUserInRequest  " + result.getResponse().getContentAsString());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getCause());
        }

    }


    //Secured API, So need to login to test. Hence using Dummy Account "Junit" for testing
    public JwtResponseDTO getTester(RestTemplate restTemplate){
        LoginDTO login = new LoginDTO("Junit", "junit");
        HttpEntity<LoginDTO> loginReq = new HttpEntity<>(login);
        ResponseEntity<JwtResponseDTO> jwtResponse = restTemplate.exchange("http://GATEWAY-MICROSERVICE/auth/login", HttpMethod.POST, loginReq, JwtResponseDTO.class);
        return jwtResponse.getBody();
    }


}