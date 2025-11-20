package com.eCondo.auth.api.mappers;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.eCondo.auth.api.events.UserCreatedEvent;
import com.eCondo.auth.api.requests.CreateUserRequest;
import com.eCondo.auth.model.Role;
import com.eCondo.auth.model.User;

import static java.util.stream.Collectors.toSet;

@Mapper(componentModel="Spring")
public abstract class UserMapper {

    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "stringToRole")
	@Mapping(target = "enabled", constant = "true")
	public abstract User create(CreateUserRequest request, final Set<String> authorities);

	@Mapping(source = "authorities", target = "authorities", qualifiedByName = "stringToRole")
	@Mapping(target = "enabled", constant = "true")
	public abstract User create(UserCreatedEvent event, final Set<String> authorities);

    @Named("stringToRole")
	protected Set<Role> stringToRole(final Set<String> authorities) {
		if (authorities != null) {
			return authorities.stream().map(Role::new).collect(toSet());
		}
		return new HashSet<>();
	}
    
}
