package com.sica.domain;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	private String id;

	private String username;

	private String password;

	private List<Role> roles;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		User other = (User) that;
		return (this.getId() == null ? other.getId() == null
				: this.getId().equals(other.getId()))
				&& (this.getUsername() == null ? other.getUsername() == null
						: this.getUsername().equals(other.getUsername()))
				&& (this.getPassword() == null ? other.getPassword() == null
						: this.getPassword().equals(other.getPassword()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getUsername() == null) ? 0 : getUsername().hashCode());
		result = prime * result
				+ ((getPassword() == null) ? 0 : getPassword().hashCode());
		return result;
	}
}
