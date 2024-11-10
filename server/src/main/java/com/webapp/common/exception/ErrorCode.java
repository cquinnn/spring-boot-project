package com.webapp.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
  USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
  USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
  PASSWORD_INVALID(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
  USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
  UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
  UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
  INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
  VIDEO_NOT_EXISTED(2005, "Video title not existed", HttpStatus.NOT_FOUND),
  VIDEO_TITLE_DUPLICATED(2006, "Video title existed", HttpStatus.BAD_REQUEST),
  VIDEO_STORAGE_FAILURE(2007, "Could not save the video", HttpStatus.INTERNAL_SERVER_ERROR);
  private int code;
  private String message;
  private HttpStatusCode statusCode;

  private ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }
}
