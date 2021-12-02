package com.betamart.model;

import com.betamart.model.auditable.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_destination_location_details")
@Data
public class OrderDestinationLocationDetails extends BaseModel {
    //id -> location id

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "fk_order_id", nullable = false)
    private Order order;

    @Column(name = "province_id", nullable = false)
    private long provinceId;

    @Column(name = "city_id", nullable = false)
    private long cityId;

    @Column(name = "province_name", nullable = false)
    private String provinceName;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "city_type", nullable = false)
    private String cityType;

    @Column(name = "postal_code", nullable = false)
    private int postalCode;

}
