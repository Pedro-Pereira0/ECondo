package com.eCondo.auth.api;

import static java.lang.String.format;

import java.time.Instant;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.joining;
import java.security.Principal;

/**
 * Based on https://github.com/Yoh0xFF/java-spring-security-example
 *
 */

@Tag(name = "Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class AuthApi {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UserViewMapper userViewMapper;
    private final UserService userService;

    @PostMapping("login")
	public ResponseEntity<UserView> login(@RequestBody @Valid final AuthRequest request) {
		try {
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			// if the authentication is successful, Spring will store the authenticated user
			// in its "principal"
			final User user = (User) authentication.getPrincipal();
			final Instant now = Instant.now();
			final long expiry = 9999999L; // 1 hours is usually too long for a token to be valid. adjust for production

			final String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(joining(" "));

			final JwtClaimsSet claims = JwtClaimsSet.builder().issuer("example.io").issuedAt(now)
					.expiresAt(now.plusSeconds(expiry)).subject(format("%s,%s", user.getId(), user.getUsername()))
					.claim("roles", scope).build();

			final String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(userViewMapper.toUserView(user));
		} catch (final BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	/**
	 * signup to the service
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("register")
	public ResponseEntity<UserView> register(@RequestBody @Valid final CreateUserRequest request) {
		final var user = userService.create(request);
		return new ResponseEntity<>(userViewMapper.toUserView(user), HttpStatus.CREATED);
	}

	//Uses the same request as the created so the password and username is updated
	@PutMapping("update")
	public ResponseEntity<UserView> update(Principal principal, @RequestBody @Valid final UpdateUserRequest request){
		int index = principal.getName().indexOf(",");
		String username = principal.getName().substring(index+1);

		final var user = userService.newUpdate(username,request);
		return new ResponseEntity<>(userViewMapper.toUserView(user),HttpStatus.OK);
	}

}
