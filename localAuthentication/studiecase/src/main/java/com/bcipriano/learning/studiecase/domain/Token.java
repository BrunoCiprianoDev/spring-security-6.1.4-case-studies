package com.bcipriano.learning.studiecase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private String login;
    private boolean authenticated;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private Date created;
    private Date expiration;
    private String accessToken;
}