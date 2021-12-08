package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.exception.InvalidEmailAddressException;
import com.switchfully.eurder.domain.exception.UnauthorizedException;
import com.switchfully.eurder.domain.exception.UnknownIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    protected void unauthorizedHandler(UnauthorizedException exception, HttpServletResponse response) throws IOException {
        response.sendError(UNAUTHORIZED.value(), exception.getMessage());
        LOGGER.error(exception.getMessage(), exception);
    }

    @ExceptionHandler(InvalidEmailAddressException.class)
    protected void invalidEmailAddressHandler(InvalidEmailAddressException exception, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
        LOGGER.error(exception.getMessage(), exception);
    }

    @ExceptionHandler(UnknownIdException.class)
    protected void unknownIdExceptionHandler(UnknownIdException exception, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
        LOGGER.error(exception.getMessage(), exception);
    }
}
