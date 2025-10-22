package com.eCondo.auth.api.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserView {
    private String id;
    private String name;
    private String fullName;
    
}
