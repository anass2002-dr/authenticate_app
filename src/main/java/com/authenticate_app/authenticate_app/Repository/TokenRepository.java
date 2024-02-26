package com.authenticate_app.authenticate_app.Repository;

import com.authenticate_app.authenticate_app.Modal.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    Token findByToken(String token);
}
