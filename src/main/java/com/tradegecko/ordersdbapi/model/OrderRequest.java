package com.tradegecko.ordersdbapi.model;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import java.math.BigInteger;

@Data
public class OrderRequest {

    @Nullable
    @Column(name = "obj_id")
    private BigInteger objectId;

    @Nullable
    @Column(name = "cust_nm")
    private String customerName;

    @Nullable
    @Column(name = "cust_adr")
    private String customerAddress;

    @Nullable
    @Column(name = "status")
    private String status;

    @Nullable
    @Column(name = "ship_dt")
    private String shipDate;

    @Nullable
    @Column(name = "ship_prvdr")
    private String shippingProvider;

    @Nullable
    @Column(name = "ts")
    private BigInteger timestamp;
}
