package com.betamart.base.payload.response;

import lombok.Data;

@Data
public class DataTableResponse {
    private int recordsTotal;
    private int recordsFiltered;
    private int draw;
}
