package com.centili.stepexe.exceptions;

import com.centili.stepexe.utils.ExceptionConstants;

public class StepExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public StepExistsException() {
		super(ExceptionConstants.STEP_EXIST.getCode());
	}

	public static String getExceptionMessage() {
		return new StepExistsException().getMessage();
	}
}