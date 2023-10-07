package com.bcipriano.learning.studiecase.controllers.dto.request;

public record RegisterDTO(

   String login,
        String firstName,

        String lastName,

        String password,

        String role) {
}