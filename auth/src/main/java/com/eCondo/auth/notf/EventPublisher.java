package com.eCondo.auth.notf;

import com.eCondo.auth.api.events.UserCreatedEvent;


public interface EventPublisher {
    public void send(UserCreatedEvent event);

}
