package com.javaweb.controllerAdvice;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javaweb.CustomException.FieldRequireException;
import com.javaweb.model.ErrorResponeDTO;
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler{
	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(
            ArithmeticException ex, WebRequest request) {

       ErrorResponeDTO errorResponeDTO=new ErrorResponeDTO();
       errorResponeDTO.setError(ex.getMessage());
       List<String>details=new ArrayList<String>();
       details.add("Số nguyên làm sao chia được cho không!");
       errorResponeDTO.setDetail(details);
        return new ResponseEntity<>( errorResponeDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	@ExceptionHandler(FieldRequireException.class)
	public ResponseEntity<Object> handleFieldRequiredException(
            FieldRequireException ex, WebRequest request) {

       ErrorResponeDTO errorResponeDTO=new ErrorResponeDTO();
       errorResponeDTO.setError(ex.getMessage());
       List<String>details=new ArrayList<String>();
       details.add("Check lại name hoặc numberOfBasement đi bởi vì đang bị null đó!");
       errorResponeDTO.setDetail(details);
        return new ResponseEntity<>( errorResponeDTO, HttpStatus.BAD_GATEWAY);
    }
}
