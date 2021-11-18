package com.betamart.module.user.payload.response;

import com.betamart.module.role.payload.response.RoleResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    String username;
    String password;

    @JsonProperty(value = "role")
    RoleResponse roleResponse;

    String token;
}
