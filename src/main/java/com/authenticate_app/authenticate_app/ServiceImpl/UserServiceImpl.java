package com.authenticate_app.authenticate_app.ServiceImpl;

import com.authenticate_app.authenticate_app.Modal.AuthRespons;
import com.authenticate_app.authenticate_app.Modal.Role;
import com.authenticate_app.authenticate_app.Modal.User;
import com.authenticate_app.authenticate_app.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;
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
        String Token="validate";
        userRepository.save(user);
        return new AuthRespons(Token,"register with success");
    }
    public Boolean ValidateRegistery(User userReq){
        return !userReq.getUsername().isEmpty() && !userReq.getFirst_name().isEmpty()
                && !userReq.getLast_name().isEmpty()
                && !userReq.getPassword_user().isEmpty() && !userReq.getRole().isEmpty();
    }

}
