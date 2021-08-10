package com.dnd10.iterview.advice;

import com.dnd10.iterview.advice.exception.ErrorMessage;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
    log.error("{}",request.getRequestURL());
    log.error("{} :: @Valid parameter 에 유효하지 않은 값이 전달되었습니다.",e.getMessage(),e);
    return getErrorMessage(e.getMessage(), "유효하지 않은 값이 전달되었습니다.");
  }

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage nullPointerException(HttpServletRequest request, NullPointerException e) {
    log.error("{}",request.getRequestURL());
    log.error("{} :: null 인 객체를 참조하였습니다.",e.getMessage(),e);
    return getErrorMessage(e.getMessage(), "유효하지 않은 값이 전달되었습니다.");
  }
  @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage arrayIndexOutOfBoundsException(HttpServletRequest request, ArrayIndexOutOfBoundsException e) {
    log.error("{}",request.getRequestURL());
    log.error("{} :: index 범위를 벗어나는 참조가 발생했습니다.",e.getMessage());
    return getErrorMessage(e.getMessage(), "유효하지 않은 값이 전달되었습니다.");
  }
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage arrayIndexOutOfBoundsException(HttpServletRequest request, IllegalArgumentException e) {
    log.error("{}",request.getRequestURL());
    log.error("{} :: 잘못된 인자가 전달되었습니다.",e.getMessage());
    return getErrorMessage(e.getMessage(), "유효하지 않은 값이 전달되었습니다.");
  }
  @ExceptionHandler(OAuth2AuthenticationException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage oAuth2AuthenticationException(HttpServletRequest request, OAuth2AuthenticationException e) {
    log.error("{}",request.getRequestURL());
    return getErrorMessage(e.getMessage(), "유효하지 않은 값이 전달되었습니다.");
  }
  @ExceptionHandler(PropertyReferenceException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage propertyReferenceException(HttpServletRequest request, PropertyReferenceException e) {
    log.error("{}",request.getRequestURL());
    return getErrorMessage(e.getMessage(), "유효하지 않은 값이 전달되었습니다.");
  }
  @ExceptionHandler(IndexOutOfBoundsException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage indexOutOfBoundsException(HttpServletRequest request, IndexOutOfBoundsException e) {
    log.error("{}",request.getRequestURL());
    return getErrorMessage(e.getMessage(), "범위를 초과한 값이 전달되었습니다.");
  }

  private ErrorMessage getErrorMessage(String message, String description) {
    return ErrorMessage.builder()
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .timestamp(new Date())
        .message(message)
        .description(description)
        .build();
  }
}
