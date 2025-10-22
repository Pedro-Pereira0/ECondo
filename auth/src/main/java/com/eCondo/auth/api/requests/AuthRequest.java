package com.eCondo.auth.api.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthRequest {
    @NotNull
    @Email
    private String username;
    @NotNull
    private String password;
    
}
