package com.unict.riganozito.videomanagementservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class HttpStatusBadRequestException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -8365664689304867478L;

}