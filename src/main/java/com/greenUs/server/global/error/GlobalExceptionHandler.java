package com.greenUs.server.global.error;

import com.greenUs.server.auth.exception.EmptyAuthorizationHeaderException;
import com.greenUs.server.auth.exception.InvalidTokenException;
import com.greenUs.server.infrastructure.oauth.exception.OAuthException;
import com.greenUs.server.member.exception.NotFoundMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuthorization(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_TOKEN);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(EmptyAuthorizationHeaderException.class)
    public ResponseEntity<ErrorResponse> handleEmptyAuthorizationHeader(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.EMPTY_TOKEN_HEADER);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ErrorResponse> handleNotFound() {
        ErrorResponse response = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(OAuthException.class)
    public ResponseEntity<ErrorResponse> handleOAuthServerError(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.OAUTH_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(response);
    }


}
