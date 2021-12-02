package com.betamart.common.payload.response;

import lombok.Data;

@Data
public class DataTableResponse {
    private int recordsTotal;
    private int recordsFiltered;
    private int draw;
}
