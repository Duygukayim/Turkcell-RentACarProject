package com.turkcell.rentACarProject.core.utilities.results;

public class SuccessResult extends Result {

	public SuccessResult() {
		super(true);
	}

	public SuccessResult(String message) {
		super(true, message);
	}

	public class ErrorResult extends Result {
		public ErrorResult() {
			super(false);
		}

		public ErrorResult(String message) {
			super(false, message);
		}
	}
}
