package com.sica.domain;

import java.io.Serializable;

public class Role implements Serializable {
	private String id;

	private String name;

	private String jsms;

	private String bz;

	private Integer jlzt;

	private String glbm;

	private String userid;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getJsms() {
		return jsms;
	}

	public void setJsms(String jsms) {
		this.jsms = jsms == null ? null : jsms.trim();
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
	}

	public Integer getJlzt() {
		return jlzt;
	}

	public void setJlzt(Integer jlzt) {
		this.jlzt = jlzt;
	}

	public String getGlbm() {
		return glbm;
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm == null ? null : glbm.trim();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
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
		Role other = (Role) that;
		return (this.getId() == null ? other.getId() == null
				: this.getId().equals(other.getId()))
				&& (this.getName() == null ? other.getName() == null
						: this.getName().equals(other.getName()))
				&& (this.getJsms() == null ? other.getJsms() == null
						: this.getJsms().equals(other.getJsms()))
				&& (this.getBz() == null ? other.getBz() == null
						: this.getBz().equals(other.getBz()))
				&& (this.getJlzt() == null ? other.getJlzt() == null
						: this.getJlzt().equals(other.getJlzt()))
				&& (this.getGlbm() == null ? other.getGlbm() == null
						: this.getGlbm().equals(other.getGlbm()))
				&& (this.getUserid() == null ? other.getUserid() == null
						: this.getUserid().equals(other.getUserid()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getJsms() == null) ? 0 : getJsms().hashCode());
		result = prime * result + ((getBz() == null) ? 0 : getBz().hashCode());
		result = prime * result + ((getJlzt() == null) ? 0 : getJlzt().hashCode());
		result = prime * result + ((getGlbm() == null) ? 0 : getGlbm().hashCode());
		result = prime * result
				+ ((getUserid() == null) ? 0 : getUserid().hashCode());
		return result;
	}
}
