package com.betamart.module.user.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String username;
    private String password;

    @JsonProperty(value = "role_id")
    private Long roleId;
}
