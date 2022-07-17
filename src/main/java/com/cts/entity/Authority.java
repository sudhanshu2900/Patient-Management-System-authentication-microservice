package com.cts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Table(name="AUTH_AUTHORITY")
@Entity
public class Authority implements GrantedAuthority{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="role_code")
	private String roleCode;
	
	@Column(name="role_detail")
	private String roleDetail;

	@Override
	public String getAuthority() {
		return null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDetail() {
		return roleDetail;
	}

	public void setRoleDetail(String roleDetail) {
		this.roleDetail = roleDetail;
	}

}
