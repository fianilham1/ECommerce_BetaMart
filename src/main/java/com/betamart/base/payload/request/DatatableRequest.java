package com.betamart.base.payload.request;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class DatatableRequest {

    private int draw;
    private int start;
    private int length;
    private HashMap<String, Object> search;
    private List<HashMap<String, Object>> order;
}
