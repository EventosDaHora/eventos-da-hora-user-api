package br.com.eventosdahora.api.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
	
	@Email
	@Size(max = 255)
	@NotBlank
	private String dsEmail;
	
	@NotBlank
	@Size(max = 20, min = 9)
	private String nuCel;
	
	@NotBlank
	@Size(max = 255)
	private String nmUser;
	
	public String getDsEmail() {
		return dsEmail;
	}
	
	public void setDsEmail(final String dsEmail) {
		this.dsEmail = dsEmail;
	}
	
	
	public String getNuCel() {
		return nuCel;
	}
	
	public void setNuCel(final String nuCel) {
		this.nuCel = nuCel;
	}
	
	public String getNmUser() {
		return nmUser;
	}
	
	public void setNmUser(final String nmUser) {
		this.nmUser = nmUser;
	}
}
