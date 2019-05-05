package com.tradegecko.ordersdbapi.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "obj_id")
    private BigInteger objectId;

    @Column(name = "cust_nm")
    private String customerName;

    @Column(name = "cust_adr")
    private String customerAddress;

    @Column(name = "status")
    private String status;

    @Column(name = "ship_dt")
    private String shipDate;

    @Column(name = "ship_prvdr")
    private String shippingProvider;

    @Column(name = "ts")
    private BigInteger timestamp;

    public enum OrderStatus {
        UNPAID,
        PAID,
        SHIPPED
    }
}
