package br.com.eventosdahora.api.entity;

import br.com.eventosdahora.api.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="tb_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seq_user", sequenceName="seq_user", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_user")
	@Column(name="id_user")
	private Integer idUser;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_profile")
	private Profile profile;
	
	@Column(name = "dt_create")
	private Timestamp dtCreate;
	
	@Pattern(message = "Email must be a valid email address", regexp = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
	@Email
	@NotBlank
	@Size(max = 255)
	@Column(name="ds_email")
	private String dsEmail;

	@Size(max = 500)
	@Column(name="ds_password")
	private String dsPassword;
	
	@NotBlank
	@Size(max = 2, min = 2)
	@Column(name = "nu_ddd_cel")
	private String nuDdd;
	
	@NotBlank
	@Size(max = 9, min = 8)
	@Column(name = "nu_cel")
	private String nuCel;
	
	@NotBlank
	@Size(max = 255)
	@Column(name="nm_user")
	private String nmUser;
	
	@Column(name = "dt_last_login")
	private Timestamp dtLastLogin;
	
	@Column(name="ds_token")
	private String dsToken;

	@Column(name="dt_expiry_token")
	private Timestamp dtExpiry;
	
	@Column(name="is_email_verified")
	private boolean isEmailVerified;
	
	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getDsEmail() {
		return this.dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = StringUtil.lowerCase(dsEmail);
	}

	@JsonIgnore
	public String getDsPassword() {
		return this.dsPassword;
	}

	@JsonProperty
	public void setDsPassword(String dsPassword) {
		this.dsPassword = dsPassword;
	}

	public String getNmUser() {
		return nmUser;
	}

	public void setNmUser(String nmUser) {
		this.nmUser = nmUser;
	}
	
	public String getNuDdd() {
		return nuDdd;
	}

	public void setNuDdd(String nuDdd) {
		this.nuDdd = nuDdd;
	}

	public String getNuCel() {
		return nuCel;
	}

	public void setNuCel(String nuCel) {
		this.nuCel = nuCel;
	}

	public String getDsToken() {
		return dsToken;
	}

	public void setDsToken(String dsToken) {
		this.dsToken = dsToken;
	}

	public Timestamp getDtExpiry() {
		return dtExpiry;
	}

	public void setDtExpiry(Timestamp dtExpiry) {
		this.dtExpiry = dtExpiry;
	}
	
	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}
	
	public boolean getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public Timestamp getDtLastLogin() {
		return dtLastLogin;
	}
	
	public void setDtLastLogin(final Timestamp dtLastLogin) {
		this.dtLastLogin = dtLastLogin;
	}
	
	public void setEmailVerified(final boolean emailVerified) {
		isEmailVerified = emailVerified;
	}
}

