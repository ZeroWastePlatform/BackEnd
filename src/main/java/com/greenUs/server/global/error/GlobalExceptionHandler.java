package com.greenUs.server.global.error;

import com.greenUs.server.attachment.exception.FailConvertOutputStream;
import com.greenUs.server.attachment.exception.FailResizeAttachment;
import com.greenUs.server.attachment.exception.NotEqualAttachmentAndPostAttachment;
import com.greenUs.server.attachment.exception.NotFoundObjectException;
import com.greenUs.server.auth.exception.EmptyAuthorizationHeaderException;
import com.greenUs.server.auth.exception.InvalidTokenException;
import com.greenUs.server.auth.exception.RefreshTokenNotExistException;
import com.greenUs.server.basket.exception.NotEqualMemberAndBasketMember;
import com.greenUs.server.basket.exception.NotFoundBasketException;
import com.greenUs.server.comment.exception.NotEqualCommentAndPostComment;
import com.greenUs.server.comment.exception.NotEqualMemberAndCommentMember;
import com.greenUs.server.comment.exception.NotFoundCommentException;
import com.greenUs.server.infrastructure.oauth.exception.OAuthException;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.post.exception.NotEqualMemberAndPostMember;
import com.greenUs.server.post.exception.NotFoundPostException;

import com.greenUs.server.product.exception.LikeDuplicateException;
import com.greenUs.server.product.exception.NotFoundProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidTokenException.class, RefreshTokenNotExistException.class})
    public ResponseEntity<ErrorResponse> handleInvalidAuthorization(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_TOKEN);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(EmptyAuthorizationHeaderException.class)
    public ResponseEntity<ErrorResponse> handleEmptyAuthorizationHeader(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.EMPTY_TOKEN_HEADER);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({NotFoundMemberException.class, NotFoundProductException.class})
    public ResponseEntity<ErrorResponse> handleNotFound() {
        ErrorResponse response = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(OAuthException.class)
    public ResponseEntity<ErrorResponse> handleOAuthServerError(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.OAUTH_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(NotFoundPostException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundPost() {
        ErrorResponse response = new ErrorResponse(ErrorCode.POST_NOT_FOUND);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundCommentException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundComment() {
        ErrorResponse response = new ErrorResponse(ErrorCode.COMMENT_NOT_FOUND);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotEqualMemberAndPostMember.class)
    public ResponseEntity<ErrorResponse> handleNotEqualMemberAndPostMember() {
        ErrorResponse response = new ErrorResponse(ErrorCode.POST_MEMBER_NOT_EQUAL);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotEqualMemberAndCommentMember.class)
    public ResponseEntity<ErrorResponse> handleNotEqualMemberAndCommentMember() {
        ErrorResponse response = new ErrorResponse(ErrorCode.COMMENT_MEMBER_NOT_EQUAL);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotEqualCommentAndPostComment.class)
    public ResponseEntity<ErrorResponse> handleNotEqualCommentAndPostComment() {
        ErrorResponse response = new ErrorResponse(ErrorCode.POST_COMMENT_NOT_EQUAL);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotEqualAttachmentAndPostAttachment.class)
    public ResponseEntity<ErrorResponse> handleNotEqualAttachmentAndPostAttachment() {
        ErrorResponse response = new ErrorResponse(ErrorCode.POST_ATTACHMENT_NOT_EQUAL);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(FailConvertOutputStream.class)
    public ResponseEntity<ErrorResponse> handleFailConvertOutputStream() {
        ErrorResponse response = new ErrorResponse(ErrorCode.OUTPUT_STREAM_ERROR);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundObjectException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundObjectException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.OBJECT_NOT_FOUND);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(FailResizeAttachment.class)
    public ResponseEntity<ErrorResponse> handleFailResizeAttachment() {
        ErrorResponse response = new ErrorResponse(ErrorCode.RESIZE_SERVER_ERROR);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(LikeDuplicateException.class)
    public ResponseEntity<ErrorResponse> likeDuplicate() {
        ErrorResponse response = new ErrorResponse(ErrorCode.LIKE_DUPLICATE);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(NotEqualMemberAndBasketMember.class)
    public ResponseEntity<ErrorResponse> handleNotEqualMemberAndBasketMember() {
        ErrorResponse response = new ErrorResponse(ErrorCode.BASKET_MEMBER_NOT_EQUAL);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundBasketException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundBasketException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.BASKET_NOT_FOUND);
        return ResponseEntity.badRequest().body(response);
    }
}
