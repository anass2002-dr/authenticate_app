package com.authenticate_app.authenticate_app.Repository;

import com.authenticate_app.authenticate_app.Modal.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    @Query("""
select t from Token t inner join User u on t.user.user_id = u.user_id
where t.user.user_id = :userId and t.loggedout = false
""")
    List<Token> findAllTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);

}
