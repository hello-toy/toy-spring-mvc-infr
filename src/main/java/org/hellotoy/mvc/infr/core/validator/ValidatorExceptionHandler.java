package org.hellotoy.mvc.infr.core.validator;

import org.hellotoy.mvc.infr.api.out.MessageEnum;
import org.hellotoy.mvc.infr.api.out.ResultResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidatorExceptionHandler {
	@ExceptionHandler(BindException.class)
    public ResultResponse<String> notValidException(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder stringBuilder = new StringBuilder();
        allErrors.forEach(objectError -> stringBuilder.append(objectError.getDefaultMessage()).append(";"));
        return ResultResponse.build(MessageEnum.ILLEGAL_ARGS, stringBuilder.toString());
    }
}
