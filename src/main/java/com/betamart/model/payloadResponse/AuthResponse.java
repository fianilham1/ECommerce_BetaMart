package com.betamart.model.payloadResponse;

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
