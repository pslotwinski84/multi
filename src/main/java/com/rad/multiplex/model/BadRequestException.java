package com.rad.multiplex.model;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	
	  public BadRequestException(String exception) {
	    super(exception);
	  }
}