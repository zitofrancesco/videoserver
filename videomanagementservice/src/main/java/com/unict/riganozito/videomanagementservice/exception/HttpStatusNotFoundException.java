package com.unict.riganozito.videomanagementservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not Found")
public class HttpStatusNotFoundException extends Exception {

}