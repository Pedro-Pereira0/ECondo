package com.eCondo.auth.api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.eCondo.auth.api.views.UserView;
import com.eCondo.auth.model.User;

@Mapper(componentModel="spring")
public abstract class UserViewMapper {
    public abstract UserView toUserView(User user);
	public abstract List<UserView> toUserView(List<User> users);
    
}
