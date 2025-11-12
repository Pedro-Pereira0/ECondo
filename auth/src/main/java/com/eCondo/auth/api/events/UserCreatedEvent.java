package com.eCondo.auth.api.events;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent {
	@Getter
	@NotBlank
	@NonNull
	private String instance;
	@NonNull
	@NotBlank
	@Email
	private String username;

	@NonNull
	@NotBlank
	private String fullName;

	@NonNull
	@NotBlank
	private String password;

	@NonNull
	@NotBlank
	private String rePassword;

	private Set<String> authorities = new HashSet<>();

	public UserCreatedEvent(final String instance, final String username, final String fullName, final String password) {
		this.instance = instance;
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.rePassword = password;
	}

	public UserCreatedEvent(final String instance, final String username, final String fullName, final String password, final String rePassword, final Set<String> authorities) {
		this.instance = instance;
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.rePassword = rePassword;
		this.authorities = authorities;
	}
}
