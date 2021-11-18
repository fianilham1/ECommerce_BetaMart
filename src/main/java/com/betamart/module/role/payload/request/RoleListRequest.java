package com.betamart.module.role.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListRequest {

    @JsonProperty(value = "role_name_list")
    private List<String> roleNameList;
}
