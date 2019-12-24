package com.unict.riganozito.videomanagementservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class HttpStatusInternalServerErrorException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 8664627371784720990L;

}