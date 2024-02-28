package com.authenticate_app.authenticate_app.ServiceImpl;

import com.authenticate_app.authenticate_app.JWT.JwtService;
import com.authenticate_app.authenticate_app.Modal.AuthRespons;
import com.authenticate_app.authenticate_app.Modal.Token;
import com.authenticate_app.authenticate_app.Modal.User;
import com.authenticate_app.authenticate_app.Repository.TokenRepository;
import com.authenticate_app.authenticate_app.Repository.UserRepository;
import com.authenticate_app.authenticate_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public AuthRespons Register(User userReq){

        Optional<User> findUsername= userRepository.findByUsername(userReq.getUsername());
        if(findUsername.isPresent()){
            return new AuthRespons(null ,"this username is already exist");
        }
        User user=new User();
        user.setUsername(userReq.getUsername());
        user.setPassword_user(passwordEncoder.encode(userReq.getPassword()));
        user.setFirst_name(userReq.getFirst_name());
        user.setLast_name(userReq.getLast_name());
        user.setRole(userReq.getRole());
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);
        return new AuthRespons(jwt,"register with success");
    }
    @Override
    public AuthRespons Login(User UserReq){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        UserReq.getUsername(),
                        UserReq.getPassword()
                )
        );

        User user = userRepository.findByUsername(UserReq.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthRespons(jwt, "User login was successful");
    }
    @Override
    public Boolean ValidateRegistery(User userReq){
        return !userReq.getUsername().isEmpty() && !userReq.getFirst_name().isEmpty()
                && !userReq.getLast_name().isEmpty()
                && !userReq.getPassword_user().isEmpty() && !userReq.getRole().isEmpty();
    }
    @Override
    public void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getUser_id());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedout(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    @Override
    public void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedout(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
