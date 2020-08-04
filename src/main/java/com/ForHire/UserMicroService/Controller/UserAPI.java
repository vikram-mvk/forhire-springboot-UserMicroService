package com.ForHire.UserMicroService.Controller;


import com.ForHire.UserMicroService.Entity.User;
import com.ForHire.UserMicroService.Repository.UserRepository;
import com.ForHire.UserMicroService.Security.JWTUtils.JwtUtils;
import com.ForHire.UserMicroService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user/api")
public class UserAPI {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/getUserInRequest")
    public ResponseEntity<?> getUserInRequest(HttpServletRequest req){
        String jwt = jwtUtils.parseJwt(req);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        User this_user = userService.findUserByUserName(username);
        if(this_user==null)   return ResponseEntity.status(400).body("Can't get User from JWT. User doesn't exist or JWT is not valid");
        return ResponseEntity.status(200).body(this_user.getId());
    }

    @PostMapping("/getUserNameFromId")
    public ResponseEntity<?> getUserFromId(@RequestParam("id") Long id){
        User user = userService.findUserById(id);
        if(user==null)  return ResponseEntity.status(404).body("Can't get User from ID. User doesn't exist or invalid ID");
        return ResponseEntity.status(200).body(user.getUserName());
    }

}
