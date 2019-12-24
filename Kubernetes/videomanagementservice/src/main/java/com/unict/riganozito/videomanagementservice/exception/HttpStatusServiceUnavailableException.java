package com.unict.riganozito.videomanagementservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Service Unavailable")
public class HttpStatusServiceUnavailableException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -5826069237256531464L;

}