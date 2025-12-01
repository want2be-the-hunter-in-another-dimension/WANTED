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

    // Quest 도메인 예외 처리
    @ExceptionHandler(QuestException.class)
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
                .orElse("잘못된 요청입니다.");

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("VALIDATION_ERROR", message));
    }

    // PathVariable / RequestParam 검증 실패 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .findFirst()
                .map(v -> v.getPropertyPath() + " : " + v.getMessage())
                .orElse("잘못된 요청입니다.");

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("VALIDATION_ERROR", message));
    }

    // JSON 파싱 오류 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("INVALID_JSON", "요청 본문을 읽을 수 없습니다. JSON 형식을 확인해주세요."));
    }

    // 필수 요청 파라미터 누락 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("MISSING_PARAMETER", e.getParameterName() + " 파라미터가 필요합니다."));
    }

    // 예상치 못한 서버 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse("INTERNAL-ERROR", "예기치 못한 오류가 발생했습니다."));
    }
}
