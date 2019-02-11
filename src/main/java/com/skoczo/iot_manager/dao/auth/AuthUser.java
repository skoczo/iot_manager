package com.skoczo.iot_manager.dao.auth;

import java.security.MessageDigest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.DatatypeConverter;

import lombok.Data;
import lombok.Setter;

@Data
@Entity
public class AuthUser {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@Setter(lombok.AccessLevel.NONE)
	private String password;
	
	@Setter(lombok.AccessLevel.NONE)
	@Column(unique=true, nullable=false)
	private String iotToken;
	
	public void setPassword(String password) throws Exception {
		this.password = password;
		setIotToken();		
	}
	
	public void setIotToken(String iotToken) {
		this.iotToken = iotToken;
	}
	
	public void setIotToken() throws Exception {
		if(password == null || password.isEmpty()) {
			throw new Exception("Can't generate iot token. Password is empty or not set");
		}
		
		MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update((Long.toString(System.currentTimeMillis()) + password).getBytes());
	    byte[] digest = md.digest();
	    iotToken = DatatypeConverter
	      .printHexBinary(digest).toUpperCase();
		
		
	}
}
