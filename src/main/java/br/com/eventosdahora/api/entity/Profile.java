package br.com.eventosdahora.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tb_profile")
public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_profile")
	private Integer idProfile;

	@Column(name="ds_profile")
	private String dsProfile;

	@Column(name="nm_profile")
	private String nmProfile;

	@JsonIgnore
	@ManyToMany(mappedBy="profiles")
	private List<Role> roles;

	@JsonIgnore
	@OneToMany(mappedBy="profile")
	private List<User> users;
	
	public Profile() {
	}

	public Integer getIdProfile() {
		return this.idProfile;
	}

	public void setIdProfile(Integer idProfile) {
		this.idProfile = idProfile;
	}

	public String getDsProfile() {
		return this.dsProfile;
	}

	public void setDsProfile(String dsProfile) {
		this.dsProfile = dsProfile;
	}

	public String getNmProfile() {
		return this.nmProfile;
	}

	public void setNmProfile(String nmProfile) {
		this.nmProfile = nmProfile;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
