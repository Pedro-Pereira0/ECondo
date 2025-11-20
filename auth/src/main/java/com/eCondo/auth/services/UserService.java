package com.eCondo.auth.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eCondo.auth.api.events.UserCreatedEvent;
import com.eCondo.auth.api.mappers.EventUserMapper;
import com.eCondo.auth.api.mappers.UserMapper;
import com.eCondo.auth.api.requests.CreateUserRequest;
import com.eCondo.auth.exceptions.ConflictException;
import com.eCondo.auth.model.Role;
import com.eCondo.auth.model.User;
import com.eCondo.auth.notf.EventPublisher;
import com.eCondo.auth.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepo;
	private final EventUserMapper eventUserMapper;
	//private final RabbitPublisher eventPublisher;
	private final EventPublisher eventPublisher;

	@Value("${app.instance}")
	private String instance;

    @Transactional
	public User create(final CreateUserRequest request) {
		if (userRepo.findByUsername(request.getUsername()).isPresent()) {
			throw new ConflictException("Username already exists!");
		}
		if (!request.getPassword().equals(request.getRePassword())) {
			throw new ValidationException("Passwords don't match!");
		}

		final HashSet<String> authorities = new HashSet<>();
		authorities.add(Role.ADMIN);
		
		User user = userMapper.create(request, authorities);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user = userRepo.save(user);

		UserCreatedEvent event = eventUserMapper.toEvent(instance, request);

		eventPublisher.send(event);

		return user;
	}

    @Transactional
	public User localCreate(final UserCreatedEvent event) {
		if (userRepo.findByUsername(event.getUsername()).isPresent()) {
			throw new ConflictException("Username already exists!");
		}
		if (!event.getPassword().equals(event.getRePassword())) {
			throw new ValidationException("Passwords don't match!");
		}

		final HashSet<String> authorities = new HashSet<>();
		authorities.add(Role.ADMIN);

		final User user = userMapper.create(event, authorities);
		user.setPassword(passwordEncoder.encode(event.getPassword()));

		System.out.println("Event " + event + " processed.");
		
		return userRepo.save(user);
	}

/*     @Transactional
	public User update(final Long id, final EditUserRequest request) {
		final User user = userRepo.getById(id);
		userEditMapper.update(request, user);

		return userRepo.save(user);
	}

	@Transactional
	public User upsert(final CreateUserRequest request) {
		final Optional<User> optionalUser = userRepo.findByUsername(request.getUsername());

		if (optionalUser.isEmpty()) {
			return create(request);
		}
		final EditUserRequest updateUserRequest = new EditUserRequest(request.getFullName(), request.getAuthorities());
		return update(optionalUser.get().getId(), updateUserRequest);
	} */

	@Transactional
	public User delete(@NonNull final Long id) {
		final User user = userRepo.getById(id);

		// user.setUsername(user.getUsername().replace("@", String.format("_%s@",
		// user.getId().toString())));
		user.setEnabled(false);
		return userRepo.save(user);
	}

    @Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with username - %s, not found", username)));
	}

	public boolean usernameExists(final String username) {
		return userRepo.findByUsername(username).isPresent();
	}

	public User getUser(@NonNull final Long id) {
		return userRepo.getById(id);
	}

}