package com.eCondo.auth.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@AllArgsConstructor
public class Role implements GrantedAuthority{
    private static final long serialVersionUID = 1L;

	public static final String SYS_ADMIN = "SYS_ADMIN";
	public static final String ADMIN = "ADMIN";

	private String authority;

	@Override
	public String getAuthority(){
		return this.authority;
	}

}
