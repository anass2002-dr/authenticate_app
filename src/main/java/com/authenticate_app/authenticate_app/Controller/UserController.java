package com.authenticate_app.authenticate_app.Controller;

import com.authenticate_app.authenticate_app.Modal.AuthRespons;
import com.authenticate_app.authenticate_app.Modal.User;
import com.authenticate_app.authenticate_app.ServiceImpl.UserServiceImpl;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = {"/user","/"})

public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public AuthRespons Register(@RequestBody(required = true)User  userReq){
        if(!userServiceImpl.ValidateRegistery(userReq)){
            return new AuthRespons(null,"All inputs is required");
        }
        return userServiceImpl.Register(userReq);
    }
    @GetMapping("/")
    public String Index(){
        return "this is index page";
    }
}
