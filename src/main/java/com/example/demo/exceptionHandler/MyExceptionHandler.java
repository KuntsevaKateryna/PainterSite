package com.example.demo.exceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
public class MyExceptionHandler {

    Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

@ExceptionHandler
@ResponseBody
    public String handle_Exception(Exception e) {
    String message = e.getClass()+" "+e.getMessage();
    logger.error("!!!LOGGER: "+ message);
        return message+ "<br>"+" Please, back to the previous page.";
    }



   @ExceptionHandler
    @ResponseBody
    public String handle_AccessDeniedException(AccessDeniedException e) {
       String message = "The current user does not have privileges to perform this operation";
       logger.error("!!!LOGGER: "+ message);
       return message+ "<br>"+" Please, back to the previous page.";
    }


}
