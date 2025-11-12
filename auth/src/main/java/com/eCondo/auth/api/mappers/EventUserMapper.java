package com.eCondo.auth.api.mappers;

import org.mapstruct.Mapper;

import com.eCondo.auth.api.events.UserCreatedEvent;
import com.eCondo.auth.api.requests.CreateUserRequest;

@Mapper(componentModel="Spring")
public abstract class EventUserMapper {
    public abstract UserCreatedEvent toEvent(final String instance, CreateUserRequest request);
}
