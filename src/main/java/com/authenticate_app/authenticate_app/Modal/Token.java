package com.authenticate_app.authenticate_app.Modal;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_token;
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Boolean loggedout;
}
