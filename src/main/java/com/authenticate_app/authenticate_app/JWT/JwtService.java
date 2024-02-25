package com.authenticate_app.authenticate_app.JWT;

import com.authenticate_app.authenticate_app.Modal.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtService {
    public String SecretKey="Anaefvverss--Desderma51646547@77her/wrj64hbwfh2924797876924";
    public String GenerateToken(User user){
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .signWith(signkey())
                .compact();

    }
    public SecretKey signkey(){
        byte [] keybytes= Decoders.BASE64.decode(SecretKey);
        return Keys.hmacShaKeyFor(keybytes);
    }
}
