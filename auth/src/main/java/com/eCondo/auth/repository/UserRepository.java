package com.eCondo.auth.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import com.eCondo.auth.exceptions.NotFoundException;
import com.eCondo.auth.model.User;


public interface UserRepository{

	<S extends User> List<S> saveAll(@NonNull Iterable<S> entities);

	<S extends User> S save(@NonNull S entity);

	Optional<User> findById(@NonNull Long objectId);

	default User getById(@NonNull final Long id) {
		final Optional<User> maybeUser = findById(id);
		// throws 404 Not Found if the user does not exist or is not enabled
		return maybeUser.filter(User::isEnabled).orElseThrow(() -> new NotFoundException(User.class, id));
	}

	default User getByUsername(final String username) {
		final Optional<User> maybeUser = findByUsername(username);
		// throws 404 Not Found if the user does not exist or is not enabled
		return maybeUser.filter(User::isEnabled).orElseThrow(() -> new NotFoundException(User.class, username));
	}

	Optional<User> findByUsername(String username);

	//List<User> searchUsers(Page page, SearchUsersQuery query);
}