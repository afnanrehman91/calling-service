package com.calling.app.models;

public class HelloWorldResponse {
	private String salutation;

	public HelloWorldResponse() {
	}

	public HelloWorldResponse(String salutation) {
		this.salutation = salutation;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	@Override
	public String toString() {
		return "HelloWorldResponse [salutation=" + salutation + "]";
	}

}
