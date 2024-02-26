package com.authenticate_app.authenticate_app.ServiceImpl;

import com.authenticate_app.authenticate_app.JWT.JwtService;
import com.authenticate_app.authenticate_app.Modal.AuthRespons;
import com.authenticate_app.authenticate_app.Modal.User;
import com.authenticate_app.authenticate_app.Repository.TokenRepository;
import com.authenticate_app.authenticate_app.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    JwtService jwtService;
    public AuthRespons Register(User userReq){

        Optional<User> findUsername= Optional.ofNullable(userRepository.findByUsername(userReq.getUsername()));
        if(findUsername.isPresent()){
            return new AuthRespons(null ,"this username is already exist");
        }
        User user=new User();
        user.setUsername(userReq.getUsername());
        user.setPassword_user(userReq.getPassword_user());
        user.setFirst_name(userReq.getFirst_name());
        user.setLast_name(userReq.getLast_name());
        user.setRole(userReq.getRole());
        userRepository.save(user);

//        Token token=new Token();
//        String Tk=jwtService.GenerateToken(user);
//        token.setUser(user);
//        token.setToken(Tk);
//        token.setLoggedout(false);
//        tokeRepository.save(token);

        return new AuthRespons("test token","register with success");
    }
    public Boolean ValidateRegistery(User userReq){
        return !userReq.getUsername().isEmpty() && !userReq.getFirst_name().isEmpty()
                && !userReq.getLast_name().isEmpty()
                && !userReq.getPassword_user().isEmpty() && !userReq.getRole().isEmpty();
    }

}
