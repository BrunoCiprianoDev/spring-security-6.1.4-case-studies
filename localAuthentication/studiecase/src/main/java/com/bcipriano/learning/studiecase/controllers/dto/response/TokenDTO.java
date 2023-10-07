package com.bcipriano.learning.studiecase.controllers.dto.response;

import com.bcipriano.learning.studiecase.domain.Token;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class TokenDTO {

    private final String login;
    private final String firstName;
    private final String lastName;
    private final String role;
    private final boolean authenticated;
    private final String created;
    private final String expiration;
    private final String accessToken;

    public TokenDTO(Token token) {
        this.login = token.getLogin();
        this.firstName = token.getFirstName();
        this.lastName = token.getLastName();
        this.role = token.getUserRole().name();
        this.authenticated = token.isAuthenticated();
        this.created = this.DateFormat(token.getCreated());
        this.expiration = this.DateFormat(token.getExpiration());
        this.accessToken = token.getAccessToken();
    }

    private String DateFormat(Date date) {
        Date data = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }


}