package com.bcipriano.learning.studiecase.controllers.dto.request;

public record UpdatePasswordDTO(
 String login,


        String password,

        String newPassword

) {
}