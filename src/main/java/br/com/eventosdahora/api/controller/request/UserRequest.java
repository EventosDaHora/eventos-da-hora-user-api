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
	@Size(max = 2, min = 2)
	private String nuDdd;
	
	@NotBlank
	@Size(max = 9, min = 9)
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
	
	public String getNuDdd() {
		return nuDdd;
	}
	
	public void setNuDdd(final String nuDdd) {
		this.nuDdd = nuDdd;
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
