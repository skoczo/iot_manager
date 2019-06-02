package com.skoczo.iot_manager.security.token.web.users;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User implements UserDetails {
	private static final long serialVersionUID = 2396654715019746670L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// must be uniq
	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	// must be uniq
	@Column(unique = true, nullable = false)
	private String iotToken;
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
