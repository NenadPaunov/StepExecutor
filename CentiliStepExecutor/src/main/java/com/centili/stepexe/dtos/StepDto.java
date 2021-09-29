package com.centili.stepexe.dtos;

import javax.validation.constraints.NotBlank;

public class StepDto {

	private int id;
	@NotBlank(message = "Step name is required")
	private String name;
	@NotBlank(message = "URL is required")
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
