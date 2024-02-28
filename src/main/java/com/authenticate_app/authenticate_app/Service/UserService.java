package com.authenticate_app.authenticate_app.Service;

import com.authenticate_app.authenticate_app.Modal.AuthRespons;
import com.authenticate_app.authenticate_app.Modal.User;

public interface UserService {
    public AuthRespons Register(User userReq);
    public AuthRespons Login(User UserReq);
    public Boolean ValidateRegistery(User userReq);
    public void revokeAllTokenByUser(User user);
    public void saveUserToken(String jwt, User user);

}
