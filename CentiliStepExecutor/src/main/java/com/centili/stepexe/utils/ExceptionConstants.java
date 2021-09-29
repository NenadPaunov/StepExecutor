package com.centili.stepexe.utils;

public enum ExceptionConstants {

	STEP_NOT_EXIST("Step doesn`t exists."), 
	STEP_EXIST("Step with provided name exist."),
	TRANSACTIONS_NOT_FOUND("Transactions for provided step doesn`t exists.");

	private final String code;

	private ExceptionConstants(String code) {
		this.code = code;
	}

	public final String getCode() {
		return code;
	}
}
