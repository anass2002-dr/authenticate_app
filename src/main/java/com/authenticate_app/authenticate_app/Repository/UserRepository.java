package com.authenticate_app.authenticate_app.Repository;

import com.authenticate_app.authenticate_app.Modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
//    @Query("select u from User u where username=:UserName")
//    User findByUsername(@Param("UserName") String UserName);
    Optional<User> findByUsername(String username);
}
