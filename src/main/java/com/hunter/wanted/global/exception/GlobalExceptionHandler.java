package com.hunter.wanted.global.exception;

import com.hunter.wanted.global.quest.domain.exception.QuestException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 도메인 예외 처리
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleQuestException(QuestException e) {
        return ResponseEntity
                .status(e.getErrorCode().status())
                .body(ErrorResponse.from(e.getErrorCode()));
    }

    // DTO 검증 오류 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .orElse(GlobalErrorCode.VALIDATION_ERROR.message());

        return ResponseEntity
                .status(GlobalErrorCode.VALIDATION_ERROR.status())
                .body(new ErrorResponse(GlobalErrorCode.VALIDATION_ERROR.code(), message));
    }

    // PathVariable / RequestParam 검증 실패 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .findFirst()
                .map(v -> v.getPropertyPath() + " : " + v.getMessage())
                .orElse(GlobalErrorCode.VALIDATION_ERROR.message());

        return ResponseEntity
                .status(GlobalErrorCode.VALIDATION_ERROR.status())
                .body(new ErrorResponse(GlobalErrorCode.VALIDATION_ERROR.code(), message));
    }

    // JSON 파싱 오류 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(GlobalErrorCode.INVALID_JSON.status())
                .body(new ErrorResponse(GlobalErrorCode.INVALID_JSON.code(), GlobalErrorCode.INVALID_JSON.message()));
    }

    // 필수 요청 파라미터 누락 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException e) {
        String message = e.getParameterName() + " 파라미터가 필요합니다.";
        return ResponseEntity
                .status(GlobalErrorCode.MISSING_PARAMETER.status())
                .body(new ErrorResponse(GlobalErrorCode.MISSING_PARAMETER.code(), message));
    }

    // 예상치 못한 서버 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_ERROR.status())
                .body(new ErrorResponse(GlobalErrorCode.INTERNAL_ERROR.code(), GlobalErrorCode.INTERNAL_ERROR.message()));
    }
}
