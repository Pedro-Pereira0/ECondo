package com.eCondo.auth.notf;

import com.eCondo.auth.api.events.CreateUserEvent;

public interface EventPublisher {
    public void send(CreateUserEvent event);

}
