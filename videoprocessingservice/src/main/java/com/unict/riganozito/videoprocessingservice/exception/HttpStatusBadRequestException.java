package com.unict.riganozito.videoprocessingservice.exception;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.http.HttpStatus;

public class HttpStatusBadRequestException extends HttpStatusCodeException {

    public HttpStatusBadRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}