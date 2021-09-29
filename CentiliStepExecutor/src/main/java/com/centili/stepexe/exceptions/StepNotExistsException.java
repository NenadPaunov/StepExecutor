package com.centili.stepexe.exceptions;

import com.centili.stepexe.utils.ExceptionConstants;

public class StepNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public StepNotExistsException() {
		super(ExceptionConstants.STEP_NOT_EXIST.getCode());
	}

	public static String getExceptionMessage() {
		return new StepNotExistsException().getMessage();
	}
}
