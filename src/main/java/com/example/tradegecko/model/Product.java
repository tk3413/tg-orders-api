package com.example.tradegecko.model;

import lombok.Data;

@Data
class Product {
    private String name;

    private Double price;

    private int stockLevels;

    private String timestamp;
}
