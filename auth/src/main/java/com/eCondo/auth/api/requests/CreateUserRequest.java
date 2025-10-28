package com.eCondo.auth.api.requests;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Getter
public class CreateUserRequest {
    @NonNull
	@NotBlank
	@Email
	private String username;

	@NonNull
	@NotBlank
	private String fullName;

	private String company;

	@NonNull
	@NotBlank
	private String password;

	@NonNull
	@NotBlank
	private String rePassword;

	private Set<String> authorities = new HashSet<>();

    public CreateUserRequest(final String username, final String fullName, final String password) {
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.rePassword = password;
	}

	public CreateUserRequest(@NonNull String username, @NonNull String fullName, @NonNull String password, @NonNull String rePassword, Set<String> authorities) {
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.rePassword = rePassword;
		this.authorities = authorities;
	}
    
}
