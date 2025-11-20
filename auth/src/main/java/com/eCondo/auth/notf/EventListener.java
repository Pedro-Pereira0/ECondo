package com.eCondo.auth.notf;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.eCondo.auth.api.events.UserCreatedEvent;
import com.eCondo.auth.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventListener {
    private final UserService userService;

    @Value("${app.instance}")
    private String instance;

    @RabbitListener(queues = "#{userCreatedQueue.name}")
    public void handleUserCreatedEvent(@NonNull UserCreatedEvent event){

        if(!event.getInstance().equals(instance)){
            try{
                userService.localCreate(event);
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Event " + event + "skipped.");
            }
        }
    }
    
}
