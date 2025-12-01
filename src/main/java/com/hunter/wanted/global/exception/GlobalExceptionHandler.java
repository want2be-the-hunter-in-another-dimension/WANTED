package com.hunter.wanted.global.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 애플리케이션 전역에서 발생하는 도메인 기반 Exception 처리
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.status())
                .body(ErrorResponse.from(errorCode));
    }

    /**
     * @Valid, @Validated 으로 바인딩 중 발생하는 Exception 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .orElse("잘못된 요청입니다.");

        ErrorResponse response = new ErrorResponse("VALIDATION_ERROR", message);

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * @RequestParam, @PathVariable 검증 실패 처리
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .findFirst()
                .map(v -> v.getPropertyPath() + " : " + v.getMessage())
                .orElse("잘못된 요청입니다.");

        ErrorResponse response = new ErrorResponse("VALIDATION_ERROR", message);

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * JSON 파싱 오류 처리
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException e) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_JSON",
                "요청 본문을 읽을 수 없습니다. JSON 형식을 확인해주세요."
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * 필수 요청 파라미터 누락 처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException e) {
        ErrorResponse response = new ErrorResponse(
                "MISSING_PARAMETER",
                e.getParameterName() + " 파라미터가 필요합니다."
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * 애플리케이션 전역에서 발생하는 예기치 못한 Exception 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        e.printStackTrace();

        ErrorResponse response = new ErrorResponse(
                "INTERNAL-ERROR",
                "예기치 못한 오류가 발생했습니다."
        );

        return ResponseEntity
                .status(500)
                .body(response);
    }
}
