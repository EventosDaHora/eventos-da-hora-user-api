package br.com.eventosdahora.api.enums;

public enum ProfileEnum {
	
	ADMIN(1, "ADMIN"),
	USER(2, "USER");
	
	private String profile;
	private int code;
	
	private ProfileEnum(int code, String profile) {
		this.profile = profile;
		this.code = code;
	}
	
	public String getProfile() {
		return profile;
	}
	
	public int getCode() {
		return code;
	}
}
