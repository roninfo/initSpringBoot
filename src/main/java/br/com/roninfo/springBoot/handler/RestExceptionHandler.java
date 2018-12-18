package br.com.roninfo.springBoot.handler;

import br.com.roninfo.springBoot.error.ErrorDetail;
import br.com.roninfo.springBoot.error.ResourceNotFoundDetails;
import br.com.roninfo.springBoot.error.ResourceNotFoundException;
import br.com.roninfo.springBoot.error.ValidationErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundExcpetion(ResourceNotFoundException rnfException) {
        ResourceNotFoundDetails notFoundDetails = ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(rnfException.getMessage())
                .developerMessage(rnfException.getClass().getName())
                .build();

        return new ResponseEntity<>(notFoundDetails, HttpStatus.NOT_FOUND);
    }

    // substituido pela sobrescrita do metodo
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//
//        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
//        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
//
//        ValidationErrorDetails notFoundDetails = ValidationErrorDetails.Builder
//                .newBuilder()
//                .timestamp(new Date().getTime())
//                .status(HttpStatus.BAD_REQUEST.value())
//                .title("Field Validation Error")
//                .detail("Field Validation Error")
//                .developerMessage(exception.getClass().getName())
//                .field(fields)
//                .fieldMessage(fieldMessages)
//                .build();
//
//        return new ResponseEntity<>(notFoundDetails, HttpStatus.BAD_REQUEST);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        ValidationErrorDetails notFoundDetails = ValidationErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Field Validation Error")
                .detail("Field Validation Error")
                .developerMessage(ex.getClass().getName())
                .field(fields)
                .fieldMessage(fieldMessages)
                .build();

        return new ResponseEntity<>(notFoundDetails, HttpStatus.BAD_REQUEST);
    }
//      Substituido pelo override de handleExceptionInternal
//    @Override
//    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ErrorDetail notFoundDetails = ErrorDetail.Builder
//                .newBuilder()
//                .timestamp(new Date().getTime())
//                .status(HttpStatus.BAD_REQUEST.value())
//                .title("Resource not found")
//                .detail(ex.getMessage())
//                .developerMessage(ex.getClass().getName())
//                .build();
//
//        return new ResponseEntity<>(notFoundDetails, HttpStatus.BAD_REQUEST);
//    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetail errorDetails = ErrorDetail.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(status.value())
                .title("Internal exception")
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();


        return new ResponseEntity(errorDetails, headers, status);
    }
}
