package com.authenticate_app.authenticate_app.JWT;

import com.authenticate_app.authenticate_app.Modal.Token;
import com.authenticate_app.authenticate_app.Modal.User;
import com.authenticate_app.authenticate_app.Repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtService {
    public String SecretKey="4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";

    @Autowired
    TokenRepository tokenRepository;
    public String ExtractUsername(String Token){
        return ExtractClaim(Token,Claims::getSubject);
    }

    public Boolean IsTokenExpired(String Token){
        return ExtractClaim(Token,Claims::getExpiration).before(new Date());
    }
    public <T> T ExtractClaim(String token, Function<Claims,T> claimsFunction){
        Claims claims=ExtractAllClaims(token);
        return claimsFunction.apply(claims);
    }

    public Boolean IsValideToken(String Token, UserDetails userDetails){
        return userDetails.getUsername().equals(ExtractUsername(Token)) && !IsTokenExpired(Token);
    }
    public Claims ExtractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(signkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
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
